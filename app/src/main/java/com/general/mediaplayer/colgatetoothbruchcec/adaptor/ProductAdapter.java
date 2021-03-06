package com.general.mediaplayer.colgatetoothbruchcec.adaptor;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.general.mediaplayer.colgatetoothbruchcec.R;
import com.general.mediaplayer.colgatetoothbruchcec.model.ProductModel;

import java.io.InputStream;
import java.util.List;

import hyogeun.github.com.colorratingbarlib.ColorRatingBar;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductAdapterHolder>{


    private List<ProductModel> list;
    private  OnItemClickListener listener;
    private Context context;

    public ProductAdapter(List<ProductModel> Data ,Context context) {
        list = Data;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductAdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item, parent, false);
        return new ProductAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductAdapterHolder holder, int position) {

        if (position % 2 == 1){
            holder.parentView.setBackground(context.getResources().getDrawable(R.drawable.cell));
        }else {
            holder.parentView.setBackgroundColor(context.getResources().getColor(R.color.light_gray));
        }

        Drawable myDrawable;
        if (list.get(position).brand.equals("Colgate")){
            myDrawable = context.getResources().getDrawable(R.drawable.colgate);
        }else {
            myDrawable = context.getResources().getDrawable(R.drawable.oral);
        }

        holder.colgateImageView.setImageDrawable(myDrawable);
        holder.ratingBar.setRating(list.get(position).star);
        holder.nameTextView.setText(list.get(position).productname);
        holder.priceTextView.setText(list.get(position).price);
        holder.descriptionTextView.setText(list.get(position).long_description);
        holder.bind(list.get(position) ,listener);

        Drawable drawable = loadDrawableFromAssets(context ,list.get(position).product_image);
        holder.productImageView.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }

//    public void setProducts(List<ProductModel> products)
////    {
////        this.list = products;
////    }

    public interface OnItemClickListener{

        void onItemLeftClick(ProductModel productModel);
        void onItemRightClick(ProductModel productModel);

    }

    class ProductAdapterHolder extends RecyclerView.ViewHolder {

        LinearLayout leftView;
        LinearLayout rightView;
        CardView parentView;

        ImageView productImageView;
        TextView nameTextView;
        TextView descriptionTextView;
        TextView priceTextView;
        ImageView colgateImageView;
        ColorRatingBar ratingBar;

        ProductAdapterHolder(View v) {
            super(v);

            leftView = v.findViewById(R.id.left_view);
            rightView = v.findViewById(R.id.right_view);
            parentView = v.findViewById(R.id.parent_view);

            nameTextView = v.findViewById(R.id.name_textview);
            descriptionTextView = v.findViewById(R.id.description_textview);
            productImageView = v.findViewById(R.id.product_imageview);
            colgateImageView = v.findViewById(R.id.colgate_imageview);
            priceTextView = v.findViewById(R.id.price_textview);
            ratingBar = v.findViewById(R.id.rating_bar);
        }

        void bind(final ProductModel productModel, final OnItemClickListener listener)
        {

            leftView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemRightClick(productModel);
                }
            });

            rightView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemRightClick(productModel);
                }
            });
        }
    }

    static public Drawable loadDrawableFromAssets(Context context, String path)
    {
        try (InputStream stream = context.getAssets().open(path)) {
            return Drawable.createFromStream(stream, null);
        } catch (Exception ignored) {
        }
        return null;
    }
}
