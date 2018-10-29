/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.amostra.comanda.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amostra.comanda.R;
import com.amostra.comanda.ui.model.MenuDrawerItem;

import java.util.List;

public class MenuDrawerAdapter extends RecyclerView.Adapter<MenuDrawerAdapter.ViewHolder> {

    public List<MenuDrawerItem> menuDrawerItems;
    private OnItemClickListener onItemClickListener;
    MenuDrawerItem item;
    private Context context;

    private int selectedPosition;
    public MenuDrawerAdapter(Context context, List<MenuDrawerItem> menuDrawerItems, OnItemClickListener onItemClickListener) {
        this.menuDrawerItems = menuDrawerItems;
        this.onItemClickListener = onItemClickListener;
        this.context = context;
    }


    public interface OnItemClickListener {
        public void onClick(View view, MenuDrawerItem menuDrawerItem);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_recycler_view_menu_drawer, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        item = menuDrawerItems.get(position);

        holder.textViewTitulo.setText(item.getTitle());
        holder.imageViewIcon.setImageResource(item.getImageIconResource());
        holder.imageViewIcon.setColorFilter(item.getColorImage());

        if (selectedPosition == position) {
            holder.viewIndicator.setVisibility(View.VISIBLE);
        } else {
            holder.viewIndicator.setVisibility(View.INVISIBLE);
        }

        Log.i("Notify", "onBindViewHolder: " + item.getFragment());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onClick(view, menuDrawerItems.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuDrawerItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView textViewTitulo;
        public final ImageView imageViewIcon;
        public final View viewIndicator;

        public ViewHolder(View view) {
            super(view);
            textViewTitulo = (TextView) view.findViewById(R.id.text_view_item_drawer);
            imageViewIcon = (ImageView) view.findViewById(R.id.image_view_item_drawer);
            viewIndicator = (View) view.findViewById(R.id.itemIndicator);
        }
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public int getSelectedPositionById(int id) {
        for (int i = 0; i < getItemCount(); i++) if (menuDrawerItems.get(i).getId() == id) return i;
        return selectedPosition;
    }

    public void setSelectedPositionById(int id) {
        for (int i = 0; i < getItemCount(); i++) if (menuDrawerItems.get(i).getId() == id) this.selectedPosition = i;
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
    }

    public MenuDrawerItem getMenuById(int idMenu) {
        for (int i = 0; i < getItemCount(); i++){
            if (menuDrawerItems.get(i).getId() == idMenu) return menuDrawerItems.get(i) ;
        }
        return menuDrawerItems.get(0);
    }
}
