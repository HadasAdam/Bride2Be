package com.example.bride2be;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.ContentView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.bride2be.adapters.ProductAdapter;
import com.example.bride2be.models.Model;
import com.example.bride2be.models.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProductsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductsListFragment extends Fragment {

    ArrayList <Product> productsArrayList;
    ProgressBar progressBar;
    int count = 0;
    Timer timer;

    public ProductsListFragment() {
    }

    public static ProductsListFragment newInstance(String param1, String param2) {
        ProductsListFragment fragment = new ProductsListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products_list, container, false);

       progressBar = view.findViewById(R.id.product_list_progressBar);
        RecyclerView recyclerView = view.findViewById(R.id.productlist_recycledview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        ProductAdapter adapter = new ProductAdapter(getLayoutInflater());
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(linearLayoutManager);
        timer = new Timer();TimerTask timerTask = new TimerTask() {
           @Override
          //the timer is run but the data is not view
           public void run() {
               count++;
               progressBar.setProgress(count);
                if(count == 100)
                {
                 timer.cancel();
                 Model.instance.getAllProducts(new Model.GetAllProductsListener() {
                     @Override
                     public void onComplete(List<Product> products) {
                         adapter.setData(products);
                         recyclerView.setAdapter(adapter);
                         productsArrayList = new ArrayList<Product>(products);
                     }
                 });
                }
            }
        };
        timer.schedule(timerTask,0,100);
        adapter.setOnClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String productId = productsArrayList.get(position).getId();
                NavDirections action = (NavDirections)ProductsListFragmentDirections.actionProductsListFragmentToProductDetailsFragment(productId);
                Navigation.findNavController(view).navigate(action);
            }
        });
    return  view;

}


}