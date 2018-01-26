package com.example.adenzahra.myapplication;

import android.content.SharedPreferences;

/**
 * Created by Aden Zahra on 1/25/2018.
 */

public class Globals {
    public static final String LOG_TAG = "MC_Database";

    public static final int NOTIFICATION_ID = 12344;
    public static final String NOTIFICATION_CHANNEL_ID="26342";
    public static final String NOTIFICATION_CHANNEL_NAME=LOG_TAG+"_NCHANNEL";

    //Request Codes Below
    public static final int WRITE_EXTERNAL_RCODE=5161;
    public static final int CAMERA_RCODE=1658;
    public static final int VIDEO_RCODE=4846;

    // Shared Preferences Keys Below
    public static final String FIRST_START="start";


}
