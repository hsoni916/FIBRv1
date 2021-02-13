package com.example.fibr;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.FIBR.R;

public class PermissionActivity extends AppCompatActivity {
    private final int[] availablepermission= new int[]{};
    SharedPreferences Permissions;
    SharedPreferences.Editor editor;
    boolean check = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Permissions = getSharedPreferences("Permissions",MODE_PRIVATE);
        check = Permissions.getBoolean("Permissions Set",false);
        editor = Permissions.edit();
        if(!check){
            setContentView(R.layout.accountontainer);
            SMS();
        }else{
            Intent signin = new Intent(getApplicationContext(), AccountContainer.class);
            signin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            signin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            signin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(signin);
        }
    }

    private void WriteStorage() {
        int permissioncheck = ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissioncheck!=PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showExplanation("Storage Permission", "Required for a crash logs and analytics.", Manifest.permission.WRITE_EXTERNAL_STORAGE, 4);
            }else{
                requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,4);
            }
        }
    }

    private void ReadStorage() {
        int permissioncheck = ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if(permissioncheck!=PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
                showExplanation("Storage Permission", "Required for sharing reports.", Manifest.permission.READ_EXTERNAL_STORAGE, 3);
            }else{
                requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,3);
            }
        }
    }

    private void Contacts() {
        int permissioncheck = ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_CONTACTS);
        if(permissioncheck!=PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                showExplanation("Contact Permission:", "Required for a better experience.", Manifest.permission.READ_CONTACTS, 2);
            }else{
                requestPermission(Manifest.permission.READ_CONTACTS,2);
            }
        }
    }

    private void SMS() {
        int permissioncheck = ContextCompat.checkSelfPermission(getBaseContext(), Manifest.permission.READ_SMS);
        if(permissioncheck!=PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_SMS)) {
                showExplanation("SMS Permission:", "To get OTP for verification.", Manifest.permission.READ_SMS, 1);
            }else{
                requestPermission(Manifest.permission.READ_SMS,1);
            }
        }
    }

    private void showExplanation(String title,
                                 String message,
                                 final String permission,
                                 final int permissionRequestCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        requestPermission(permission, permissionRequestCode);
                    }
                });
        builder.create().show();
    }

    private void requestPermission(String permission, int permissionRequestCode) {
            ActivityCompat.requestPermissions(this,
                    new String[]{permission}, permissionRequestCode);
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode,
            @NonNull String[] permissions,
            @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                editor.putBoolean("SMS", grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED);
                editor.putBoolean("Permisions Set",true);
                Contacts();
                break;
            case 2:
                editor.putBoolean("Contacts", grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED);
                editor.putBoolean("Permisions Set",true);
                ReadStorage();
                break;
            case 3:
                editor.putBoolean("Read Storage", grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED);
                editor.putBoolean("Permisions Set",true);
                WriteStorage();
            break;
            case 4:
                editor.putBoolean("Write Storage", grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED);
                editor.putBoolean("Permisions Set",true);
        }
        editor.apply();
    }
}
