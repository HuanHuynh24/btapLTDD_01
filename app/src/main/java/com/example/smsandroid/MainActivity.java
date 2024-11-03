package com.example.smsandroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;

import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView txtAddress, txtBody, txtTime, number;
    List<SmS> sList = new ArrayList<SmS>();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_SMS}, 1);
        } else readSMS();
    }
    private void bindViews(){
        txtAddress = findViewById(R.id.address_txt);
        txtBody = findViewById(R.id.body_txt);
        txtTime = findViewById(R.id.time_txt);
        listView = findViewById(R.id.lv);
        number = findViewById(R.id.num);
    }
    private void readSMS() {
        Log.d("xin chao", "");
        ContentResolver contentResolver = getContentResolver();
        Uri uri = Uri.parse("content://sms/inbox");
        String[] projection = {
                "address",
                "body",
                "date"
        };
        Cursor cursor = contentResolver.query(uri, projection, null, null, "date DESC");
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String body = "", address = "", formattedDate ="";
                int k = cursor.getColumnIndex("address");
                if(k!=-1){
                      address = cursor.getString(k);
                }
                k = cursor.getColumnIndex("body");
                if(k!=-1){
                      body = cursor.getString(k);
                }
                k = cursor.getColumnIndex("date");
                if(k!=-1){
                    long date = cursor.getLong(k);
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
                      formattedDate = dateFormat.format(new Date(date));
                }
                sList.add(new SmS(address, body, formattedDate));
            }
            initView();
            cursor.close();
        }
    }

    private void initView() {
        AdapterSmS adapter = new AdapterSmS(
                this,
                sList
        );
        number.setText("Số lượng tin nhắn: "+ sList.size());
        // Gán adapter cho ListView
        listView.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    readSMS();
            } else {
                Toast.makeText(this, "Permission denied to read SMS", Toast.LENGTH_SHORT).show();
            }
        }
    }
}