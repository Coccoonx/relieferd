package com.lri.eeclocalizer.uis.parishdetails;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.lri.eeclocalizer.R;


/**
 * Created by borelkoumo on 30/08/15.
 */
public class UserEditDetailsHolder extends RecyclerView.ViewHolder {

    CardView cv;
    TextView cardTitle;
    EditText editInfo;
    String inputType = "";
    Spinner sexSpinner;
    Context context;

    public UserEditDetailsHolder(Context context, View itemView) {
        super(itemView);
        this.context = context;
        cv = (CardView) itemView.findViewById(R.id.cv);
//        cardTitle = (TextView) itemView.findViewById(R.id.cardTitle);
//        editInfo = (EditText) itemView.findViewById(R.id.information);
//        sexSpinner = (Spinner) itemView.findViewById(R.id.sexSpinner);


    }

    class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//            ((ParishDetails) context).userInformationEditAdapter.userData
//                    .put("Sex", parent.getItemAtPosition(pos).toString());
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
        }
    }

}

