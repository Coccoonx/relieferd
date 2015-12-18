package com.lri.eeclocalizer.uis.parishdetails;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.lri.eeclocalizer.R;
import com.lri.eeclocalizer.utils.UIUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Created by Lyonnel T. DZOTANG on 15/07/2015.
 */
public class UserInformationEditAdapter extends RecyclerView.Adapter<UserEditDetailsHolder> {

    public Map<String, String> userData = new HashMap<String, String>();
    List<UserDetails> userDetailsList;
    Context mContext;

    public UserInformationEditAdapter(Context context, List<UserDetails> userDetailsList) {
        super();
        mContext = context;
        this.userDetailsList = userDetailsList;
    }

    @Override
    public UserEditDetailsHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardview_information_displayer, viewGroup, false);
        UserEditDetailsHolder pvh = new UserEditDetailsHolder(mContext, v);
        return pvh;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(UserEditDetailsHolder userDetailsHolder, final int i) {
        // Setting font
        UIUtils.setFont(UIUtils.Font.REGULAR, userDetailsHolder.cardTitle);
        UIUtils.setFont(UIUtils.Font.LIGHT, userDetailsHolder.editInfo);

        UserDetails userDetails = userDetailsList.get(i);
        Log.d("safer user item", "Item " + i + " :" + userDetails);

        userDetailsHolder.cardTitle.setText(userDetails.getCardTitle());
        userDetailsHolder.editInfo.setText(userDetails.getInformation());
        userDetailsHolder.inputType = userDetails.getType();

        if (userDetails.getType().equals("phone")) {
            (userDetailsHolder.editInfo).setInputType(InputType.TYPE_CLASS_PHONE);
        } else if (userDetails.getType().equals("text")) {
            (userDetailsHolder.editInfo).setInputType(InputType.TYPE_CLASS_TEXT);
        } else if (userDetails.getType().equals("email")) {
            (userDetailsHolder.editInfo).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS);
        } else if (userDetails.getType().equals("spinner")) {
            (userDetailsHolder.sexSpinner).setVisibility(View.VISIBLE);
            (userDetailsHolder.editInfo).setVisibility(View.GONE);

            // Setting the default value of the spinner
            ArrayAdapter myAdap = (ArrayAdapter) (userDetailsHolder.sexSpinner).getAdapter();
            int spinnerPosition = myAdap.getPosition(userDetails.getInformation());
            (userDetailsHolder.sexSpinner).setSelection(spinnerPosition);
        } else {
            (userDetailsHolder.editInfo).setFocusable(false);
            (userDetailsHolder.editInfo).setClickable(false);
        }


        userDetailsHolder.editInfo.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                //setting data to array, when changed
//                texts[position] = s.toString();
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                userData.put(userDetailsList.get(i).getCardTitle(), s.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        if (userDetailsList != null)
            return userDetailsList.size();
        else
            return 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
