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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ArrayList <Product> productsArrayList;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProductsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ItemsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProductsListFragment newInstance(String param1, String param2) {
        ProductsListFragment fragment = new ProductsListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

   ProgressBar progressBar;
   int count = 0;
   Timer timer;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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