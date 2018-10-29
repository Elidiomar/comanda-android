package com.amostra.comanda.util;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import com.amostra.comanda.R;
import com.amostra.comanda.ui.model.MenuDrawerItem;
import com.amostra.comanda.ui.views.fragments.HistoricFragment;
import com.amostra.comanda.ui.views.fragments.HomeFragment;
import com.amostra.comanda.ui.views.fragments.OrderFragment;
import com.amostra.comanda.ui.views.fragments.ProductFragment;
import com.amostra.comanda.ui.viewModels.ProductViewModel;
import com.amostra.comanda.ui.views.fragments.ProfileFragment;
import com.amostra.comanda.ui.views.fragments.SearchFragment;

import java.util.ArrayList;
import java.util.List;

public class ManagerDrawerFragments {

    private Context context;
    private int oldPosition;
    private List<MenuDrawerItem> menuDrawerItems;

    public ManagerDrawerFragments(Context context) {
        this.context = context;
        setMenuDrawerItems(initMenuItems());
    }

    private List<MenuDrawerItem> initMenuItems() {

        int colorImage = ContextCompat.getColor(context, R.color.text_color_items);

        List<MenuDrawerItem> listMenu = new ArrayList<>();
        listMenu.add(new MenuDrawerItem(R.integer.search_menu_id,context.getString(R.string.title_search).toUpperCase(), R.drawable.ic_action_search, colorImage, SearchFragment.class));
        listMenu.add(new MenuDrawerItem(R.integer.home_menu_id,context.getString(R.string.title_home).toUpperCase(), R.drawable.ic_menu_home, colorImage, HomeFragment.class));
        listMenu.add(new MenuDrawerItem(R.integer.product_menu_id,context.getString(R.string.title_product).toUpperCase(), R.drawable.ic_menu_product, colorImage, ProductFragment.class));
        listMenu.add(new MenuDrawerItem(R.integer.order_menu_id,context.getString(R.string.title_order).toUpperCase(), R.drawable.ic_menu_order, colorImage, OrderFragment.class));
        listMenu.add(new MenuDrawerItem(R.integer.historic_menu_id,context.getString(R.string.title_historic).toUpperCase(), R.drawable.ic_menu_profile, colorImage, HistoricFragment.class));
        listMenu.add(new MenuDrawerItem(R.integer.profile_menu_id,context.getString(R.string.title_profile).toUpperCase(), R.drawable.ic_menu_profile, colorImage, ProfileFragment.class));

        return listMenu;
    }

    public Fragment getFragment(int position, boolean reloadFragment, Bundle bundle) throws InstantiationException, IllegalAccessException {

        Fragment fragment = null;
        if (position == oldPosition && !reloadFragment) return fragment;

        oldPosition = position;

        switch (position) {
            case R.integer.header_menu_id: return fragment;

            case R.integer.search_menu_id:
                fragment = new SearchFragment();
                fragment.setArguments(bundle);
                break;

            case R.integer.home_menu_id:
                fragment = HomeFragment.class.newInstance();
                fragment.setArguments(bundle);
                break;

            case R.integer.product_menu_id:
                fragment = new ProductFragment();
                fragment.setArguments(bundle);
                break;

            case R.integer.order_menu_id:
                fragment = new OrderFragment();
                fragment.setArguments(bundle);
                break;

            case R.integer.profile_menu_id:
                fragment = new ProfileFragment();
                fragment.setArguments(bundle);
                break;

        }

        return fragment;
    }

    private int finItemPositionByMenuId(int menuId) {
        int len = menuDrawerItems.size();
        for (int i = 0; i < len; i++) {
            if (menuDrawerItems.get(i).getId() == menuId) {
                return i;
            }
        }
        return oldPosition;
    }

    public List<MenuDrawerItem> getMenuDrawerItems() {
        return menuDrawerItems;
    }

    public void setMenuDrawerItems(List<MenuDrawerItem> menuDrawerItems) {
        this.menuDrawerItems = menuDrawerItems;
    }
}
