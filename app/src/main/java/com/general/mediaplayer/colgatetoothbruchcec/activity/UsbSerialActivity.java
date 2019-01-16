package com.general.mediaplayer.colgatetoothbruchcec.activity;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;

import com.general.mediaplayer.colgatetoothbruchcec.model.Global;
import com.general.mediaplayer.colgatetoothbruchcec.model.ProductModel;
import com.hoho.android.usbserial.driver.CdcAcmSerialDriver;
import com.hoho.android.usbserial.driver.FtdiSerialDriver;
import com.hoho.android.usbserial.driver.ProbeTable;
import com.hoho.android.usbserial.driver.ProlificSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;
import com.hoho.android.usbserial.util.HexDump;
import com.hoho.android.usbserial.util.SerialInputOutputManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UsbSerialActivity extends BaseActivity {


    private final String TAG = UsbSerialActivity.class.getSimpleName();
    private static final String ACTION_USB_PERMISSION = "com.examples.accessory.controller.action.USB_PERMISSION";

    UsbSerialPort sPort;
    UsbDeviceConnection connection;
    PendingIntent mPermissionIntent;

    private boolean isAsked = false;
    private SerialInputOutputManager mSerialIoManager;
    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();

    private final SerialInputOutputManager.Listener mListener =
            new SerialInputOutputManager.Listener() {

                @Override
                public void onRunError(Exception e) {
                    Log.d(TAG, "Runner stopped.");
                }

                @Override
                public void onNewData(final byte[] data) {
                    UsbSerialActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            UsbSerialActivity.this.updateReceivedData(data);
                        }
                    });
                }
            };

    private void updateReceivedData(byte[] data) {
        final String message = "Read " + data.length + " bytes: \n"
                + HexDump.dumpHexString(data) + "\n\n";
        Log.d("---HexString---", message);
        try {
            String upc_code = new String(data, "UTF-8");
            Log.d("---UPCCode---", upc_code);
            compareUPCCode(upc_code.replace("\r\n", ""));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.d(TAG, e.toString());
        }
    }

    public void compareUPCCode(String upc_Code) {
        if (upc_Code != null && !upc_Code.isEmpty()) {
            for (ProductModel productModel : Global.products) {
                if (productModel.upc_code.equals(upc_Code)) {
                    Global.currentProduct = productModel;
                    Intent intent = new Intent(UsbSerialActivity.this ,ProductDetailActivity.class);
                    startActivity(intent);
                }
            }
        }
    }

    private void stopIoManager() {
        if (mSerialIoManager != null) {
            Log.i(TAG, "Stopping io manager ..");
            mSerialIoManager.stop();
            mSerialIoManager = null;
        }
    }

    private void startIoManager() {
        if (sPort != null) {
            Log.i(TAG, "Starting io manager ..");
            mSerialIoManager = new SerialInputOutputManager(sPort, mListener);
            mExecutor.submit(mSerialIoManager);
        }
    }

    private void onDeviceStateChange() {
        stopIoManager();
        startIoManager();
    }

    public final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {

                    final UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
                    if (connection == null) {
                        connection = usbManager.openDevice(sPort.getDriver().getDevice());
                        openConnection(connection);
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPermissionIntent = PendingIntent.getBroadcast(this, 0, new Intent(ACTION_USB_PERMISSION), 0);
        IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
        registerReceiver(mUsbReceiver, filter);

        ProbeTable customTable = getDetail();
        UsbSerialProber prober = new UsbSerialProber(customTable);
        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        List<UsbSerialDriver> availableDrivers = prober.findAllDrivers(manager);
        if (availableDrivers.isEmpty()) {
            return;
        }

        // Open a connection to the first available driver.
        UsbSerialDriver driver = availableDrivers.get(0);
        sPort = driver.getPorts().get(0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sPort != null) {
            final UsbManager usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
            if (usbManager.hasPermission(sPort.getDriver().getDevice())){

                if (connection == null) {
                    connection = usbManager.openDevice(sPort.getDriver().getDevice());
                    openConnection(connection);
                }
            } else{
                if (!isAsked &&  connection == null) {
                    isAsked = true;
                    usbManager.requestPermission(sPort.getDriver().getDevice(), mPermissionIntent);
                }
            }
        }

        onDeviceStateChange();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sPort != null) {
            try {
                sPort.close();
            } catch (IOException e) {
                // Ignore.
            }
            sPort = null;
        }
    }

    private void openConnection(UsbDeviceConnection connection)
    {
        try {
            sPort.open(connection);
            sPort.setParameters(115200, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
            sPort.setDTR(true);
            sPort.setRTS(true);
        } catch (IOException e) {
            Log.e(TAG, "Error setting up device: " + e.getMessage(), e);
            try {
                sPort.close();
            } catch (IOException e2) {
                // Ignore.
            }
            sPort = null;
        }
    }

    public void sendCommand(String str) {
        if (sPort != null) {
            try {
                byte response[] = str.getBytes();
                sPort.write(response, 200);
            } catch (IOException e) {
                Log.e(TAG, "write error: " + e.getMessage());
            }
        }
    }

    public ProbeTable getDetail() {
        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);
        ProbeTable customTable = new ProbeTable();
        //customTable.addProduct(0x2a03, 0x0043, CdcAcmSerialDriver.class);
        UsbSerialProber prober = new UsbSerialProber(customTable);
        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        while (deviceIterator.hasNext()) {

            UsbDevice device = deviceIterator.next();
            String name = device.getManufacturerName().toLowerCase();
            if (name.contains("arduino"))
                customTable.addProduct(device.getVendorId(), device.getProductId(), CdcAcmSerialDriver.class);
            else if(name.contains("ftdi"))
                customTable.addProduct(device.getVendorId(), device.getProductId(), FtdiSerialDriver.class);
            else if(name.contains("prolific"))
                customTable.addProduct(device.getVendorId(), device.getProductId(), ProlificSerialDriver.class);
        }
        return customTable;
    }
}
