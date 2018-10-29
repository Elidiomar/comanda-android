package com.amostra.comanda.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amostra.comanda.R;
import com.amostra.net.data.model.product.Order;
import com.amostra.net.data.model.product.Product;
import com.amostra.comanda.ui.views.activities.ProductDetailActivity;
import com.amostra.comanda.ui.views.fragments.OrderFragment;
import com.amostra.net.api.API;
import com.amostra.net.util.UserPrefManager;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Product> productArrayList;
    Context context;
    private OrderFragment orderFragment;
    private UserPrefManager userPrefManager;
    private Order order;

    public OrderAdapter(Context context, List<Product> productArrayList, OrderFragment orderFragment) {
        super();
        this.productArrayList = productArrayList;
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        userPrefManager = new UserPrefManager(context);
        order = userPrefManager.loadObject(UserPrefManager.ORDER, Order.class);
        this.orderFragment =orderFragment;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_recycler_view_order, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        final Product product =  productArrayList.get(position);

            viewHolder.titleTextView.setText(product.getTitle());
            viewHolder.dateTextView.setText(product.getControl().toString());
            viewHolder.categoryTextView.setText(("Categoria " + product.getCategoryCod()));
            viewHolder.priceTextView.setText(("R$ " + product.getPrice()));

            viewHolder.detailImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detailProduct(product);
                }
            });

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    detailProduct(product);
                }
            });

            viewHolder.removeActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeProduct(product, position);
                    notifyItemRemoved(position);
                    OrderAdapter.this.notify();
                }
            });

    }

    private void removeProduct(Product product, int position) {
        productArrayList.remove(product);
        order.setProductList(productArrayList);
        userPrefManager.saveObject(UserPrefManager.ORDER, order);
        orderFragment.loadOrder(order);
        Toast.makeText(context, product.getTitle(), Toast.LENGTH_SHORT).show();
    }

    private void detailProduct(Product product) {
        Bundle bundle = new Bundle();
        bundle.putString(ProductDetailActivity.USER_DETAIL, API.toJson(product));
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {

    }

    @Override
    public int getItemCount() {
       if (productArrayList != null) return productArrayList.size();
       else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView, priceTextView, categoryTextView, dateTextView;
        ImageView productImageView;
        Button removeActionButton;
        ImageButton shareImageButton, detailImageButton, saveImageButton;

        ViewHolder(View view) {
            super(view);

            titleTextView = view.findViewById(R.id.text_view_item_order_title);
            priceTextView = view.findViewById(R.id.text_view_item_order_price);
            categoryTextView = view.findViewById(R.id.text_view_item_order_category);
            dateTextView = view.findViewById(R.id.text_view_item_order_date);

            productImageView = view.findViewById(R.id.image_view_item_order_logo);

            removeActionButton = view.findViewById(R.id.button_view_item_order_remove);

            shareImageButton = view.findViewById(R.id.image_button_item_order_share);
            detailImageButton = view.findViewById(R.id.image_button_item_order_detail);
            saveImageButton = view.findViewById(R.id.image_button_item_order_save);

            Log.i("ViewHolder", "ViewHolder() ");
        }
    }
}
