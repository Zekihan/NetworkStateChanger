package com.nougui.networkstatechanger;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;


class TileDialog {

    public static AlertDialog getDialog(@NonNull final Context context) {

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

        final AlertDialog.Builder d = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View dialogView = inflater.inflate(R.layout.number_picker_dialog, null);
        d.setTitle("Network Changer");
        d.setMessage("Select to Network Type You Want");
        d.setView(dialogView);
        final NumberPicker numberPicker = dialogView.findViewById(R.id.numberPicker1);
        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(network_options.length - 1);
        numberPicker.setDisplayedValues(network_options);
        numberPicker.setValue(NetworkChanger.getPreferredNetwork(context));
        numberPicker.setWrapSelectorWheel(false);
        d.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                NetworkChanger.setPreferredNetwork(context, numberPicker.getValue());
            }
        });
        d.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });

        return d.create();
    }
}
