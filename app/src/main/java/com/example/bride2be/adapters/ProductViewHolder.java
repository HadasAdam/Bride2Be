package com.example.bride2be.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bride2be.R;
import com.example.bride2be.models.Product;

public class ProductViewHolder extends RecyclerView.ViewHolder
{
    public ProductAdapter.OnItemClickListener listener;
    TextView productNameTV;
    TextView productPriceTV;
    TextView publisherCityTV;
    int position;

    public ProductViewHolder(@NonNull View itemView)
    {
        super(itemView);
        productNameTV = itemView.findViewById(R.id.productlistrow_productName);
        productPriceTV = itemView.findViewById(R.id.productlistrow_price);
        publisherCityTV = itemView.findViewById(R.id.productlistrow_City);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });
    }

    public void bindData(Product product, int position)
    {
        productNameTV.setText(product.getTitle());
        productPriceTV.setText(product.getPrice().toString());
        publisherCityTV.setText(product.getUploaderId());
        this.position = position;
    }
}
