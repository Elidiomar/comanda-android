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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amostra.comanda.App;
import com.amostra.comanda.R;
import com.amostra.comanda.ui.adapter.OrderAdapter;
import com.amostra.net.data.model.product.Order;
import com.amostra.net.data.model.product.Product;
import com.amostra.comanda.ui.viewModels.OrderViewModel;
import com.amostra.net.data.model.User;
import com.amostra.net.util.UserPrefManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderFragment extends Fragment {

    private OrderViewModel mViewModel;
    public OrderAdapter orderAdapter;
    private List<Product> productList ;
    protected LinearLayoutManager manager;
    protected RecyclerView mRecyclerView;
    private UserPrefManager userPrefManager;

    private TextView nameUserTextView, emailUserTextView, dateTextView, statusTextView, valueItemsTextView, totalItemsTextView;
    private Button addButton, removeAllButton;

    private Order order;

    public static OrderFragment newInstance() {
        return new OrderFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        final SwipeRefreshLayout mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_order_fragment);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(App.getContext(), R.color.orange), ContextCompat.getColor(App.getContext(),R.color.colorPrimary), ContextCompat.getColor(App.getContext(),R.color.ic_launcher_background));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onCreate(new Bundle());
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        userPrefManager = new UserPrefManager(App.getContext());

        initViews(view);

        loadOrder(userPrefManager.loadObject(UserPrefManager.ORDER, Order.class));
        orderAdapter = new OrderAdapter(getContext(), productList, this);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_fragment_order);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(orderAdapter);
        mRecyclerView.setHasFixedSize(true);

        return view;
    }

    private void initViews(View view) {

        nameUserTextView = view.findViewById(R.id.text_view_layout_product_cart_name);
        emailUserTextView = view.findViewById(R.id.text_view_layout_product_cart_email);
        dateTextView = view.findViewById(R.id.text_view_layout_product_cart_date);
        statusTextView = view.findViewById(R.id.text_view_layout_product_cart_status);
        valueItemsTextView = view.findViewById(R.id.text_view_layout_product_cart_price_item);
        totalItemsTextView = view.findViewById(R.id.text_view_layout_product_cart_item_total);

        addButton  = view.findViewById(R.id.button_view_item_order_add);
        removeAllButton = view.findViewById(R.id.button_view_item_product_clear);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "ADICIONE UM PRODUTO PARA SALVAR";
                if (order != null) {
                    if (order.getProductList().size() > 0) {
                        message = "SALVO COM SUCESSO";
                        order = new Order();
                        userPrefManager.saveObject(UserPrefManager.ORDER, order);
                        loadOrder(order);
                    }
                }
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });

        removeAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String menssage = "NÃO EXISTE ITEM PARA REMOÇÃO";
                if(productList != null) {
                    if (productList.size() > 0) {
                        menssage = "FOI REMOVIDO " + productList.size() + " ITEMS";
                        productList = new ArrayList<>();
                        order.setProductList(productList);
                        userPrefManager.saveObject(UserPrefManager.ORDER, order);
                    }
                }
                Toast.makeText(getContext(), menssage, Toast.LENGTH_SHORT).show();
                loadOrder(order);
            }
        });

        User user = userPrefManager.loadObject(UserPrefManager.USER_KEY, User.class);
        if(user != null){
            nameUserTextView.setText(user.getName());
            emailUserTextView.setText(user.getEmail());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        userPrefManager.saveObject(UserPrefManager.ORDER, order);
    }

    private double getValues(List<Product> productList) {
        double result = 0;
        if(productList != null) for (Product product:productList) result += product.getPrice();
        return result;
    }

    public void loadOrder(Order _order) {

        order = _order;
        if (order == null) this.order = new Order();
        if (productList == null) this.productList = new ArrayList<>();

        if (orderAdapter != null) orderAdapter.notifyDataSetChanged();
        productList = order.getProductList();
        if (orderAdapter != null) orderAdapter.notifyDataSetChanged();

        statusTextView.setText("EDIÇÃO");

        valueItemsTextView.setText(("R$ " + getValues(productList)));

        if (productList != null) totalItemsTextView.setText((productList.size() + " ITEMS"));

        dateTextView.setText(new Date().toString());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(OrderViewModel.class);
        // TODO: Use the ViewModel
    }

}
