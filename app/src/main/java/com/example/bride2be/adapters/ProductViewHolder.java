package com.example.bride2be.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bride2be.R;
import com.example.bride2be.models.Model;
import com.example.bride2be.models.Product;

public class ProductViewHolder extends RecyclerView.ViewHolder
{
    public ProductAdapter.OnItemClickListener listener;
    TextView productNameTV;
    TextView productPriceTV;
    ImageView productImage;
    int position;

    public ProductViewHolder(@NonNull View itemView)
    {
        super(itemView);
        productNameTV = itemView.findViewById(R.id.productlistrow_productName);
        productPriceTV = itemView.findViewById(R.id.productlistrow_price);
        productImage = itemView.findViewById(R.id.productlistrow_image);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //final int position = getAdapterPosition();
                listener.onItemClick(position);

            }
        });
    }

    public void bindData(Product product, int position)
    {
        productNameTV.setText(product.getTitle());
        productPriceTV.setText(String.valueOf(product.getPrice()));
        Model.instance.loadPictureFromStorage(product.getPicture(), productImage);
        this.position = position;
    }


}
