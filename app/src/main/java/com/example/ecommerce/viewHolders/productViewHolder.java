package com.example.ecommerce.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.Interface.ItemClickListner;
import com.example.ecommerce.R;

public class productViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView productName, productdescription, productPrice;
    public ImageView productImg;
    public ItemClickListner listner;

    public productViewHolder(@NonNull View itemView) {
        super(itemView);
        productName = itemView.findViewById(R.id.product_name_row);
        productdescription = itemView.findViewById(R.id.product_description_row);
        productPrice = itemView.findViewById(R.id.product_price_row);
        productImg = itemView.findViewById(R.id.product_image_row);

    }

    public void setItemClickListner(ItemClickListner listner) {
        this.listner = listner;


    }


    @Override
    public void onClick(View view) {

    }
}
