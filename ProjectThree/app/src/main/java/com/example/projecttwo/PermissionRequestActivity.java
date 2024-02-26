package com.example.projecttwo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * Author: Scott Martel
 * Date: 02/20/2024
 * Permission Request Activity controls SMS permission requests
 */
public class PermissionRequestActivity extends AppCompatActivity {

    private static final int SMS_PERMISSION_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission_request);

        Button requestPermissionButton = findViewById(R.id.requestPermissionButton);
        TextView permissionStatusText = findViewById(R.id.permissionStatusText);

        requestPermissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestSmsPermission();
            }
        });
    }

    private void requestSmsPermission() {
        String permission = Manifest.permission.SEND_SMS;
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission}, SMS_PERMISSION_CODE);
        } else {
            updatePermissionStatus("Permission already granted");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == SMS_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                updatePermissionStatus("SMS permission granted");
                // You can now send SMS
            } else {
                updatePermissionStatus("SMS permission denied");
                // Continue without SMS functionality
            }
        }
    }

    private void updatePermissionStatus(String status) {
        TextView permissionStatusText = findViewById(R.id.permissionStatusText);
        permissionStatusText.setText("Permission status: " + status);
    }
}
