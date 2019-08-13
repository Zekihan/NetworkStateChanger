package com.nougui.networkstatechanger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import java.io.DataOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private final String[] WRITE_PERMISSION ={"android.permission.WRITE_SECURE_SETTINGS",
            "android.permission.MODIFY_PHONE_STATE"};

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        boolean permission = Check_Secure_Settings_Permission(this);
        if(!permission)
        {
            Process p;
            try {
                p = Runtime.getRuntime().exec("su");
                DataOutputStream os = new DataOutputStream(p.getOutputStream());
                for (String s :WRITE_PERMISSION
                ) {
                    os.writeBytes("pm grant "+mContext.getPackageName()+" "+s+"\n");
                    Log.e("Permission","added permission for "+s);
                }
                os.writeBytes("exit\n");
                os.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        final NumberPicker numberPicker1= findViewById(R.id.numberPicker1);
        final String[] network_options = {"GSM/WCDMA (WCDMA preferred)",
                "GSM only",
                "WCDMA only",
                "GSM/WCDMA (auto mode)",
                "CDMA/EvDo (auto mode)",
                "CDMA only",
                "EvDo only",
                "CDMA/EvDo/GSM/WCDMA",
                "CDMA+LTE/EvDo",
                "GSM/WCDMA/LTE",
                "General",
                "LTE Only mode",
                "LTE/WCDMA",
                "TDSCDMA only",
                "TDSCDMA/WCDMA",
                "LTE/TDSCDMA",
                "TDSCDMA/GSM",
                "LTE/TDSCDMA/GSM",
                "TDSCDMA/GSM/WCDMA",
                "LTE/TDSCDMA/WCDMA",
                "LTE/TDSCDMA/GSM/WCDMA",
                "TDSCDMA/CDMA/EvDo/GSM/WCDMA",
                "LTE/TDSCDMA/CDMA/EvDo/GSM/WCDMA"};

        numberPicker1.setMinValue(0);
        numberPicker1.setMaxValue(network_options.length - 1);
        numberPicker1.setDisplayedValues(network_options);
        numberPicker1.setValue(NetworkChanger.getPreferredNetwork(mContext));
        numberPicker1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        Button getSetting = findViewById(R.id.getSetting);
        getSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Before",(NetworkChanger.getPreferredNetwork(mContext))+"");
                NetworkChanger.setPreferredNetwork(mContext,numberPicker1.getValue());
                Log.e("Value",(numberPicker1.getValue())+"");
                Log.e("After",(NetworkChanger.getPreferredNetwork(mContext))+"");
            }
        });
    }
    private static boolean Check_Secure_Settings_Permission(@NonNull Activity act)
    {
        int result = ContextCompat.checkSelfPermission(act,android.Manifest.permission.WRITE_SECURE_SETTINGS);
        return result == PackageManager.PERMISSION_GRANTED;
    }
}