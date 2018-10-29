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
import com.amostra.comanda.ui.adapter.HistoricAdapter;
import com.amostra.net.data.model.product.Historic;
import com.amostra.comanda.ui.viewModels.HistoricViewModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoricFragment extends Fragment {

    private HistoricViewModel mViewModel;
    public HistoricAdapter historicAdapter;
    private List<Historic> historicList;
    protected LinearLayoutManager manager;
    protected RecyclerView mRecyclerView;

    public static HistoricFragment newInstance() {
        return new HistoricFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historic, container, false);

        final SwipeRefreshLayout mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout_historic_fragment);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(App.getContext(), R.color.orange), ContextCompat.getColor(App.getContext(),R.color.colorPrimary), ContextCompat.getColor(App.getContext(),R.color.ic_launcher_background));
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                onCreate(new Bundle());
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


        historicList = getHistorics();
        historicAdapter = new HistoricAdapter(getContext(), historicList);

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_fragment_historic);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(historicAdapter);
        mRecyclerView.setHasFixedSize(true);

        return view;
    }

    private List<Historic> getHistorics() {

        List<Historic> historics = new ArrayList<Historic>();
        historics.add(new Historic(0, "MANOEL", "SABOR DE FRANGO", 1, 1, new Date() ));
        historics.add(new Historic(1, "JOÃO", "TIO JOÃO", 1, 1, new Date() ));
        historics.add(new Historic(2, "LUCAS", "CARIOCA", 1, 1, new Date() ));
        historics.add(new Historic(3, "PEDRO", "BOVINA", 1, 1, new Date() ));
        return historics;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HistoricViewModel.class);
        // TODO: Use the ViewModel
    }

}
