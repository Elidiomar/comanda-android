package com.amostra.comanda.ui.views.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amostra.comanda.App;
import com.amostra.comanda.R;
import com.amostra.comanda.ui.viewModels.HomeViewModel;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        final SwipeRefreshLayout mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_home_fragment);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(App.getContext(), R.color.orange), ContextCompat.getColor(App.getContext(),R.color.colorPrimary), ContextCompat.getColor(App.getContext(),R.color.ic_launcher_background));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onCreate(new Bundle());
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        // TODO: Use the ViewModel
    }

}
