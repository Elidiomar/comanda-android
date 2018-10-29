package com.amostra.comanda.ui.adapter;

import android.content.Context;
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

import com.amostra.comanda.R;
import com.amostra.net.data.model.product.Historic;
import com.amostra.comanda.util.Utils;

import java.util.List;

public class HistoricAdapter extends RecyclerView.Adapter<HistoricAdapter.ViewHolder> {

    private LayoutInflater mLayoutInflater;
    private List<Historic> historicList;
    Context context;
    private int sizeImgDetail, sizeImgActor;


   public HistoricAdapter(Context context, List<Historic> historicList) {
        super();
        this.historicList = historicList;
        this.context = context;
        sizeImgDetail = Utils.getPixels(180);
        sizeImgActor =  Utils.getPixels(50);
        mLayoutInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_recycler_view_historic, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            viewHolder.imgDetail.setImageResource(R.mipmap.logo_splash);
            viewHolder.txtDescription.setText(historicList.get(position).getDescription());
            viewHolder.txtDate.setText(historicList.get(position).getControl().toString());
            viewHolder.txtInfo.setText((historicList.get(position).getCategoryCod() + ""));
            viewHolder.txtTitle.setText((historicList.get(position).getTitle()+ ""));
            viewHolder.txtProgress.setText((historicList.get(position).getTypeProductCod() + ""));
    }

    @Override
    public void registerAdapterDataObserver(@NonNull RecyclerView.AdapterDataObserver observer) {

    }

    @Override
    public int getItemCount() {
        return historicList.size();
    }

   class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtInfo, txtTitle, txtDescription, txtProgress, txtDate;
        ImageView imgActor, imgDetail;
        Button btnDetails;
        ImageButton imbShare, imbLocation, imbSaveEvent;

        ViewHolder(View view) {
            super(view);
            txtInfo = view.findViewById(R.id.txt_crdProduct_info);
            txtTitle = view.findViewById(R.id.txt_crdProduct_title);
            txtDate = view.findViewById(R.id.txt_crdProduct_date);
            imgDetail = view.findViewById(R.id.img_layCardProduct_details);
            txtDescription = view.findViewById(R.id.txt_crdProduct_description);
            txtProgress = view.findViewById(R.id.txt_crdProduct_progress);
            imgActor = view.findViewById(R.id.img_crdProduct_icon);
            btnDetails = view.findViewById(R.id.btn_layCardProduct_details);
            imbLocation = view.findViewById(R.id.imb_layCardProduct_location);
            imbShare = view.findViewById(R.id.imb_layCardProduct_share);
            imbSaveEvent = view.findViewById(R.id.imb_layCardProduct_saveEvent);
            Log.i("ViewHolder", "ViewHolder() ");
        }
    }
}
