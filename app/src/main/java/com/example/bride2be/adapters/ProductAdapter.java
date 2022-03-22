package com.example.bride2be.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bride2be.ProductsListFragment;
import com.example.bride2be.R;
import com.example.bride2be.models.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder>
{
    List<Product> data;
    LayoutInflater inflater;



    public ProductAdapter(LayoutInflater inflater){
        this.inflater = inflater;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private OnItemClickListener listener;

    public List<Product> getData()
    {
        return data;
    }

    public void setData(List<Product> data)
    {
        this.data = data;
    }

    public void setOnClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.product_list_row,parent,false);
        ProductViewHolder holder = new ProductViewHolder(view);
        holder.listener = listener;
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = data.get(position);
        holder.bindData(product,position);
    }

    @Override
    public int getItemCount() {
        if(data == null) {
            return 0;
        }
        return data.size();
    }
}
