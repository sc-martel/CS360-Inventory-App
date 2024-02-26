package com.example.projecttwo;

import android.telephony.SmsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.widget.Toast;

public class SmsUtility {
    public static void sendSmsMessage(Context context, String phoneNumber, String message) {

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, message, null, null);

        } else {

            Toast.makeText(context, "SMS not enabled: " + message, Toast.LENGTH_LONG).show();

        }
    }
}