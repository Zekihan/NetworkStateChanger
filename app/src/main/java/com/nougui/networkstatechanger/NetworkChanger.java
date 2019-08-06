package com.nougui.networkstatechanger;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.Settings;

import java.io.DataOutputStream;
import java.io.IOException;

class NetworkChanger {

    static ContentResolver cr;

    public static int getPreferredNetwork(Context context) {
        cr = context.getContentResolver();
        return Integer.parseInt(Settings.Global.getString(cr, "preferred_network_mode1"));
    }

    public static void setPreferredNetwork(final Context context, int value) {
        cr = context.getContentResolver();
        android.provider.Settings.Global.putString(cr, "preferred_network_mode1", String.valueOf(value));

    }

    public static int getMobileDataState(Context context) {
        cr = context.getContentResolver();
        return Integer.parseInt(Settings.Global.getString(cr, "mobile_data1"));
    }

    public static void setMobileDataState(Context context, int value) {
        cr = context.getContentResolver();
        Settings.Global.putString(cr, "mobile_data1", String.valueOf(value));
    }

    public static int getAirplaneMode(Context context) {
        cr = context.getContentResolver();
        return Integer.parseInt(Settings.Global.getString(cr, "airplane_mode_on"));
    }

    public static void setAirplaneMode(Context context, int value) {
        cr = context.getContentResolver();
        Settings.Global.putString(cr, "airplane_mode_on", String.valueOf(value));
    }

    public static String getPreferredNetworkName(int network) {
        String preferredNetwork;
        switch (network) {
            case 0:
                preferredNetwork = "GSM/WCDMA (WCDMA preferred)";
                break;
            case 1:
                preferredNetwork = "GSM only";
                break;
            case 2:
                preferredNetwork = "WCDMA only";
                break;
            case 3:
                preferredNetwork = "GSM/WCDMA (auto mode)";
                break;
            case 4:
                preferredNetwork = "CDMA/EvDo (auto mode)";
                break;
            case 5:
                preferredNetwork = "CDMA only";
                break;
            case 6:
                preferredNetwork = "EvDo only";
                break;
            case 7:
                preferredNetwork = "CDMA/EvDo/GSM/WCDMA";
                break;
            case 8:
                preferredNetwork = "CDMA+LTE/EvDo";
                break;
            case 9:
                preferredNetwork = "GSM/WCDMA/LTE";
                break;
            case 10:
                preferredNetwork = "General";
                break;
            case 11:
                preferredNetwork = "LTE Only mode";
                break;
            case 12:
                preferredNetwork = "LTE/WCDMA";
                break;
            case 13:
                preferredNetwork = "TDSCDMA only";
                break;
            case 14:
                preferredNetwork = "TDSCDMA/WCDMA";
                break;
            case 15:
                preferredNetwork = "LTE/TDSCDMA";
                break;
            case 16:
                preferredNetwork = "TDSCDMA/GSM";
                break;
            case 17:
                preferredNetwork = "LTE/TDSCDMA/GSM";
                break;
            case 18:
                preferredNetwork = "TDSCDMA/GSM/WCDMA";
                break;
            case 19:
                preferredNetwork = "LTE/TDSCDMA/WCDMA";
                break;
            case 20:
                preferredNetwork = "LTE/TDSCDMA/GSM/WCDMA";
                break;
            case 21:
                preferredNetwork = "TDSCDMA/CDMA/EvDo/GSM/WCDMA";
                break;
            case 22:
                preferredNetwork = "LTE/TDSCDMA/CDMA/EvDo/GSM/WCDMA";
                break;
            default:
                preferredNetwork = String.valueOf(network);
                break;
        }
        return preferredNetwork;
    }

    public static void broadcastNetwork(int value) {
        String s = "am broadcast -a com.android.internal.telephony.MODIFY_NETWORK_MODE --ei networkMode *" + value + "*\n";
        try {
            Process su = Runtime.getRuntime().exec("su");
            DataOutputStream outputStream = new DataOutputStream(su.getOutputStream());

            outputStream.writeBytes(s + "\n");
            outputStream.flush();

            outputStream.writeBytes("exit\n");
            outputStream.flush();
            su.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


