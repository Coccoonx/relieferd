package com.lri.eeclocalizer.uis.recipient.viewholders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lri.eeclocalizer.R;
import com.lri.eeclocalizer.uis.recipient.adapters.ParishAdapter;


/**
 * Created by lyonnel on 05/11/15.
 */
public class ParishViewHolder extends RecyclerView.ViewHolder {

    public int id;
    public Context context;
    public ImageView leftImageView;
    public TextView title;
    public View rightViewDelete;
    public View rightViewEdite;
    public Boolean isSelected = false;

    public ParishAdapter.SettingsItem settingsItem;


    public ParishViewHolder(final Context context, View view, int viewType) {
        super(view);
        this.context = context;
        this.leftImageView = (ImageView) view.findViewById(R.id.leftIcon);
        this.title = (TextView) view.findViewById(R.id.title);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // View parent = (View)v.getParent();
            }
        });


    }


}
