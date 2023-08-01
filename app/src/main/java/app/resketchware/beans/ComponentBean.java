package app.resketchware.beans;

import app.resketchware.R;

public class ComponentBean {
    public static final int COMPONENT_TYPE_INTENT = 1;
    public static final int COMPONENT_TYPE_SHAREDPREF = 2;
    public static final int COMPONENT_TYPE_CALENDAR = 3;
    public static final int COMPONENT_TYPE_VIBRATOR = 4;
    public static final int COMPONENT_TYPE_TIMERTASK = 5;
    public static final int COMPONENT_TYPE_FIREBASE = 6;
    public static final int COMPONENT_TYPE_DIALOG = 7;
    public static final int COMPONENT_TYPE_MEDIAPLAYER = 8;
    public static final int COMPONENT_TYPE_SOUNDPOOL = 9;
    public static final int COMPONENT_TYPE_OBJECTANIMATOR = 10;
    public static final int COMPONENT_TYPE_GYROSCOPE = 11;
    public static final int COMPONENT_TYPE_FIREBASE_AUTH = 12;
    public static final int COMPONENT_TYPE_INTERSTITIAL_AD = 13;
    public static final int COMPONENT_TYPE_FIREBASE_STORAGE = 14;
    public static final int COMPONENT_TYPE_CAMERA = 15;
    public static final int COMPONENT_TYPE_FILE_PICKER = 16;
    public static final int COMPONENT_TYPE_REQUEST_NETWORK = 17;
    public static final int COMPONENT_TYPE_TEXT_TO_SPEECH = 18;
    public static final int COMPONENT_TYPE_SPEECH_TO_TEXT = 19;
    public static final int COMPONENT_TYPE_BLUETOOTH_CONNECT = 20;
    public static final int COMPONENT_TYPE_LOCATION_MANAGER = 21;
    public static final int COMPONENT_TYPE_REWARDED_VIDEO_AD = 22;
    public static final int COMPONENT_TYPE_PROGRESS_DIALOG = 23;
    public static final int COMPONENT_TYPE_DATE_PICKER_DIALOG = 24;
    public static final int COMPONENT_TYPE_TIME_PICKER_DIALOG = 25;
    public static final int COMPONENT_TYPE_NOTIFICATION = 26;
    public static final int COMPONENT_TYPE_FRAGMENT_ADAPTER = 27;
    public static final int COMPONENT_TYPE_FIREBASE_AUTH_PHONE = 28;
    public static final int COMPONENT_TYPE_FIREBASE_DYNAMIC_LINKS = 29;
    public static final int COMPONENT_TYPE_FIREBASE_CLOUD_MESSAGE = 30;
    public static final int COMPONENT_TYPE_FIREBASE_AUTH_GOOGLE_LOGIN = 31;
    public static final int COMPONENT_TYPE_ONESIGNAL = 32;
    public static final int COMPONENT_TYPE_FACEBOOK_ADS_BANNER = 33;
    public static final int COMPONENT_TYPE_FACEBOOK_ADS_INTERSTITIAL = 34;

    public static int getIconResource(int type) {
        switch (type) {
            case COMPONENT_TYPE_INTENT:
                return R.drawable.widget_intent;

            case COMPONENT_TYPE_SHAREDPREF:
                return R.drawable.widget_shared_preference;

            case COMPONENT_TYPE_CALENDAR:
                return R.drawable.widget_calendar;

            case COMPONENT_TYPE_VIBRATOR:
                return R.drawable.widget_vibrator;

            case COMPONENT_TYPE_TIMERTASK:
                return R.drawable.widget_timer;

            case COMPONENT_TYPE_FIREBASE:
            case COMPONENT_TYPE_FIREBASE_AUTH:
            case COMPONENT_TYPE_FIREBASE_STORAGE:
                return R.drawable.widget_firebase;

            case COMPONENT_TYPE_DIALOG:
                return R.drawable.widget_alertdialog;

            case COMPONENT_TYPE_MEDIAPLAYER:
                return R.drawable.widget_mediaplayer;

            case COMPONENT_TYPE_SOUNDPOOL:
                return R.drawable.widget_soundpool;

            case COMPONENT_TYPE_OBJECTANIMATOR:
                return R.drawable.widget_objectanimator;

            case COMPONENT_TYPE_GYROSCOPE:
                return R.drawable.widget_gyroscope;

            case COMPONENT_TYPE_INTERSTITIAL_AD:
                return R.drawable.widget_admob;

            case COMPONENT_TYPE_CAMERA:
                return R.drawable.widget_camera;

            case COMPONENT_TYPE_FILE_PICKER:
                return R.drawable.widget_file; // Dome

            case COMPONENT_TYPE_REQUEST_NETWORK:
                return R.drawable.widget_network_request;

            case COMPONENT_TYPE_TEXT_TO_SPEECH:
                return R.drawable.widget_text_to_speech;

            case COMPONENT_TYPE_SPEECH_TO_TEXT:
                return R.drawable.widget_speech_to_text;

            case COMPONENT_TYPE_BLUETOOTH_CONNECT:
                return R.drawable.widget_bluetooth;

            case COMPONENT_TYPE_LOCATION_MANAGER:
                return R.drawable.widget_location;

            case COMPONENT_TYPE_REWARDED_VIDEO_AD:
                return R.drawable.widget_media_controller;

            case COMPONENT_TYPE_PROGRESS_DIALOG:
                return R.drawable.widget_progress_dialog;

            case COMPONENT_TYPE_DATE_PICKER_DIALOG:
                return R.drawable.widget_date_picker_dialog;

            case COMPONENT_TYPE_TIME_PICKER_DIALOG:
                return R.drawable.widget_time_picker_dialog;

            case COMPONENT_TYPE_NOTIFICATION:
                return R.drawable.widget_notification;

            case COMPONENT_TYPE_FRAGMENT_ADAPTER:
                return R.drawable.widget_fragment;

            case COMPONENT_TYPE_FIREBASE_AUTH_PHONE:
                return R.drawable.widget_phone_auth;

            case COMPONENT_TYPE_FIREBASE_DYNAMIC_LINKS:
                return R.drawable.component_dynamic_link;

            case COMPONENT_TYPE_FIREBASE_CLOUD_MESSAGE:
                return R.drawable.component_fcm;

            case COMPONENT_TYPE_FIREBASE_AUTH_GOOGLE_LOGIN:
                return R.drawable.component_firebase_google;

            case COMPONENT_TYPE_ONESIGNAL:
                return R.drawable.component_firebase_admin;

            case COMPONENT_TYPE_FACEBOOK_ADS_BANNER:
                return R.drawable.component_fbads_banner;

            case COMPONENT_TYPE_FACEBOOK_ADS_INTERSTITIAL:
                return R.drawable.component_fbads_interstitial;

            default:
                return R.drawable.color_new_96;
        }
    }
}
