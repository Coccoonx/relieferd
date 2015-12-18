package com.lri.eeclocalizer.uis.recipient.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lri.eeclocalizer.R;
import com.lri.eeclocalizer.core.model.Parish;
import com.lri.eeclocalizer.uis.recipient.viewholders.ParishViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyonnel on 05/11/15.
 */
public class ParishAdapter extends RecyclerView.Adapter<ParishViewHolder> {


    public static final int USER_1 = 0;
    public static final int USER_2 = 1;
    public static final int USER_3 = 2;
    public static final int USER_4 = 3;
    public static final int USER_5 = 4;
    public static final int USER_6 = 5;
    public static final int USER_7 = 6;
    public static final int USER_8 = 7;

    Context context;
    List<SettingsItem> settingsItems;


    public ParishAdapter(Context context) {
        this.context = context;
        settingsItems = initSettingsList();
    }

    @Override
    public ParishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_parish, null);
        ParishViewHolder cv = new ParishViewHolder(this.context, v, viewType);
        return cv;
    }

    @Override
    public void onBindViewHolder(ParishViewHolder holder, int position) {
        SettingsItem settingsItem = settingsItems.get(position);
        holder.id = settingsItem.id;
        try {
            holder.leftImageView.setImageBitmap(getRoundedCornerBitmap(settingsItem.leftIcon, 320));
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.title.setText(settingsItem.title);
        holder.settingsItem = settingsItem;
        //holder.leftImageView.setImageBitmap(settingsItem.bitmap);
//        ((ImageView) holder.rightViewDelete).setImageResource(R.drawable.ic_trashbin);
//        ((ImageView) holder.rightViewEdite).setImageResource(R.drawable.ic_edit);
        //((ImageView) holder.rightView).setColorFilter(Color.argb(255, 35, 154, 252));
    }

    @Override
    public int getItemCount() {
        return settingsItems.size();
    }

    private List<SettingsItem> initSettingsList() {

        ArrayList<SettingsItem> settingsItemsArrayList = new ArrayList<SettingsItem>();

        // Construct the list data
        for (Parish parish : Parish.values()) {
            settingsItemsArrayList.add(new SettingsItem(parish.getId(),
                    BitmapFactory.decodeResource(context.getResources(), R.drawable.eecplateau),
                    parish.getDisplayName()));
        }
        // return the list
        return settingsItemsArrayList;
    }

    public Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixel) {

        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(),
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);
        final int color = 0xffffffff;
        final Paint paint = new Paint();

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixel;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        bitmap.recycle();

        return output;

    }

    private Bitmap[] converterDrawableToBitmap(int[] leftIcons) {
        Bitmap[] bitmaps = new Bitmap[leftIcons.length];
        for (int i = 0; i < leftIcons.length; i++) {
            Bitmap output = BitmapFactory.decodeResource(context.getResources(), leftIcons[i]);
            bitmaps[i] = output;
        }
        return bitmaps;
    }

    public static class SettingsItem {
        public int id;
        //public int leftIcon;
        public Bitmap leftIcon;
        public String title;
        // public  Bitmap bitmap;

        public SettingsItem(int id, Bitmap leftIcon, String title) {
            this.id = id;
            this.leftIcon = leftIcon;
            this.title = title;


        }
    }

}
