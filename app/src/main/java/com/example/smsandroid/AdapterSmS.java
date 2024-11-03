package com.example.smsandroid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AdapterSmS extends ArrayAdapter<SmS> {
    private TextView address, body, time;
    public AdapterSmS(Context context, List<SmS> items) {
        super(context, 0, items);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Kiểm tra nếu convertView chưa được tạo
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_sms, parent, false);
        }

        // Tìm các view con trong convertView
        address = convertView.findViewById(R.id.address_txt);
        body = convertView.findViewById(R.id.body_txt);
        time = convertView.findViewById(R.id.time_txt);

        SmS mySmS = getItem(position);
        if (mySmS != null) {
            address.setText(mySmS.getAddress());
            body.setText(mySmS.getBody());
            time.setText(mySmS.getTime());
        }

        return convertView;
    }



}
