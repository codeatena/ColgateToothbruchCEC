package com.general.mediaplayer.colgatetoothbruchcec.model;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;

import java.util.ArrayList;
import java.util.List;

public class Global {

    static public ProductModel currentProduct;
    static public List<ProductModel> products = new ArrayList<>();

    static public Boolean isBestClean = false;
    static public Boolean isWhiterSmile = false;
    static public Boolean isSpeciality = false;
    static public Boolean isExtraSoft = false;
    static public Boolean isSoft = false;
    static public Boolean isMedium = false;
    static public Boolean isColgate = false;
    static public Boolean isOral = false;
    static public Boolean isNew = false;

    static public List<String> benefitFilter = new ArrayList<>();
    static public List<String> bristle_typeFilter = new ArrayList<>();
    static public List<String> brandFilter = new ArrayList<>();

}