package com.example.androidarshinsky162;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 11;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 12;

    private EditText phonenumerEt;
    private EditText smsEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phonenumerEt = findViewById(R.id.phoneCallEt);
        smsEt = findViewById(R.id.smsEt);

        findViewById(R.id.callBut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callByNumber(phonenumerEt.getText().toString());

            }
        });
        findViewById(R.id.sendBut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendByNumber(phonenumerEt.getText().toString(),smsEt.getText().toString());
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    callByNumber(phonenumerEt.getText().toString());
                } else {
                    Toast.makeText(MainActivity.this,getString(R.string.permissionCall),Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    sendByNumber(phonenumerEt.getText().toString(),smsEt.getText().toString());
                } else {
                    Toast.makeText(MainActivity.this,getString(R.string.permissionSMS),Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }
    private void sendByNumber(String number, String content) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    MY_PERMISSIONS_REQUEST_SEND_SMS);
        } else {
            SmsManager smgr = SmsManager.getDefault();
            smgr.sendTextMessage(
                    number,
                    null,
                    content,
                    null,
                    null);
        }
    }
    private void callByNumber(String number) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            Uri uri = Uri.parse("tel:" + number);
            Intent dialIntent = new Intent(Intent.ACTION_CALL, uri);
            startActivity(dialIntent);
        }
    }
}
