package com.amostra.comanda.ui.views.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amostra.comanda.App;
import com.amostra.comanda.R;
import com.amostra.comanda.ui.adapter.ProductAdapter;
import com.amostra.net.data.model.product.Product;
import com.amostra.comanda.ui.viewModels.ProductViewModel;
import com.amostra.net.util.UserPrefManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductFragment extends Fragment {

    private ProductViewModel mViewModel;
    public ProductAdapter productAdapter;
    private List<Product> productsArrayList;
    protected LinearLayoutManager manager;
    protected RecyclerView mRecyclerView;
    private UserPrefManager userPrefManager;

    public static ProductFragment newInstance() {
        return new ProductFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product, container, false);
        userPrefManager = new UserPrefManager(App.getContext());
        final SwipeRefreshLayout mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_product_fragment);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(App.getContext(), R.color.orange), ContextCompat.getColor(App.getContext(),R.color.colorPrimary), ContextCompat.getColor(App.getContext(),R.color.ic_launcher_background));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onCreate(new Bundle());
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


        productsArrayList = getProducts();
        productAdapter = new ProductAdapter(getContext(), productsArrayList);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_fragment_product);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(productAdapter);
        mRecyclerView.setHasFixedSize(true);

        return view;
    }

    private List<Product> getProducts() {

        //List<Product> products = userPrefManager.loadListProducts(UserPrefManager.PRODUCTS);

        List<Product> products = new ArrayList<Product>();
        products.add(new Product("0","default.png" , "PASTEL", "SABOR DE FRANGO", 1, 20, new Date() ));
        products.add(new Product("1","default.png" , "ARROZ", "TIO JOÃO", 1, 30, new Date() ));
        products.add(new Product("2","default.png" , "FEIJÃO", "CARIOCA", 1, 23.45, new Date() ));
        products.add(new Product("3","default.png" , "CARNE", "BOVINA", 1, 32.32, new Date() ));
        return products;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        // TODO: Use the ViewModel
    }

}
