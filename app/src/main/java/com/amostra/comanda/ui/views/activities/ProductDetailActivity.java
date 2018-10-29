package com.amostra.comanda.ui.views.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amostra.comanda.App;
import com.amostra.comanda.R;
import com.amostra.net.data.model.product.Order;
import com.amostra.net.data.model.product.Product;
import com.amostra.net.api.API;
import com.amostra.net.util.UserPrefManager;

import java.util.Objects;

public class ProductDetailActivity extends AppCompatActivity {

    public static final String USER_DETAIL = "user_detail";

    private TextView descriptionTextView, categoryTextView, priceTextView, titleTextView;
    private Button btnAddOrder;
    private   Toolbar toolbar;
    private UserPrefManager userPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userPrefManager = new UserPrefManager(App.getContext());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        descriptionTextView = findViewById(R.id.text_view_product_detail_description);
        categoryTextView = findViewById(R.id.text_view_product_detail_category);
        titleTextView = findViewById(R.id.text_view_product_detail_title);
        priceTextView = findViewById(R.id.text_view_product_detail_price);
        btnAddOrder = findViewById(R.id.btn_actDetails_save);


        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            Product product = API.fromJson(bundle.getString(USER_DETAIL), Product.class);
            loadProduct(product);
        }


    }

    private void loadProduct(final Product product) {
        setTitle(product.getTitle());
        descriptionTextView.setText(product.getDescription());
        categoryTextView.setText(("CATEGORIA "+ product.getCategoryCod()));
        titleTextView.setText(product.getTitle());
        priceTextView.setText(("R$ " + product.getPrice()));

        btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order order = userPrefManager.loadObject(UserPrefManager.ORDER, Order.class);
                order.addProduct(product);
                userPrefManager.saveObject(UserPrefManager.ORDER, order);
                Toast.makeText(getBaseContext(), product.getTitle() + " ADIONADO A COMANDA", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
