package com.amostra.comanda.ui.views.activities;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amostra.comanda.App;

import com.amostra.comanda.R;
import com.amostra.comanda.service.NetworkChangeReceiver;
import com.amostra.comanda.ui.adapter.MenuDrawerAdapter;
import com.amostra.comanda.ui.model.MenuDrawerItem;
import com.amostra.comanda.ui.views.fragments.HomeFragment;
import com.amostra.comanda.util.ManagerDrawerFragments;
import com.amostra.net.api.LoginAPI;
import com.amostra.net.api.ProductAPI;
import com.amostra.net.data.model.Login;
import com.amostra.net.data.model.User;
import com.amostra.net.data.model.UserResponse;
import com.amostra.net.data.model.product.Product;
import com.amostra.net.util.UserPrefManager;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MenuDrawerAdapter.OnItemClickListener  {

    //private TextView mTextMessage;

    //private TextView mIdPK,mIdUsername,mIdEmail,mIdToken;

    //private PrefManager prefManager;

    private RelativeLayout networkStatusLayout;
    private NetworkChangeReceiver networkChangeReceiver;
    private List<Class> fragmentList;

    private static final String TAG = "MainActivity";
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    private RecyclerView recyclerViewDrawer;
    private CharSequence drawerTitle;
    private CharSequence activityTitle;
    private List<MenuDrawerItem> menuDrawerItems;
    private ManagerDrawerFragments managerDrawerFragments;
    private MenuDrawerAdapter menuDrawerAdapter;
    private int fragmentSelectIdOld = 1;

    private View viewHomeSelectionBar;
    private View viewProductSelectionBar;
    private View viewOrderSelectionBar;
    private View viewHistoricSelectionBar;

    private TextView titleToolbar;

    private ProductAPI productAPI;
    private UserPrefManager userPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        titleToolbar = findViewById(R.id.text_view_title_toolbar);

        productAPI = App.getInstance().getApi().apiProducts();
        userPrefManager = new UserPrefManager(getBaseContext());

        initNetworkBroadcast();

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        Drawable upArrow = ContextCompat.getDrawable(this, R.drawable.ic_action_menu);
        Objects.requireNonNull(upArrow).setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        toolbar.setNavigationIcon(upArrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        drawerLayout.setScrimColor(ContextCompat.getColor(this, R.color.white_alpha_bk));
        actionBarDrawerToggle.syncState();


        managerDrawerFragments = new ManagerDrawerFragments(getBaseContext());
        menuDrawerItems = managerDrawerFragments.getMenuDrawerItems();
        menuDrawerAdapter = new MenuDrawerAdapter(getBaseContext(), menuDrawerItems, (MenuDrawerAdapter.OnItemClickListener) this);

        recyclerViewDrawer = findViewById(R.id.recycler_view_main_activity_drawer);
        recyclerViewDrawer.setHasFixedSize(true);
        recyclerViewDrawer.setNestedScrollingEnabled(false);
        recyclerViewDrawer.setAdapter(menuDrawerAdapter);

        loadUser();

        configBarBottom();

        if (savedInstanceState == null) gotoFragment(menuDrawerItems.get(fragmentSelectIdOld));

        // CLICK LOGO MRV - TOOLBAR
        titleToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoFragment(menuDrawerItems.get(1));
            }
        });


        //mTextMessage = (TextView) findViewById(R.id.message);

        //ActionBar actionBar = getSupportActionBar();
        //if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        /*mIdPK = (TextView) findViewById(R.id.tv_id_pk);
        mIdUsername = (TextView) findViewById(R.id.tv_id_username);
        mIdEmail = (TextView) findViewById(R.id.tv_id_email);
        mIdToken = (TextView) findViewById(R.id.tv_id_token);*/


        /*prefManager = new PrefManager(this);

        mIdPK.setText(prefManager.getIdPk());
        mIdUsername.setText(prefManager.getIdUsername());
        mIdEmail.setText(prefManager.getIdEmail());
        mIdToken.setText(prefManager.getIdToken());*/


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void loadUser() {
        User user = new UserPrefManager(getBaseContext()).loadObject(UserPrefManager.USER_KEY, User.class);
        TextView textViewEmail= findViewById(R.id.text_view_item_drawer_email);
        textViewEmail.setText(user.getEmail());
        TextView textViewName= findViewById(R.id.text_view_item_drawer_name);
        textViewName.setText(user.getName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        getApi();
    }

    @Override
    public void onBackPressed() {
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (fragmentSelectIdOld != R.integer.home_menu_id)
                gotoFragmentByIdMenu(R.integer.home_menu_id);
            else super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                gotoFragmentByIdMenu(R.integer.search_menu_id);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view, MenuDrawerItem menuDrawerItem) {
        boolean success = gotoFragment(menuDrawerItem);
    }

    public void gotoFragmentByIdMenu(int idMenu) {
        gotoFragment(menuDrawerAdapter.getMenuById(idMenu));
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetworkBroadcast();
    }

    private boolean gotoFragment(MenuDrawerItem menuDrawerItem) {
        if (menuDrawerItem.getId() != fragmentSelectIdOld) {

            try {
                Fragment fragment = (Fragment) menuDrawerItem.getFragment().newInstance();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.fragment_content, fragment).commitAllowingStateLoss();
                Log.i(TAG, "gotoFragment: " + menuDrawerItem.getFragment());

                menuDrawerAdapter.setSelectedPositionById(menuDrawerItem.getId());
                menuDrawerAdapter.notifyDataSetChanged();
                fragmentSelectIdOld = menuDrawerItem.getId();
                titleToolbar.setText(menuDrawerItem.getTitle());
                selectFragmentBottomBar(menuDrawerItem.getId());

            } catch (Exception e) {
                Log.e(TAG, "gotoFragment: " + e.getMessage());
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initNetworkBroadcast() {
        networkStatusLayout = findViewById(R.id.relative_layout_main_activity_network);
        //animate = new TranslateAnimation(0, 0, 0, networkStatusLayout.getHeight());
        //animate.setDuration(500);
        //    animate.setFillAfter(true);
        networkChangeReceiver = new NetworkChangeReceiver();

        registerNetworkBroadcast();
    }

    private void registerNetworkBroadcast() {
        try {
            registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    protected void unregisterNetworkBroadcast() {
        try {
            unregisterReceiver(networkChangeReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void networkStatusChange() {
        if (App.isConnected()) {
            //networkStatusLayout.setAnimation(animate);
            //networkStatusLayout.animate();
            networkStatusLayout.setVisibility(View.GONE);
        } else {
            //networkStatusLayout.clearAnimation();
            networkStatusLayout.setVisibility(View.VISIBLE);
        }
    }

    private void configBarBottom() {

        viewHomeSelectionBar = findViewById(R.id.view_layout_bottom_bar_home);
        viewProductSelectionBar = findViewById(R.id.view_layout_bottom_bar_product);
        viewOrderSelectionBar = findViewById(R.id.view_layout_bottom_bar_order);
        viewHistoricSelectionBar = findViewById(R.id.view_layout_bottom_bar_historic);

        findViewById(R.id.linear_Layout_menu_footer_home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoFragmentByIdMenu(R.integer.home_menu_id);
            }
        });

        findViewById(R.id.linear_Layout_menu_footer_product).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoFragmentByIdMenu(R.integer.product_menu_id);
            }
        });

        findViewById(R.id.linear_Layout_menu_footer_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoFragmentByIdMenu(R.integer.order_menu_id);
            }
        });

        findViewById(R.id.linear_Layout_menu_footer_historic).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoFragmentByIdMenu(R.integer.historic_menu_id);
            }
        });
    }

    private void selectFragmentBottomBar(int fragment) {

        if (viewHomeSelectionBar != null) {
            viewHomeSelectionBar.setVisibility(View.INVISIBLE);
            if (fragment == R.integer.home_menu_id)
                viewHomeSelectionBar.setVisibility(View.VISIBLE);
        }

        if (viewProductSelectionBar != null) {
            viewProductSelectionBar.setVisibility(View.INVISIBLE);
            if (fragment == R.integer.product_menu_id)
                viewProductSelectionBar.setVisibility(View.VISIBLE);
        }

        if (viewOrderSelectionBar != null) {
            viewOrderSelectionBar.setVisibility(View.INVISIBLE);
            if (fragment == R.integer.order_menu_id)
                viewOrderSelectionBar.setVisibility(View.VISIBLE);
        }

        if (viewHistoricSelectionBar != null) {
            viewHistoricSelectionBar.setVisibility(View.INVISIBLE);
            if (fragment == R.integer.historic_menu_id)
                viewHistoricSelectionBar.setVisibility(View.VISIBLE);
        }

    }

    private void getApi() {
        List<Product> products;
        products = userPrefManager.loadListProducts(UserPrefManager.PRODUCTS);
        if (products == null) getProducts();
        else if (products.size() == 0) getProducts();
        else getProductsAsync();

    }


    private void getProducts() {

        try {

            Call<List<Product>> call1 = productAPI.getProducts();
            userPrefManager.saveObject(UserPrefManager.PRODUCTS, call1.execute().body());

        } catch (Exception e) {
            e.printStackTrace();
            //Log.i(TAG, e.getMessage());
        }

    }

    private void getProductsAsync() {

        try {

            Call<List<Product>> call1 = productAPI.getProducts();

            Log.i(TAG, "call1");
            call1.enqueue(new Callback<List<Product>>() {
                @Override
                public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {

                    if (response.isSuccessful()) {

                        Log.i(TAG,"onResponse Success: " + response.body());
                        userPrefManager.saveObject(UserPrefManager.PRODUCTS, response.body());
                    } else {

                        Log.i(TAG,"onResponse Error: " + response.raw() );
                    }
                }

                @Override
                public void onFailure(Call<List<Product>> call, Throwable t) {
                    call.cancel();
                    Toast.makeText(getBaseContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                    Log.i(TAG, t.getMessage());
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, e.getMessage());
        }

    }

}
