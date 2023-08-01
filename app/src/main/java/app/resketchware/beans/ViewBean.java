package app.resketchware.beans;

import app.resketchware.R;

public class ViewBean {

    public static final int VIEW_TYPE_LAYOUT_LINEAR = 0;
    public static final int VIEW_TYPE_LAYOUT_RELATIVE = 1;
    public static final int VIEW_TYPE_LAYOUT_HSCROLLVIEW = 2;
    public static final int VIEW_TYPE_WIDGET_BUTTON = 3;
    public static final int VIEW_TYPE_WIDGET_TEXTVIEW = 4;
    public static final int VIEW_TYPE_WIDGET_EDITTEXT = 5;
    public static final int VIEW_TYPE_WIDGET_IMAGEVIEW = 6;
    public static final int VIEW_TYPE_WIDGET_WEBVIEW = 7;
    public static final int VIEW_TYPE_WIDGET_PROGRESSBAR = 8;
    public static final int VIEW_TYPE_WIDGET_LISTVIEW = 9;
    public static final int VIEW_TYPE_WIDGET_SPINNER = 10;
    public static final int VIEW_TYPE_WIDGET_CHECKBOX = 11;
    public static final int VIEW_TYPE_LAYOUT_VSCROLLVIEW = 12;
    public static final int VIEW_TYPE_WIDGET_SWITCH = 13;
    public static final int VIEW_TYPE_WIDGET_SEEKBAR = 14;
    public static final int VIEW_TYPE_WIDGET_CALENDARVIEW = 15;
    public static final int VIEW_TYPE_WIDGET_FAB = 16;
    public static final int VIEW_TYPE_WIDGET_ADVIEW = 17;
    public static final int VIEW_TYPE_WIDGET_MAPVIEW = 18;
    public static final int VIEW_TYPE_COUNT = 19;
    public static final int VIEW_TYPE_WIDGET_RADIOBUTTON = 19;
    public static final int VIEW_TYPE_WIDGET_RATINGBAR = 20;
    public static final int VIEW_TYPE_WIDGET_VIDEOVIEW = 21;
    public static final int VIEW_TYPE_WIDGET_SEARCHVIEW = 22;
    public static final int VIEW_TYPE_WIDGET_AUTOCOMPLETETEXTVIEW = 23;
    public static final int VIEW_TYPE_WIDGET_MULTIAUTOCOMPLETETEXTVIEW = 24;
    public static final int VIEW_TYPE_WIDGET_GRIDVIEW = 25;
    public static final int VIEW_TYPE_WIDGET_ANALOGCLOCK = 26;
    public static final int VIEW_TYPE_WIDGET_DATEPICKER = 27;
    public static final int VIEW_TYPE_WIDGET_TIMEPICKER = 28;
    public static final int VIEW_TYPE_WIDGET_DIGITALCLOCK = 29;
    public static final int VIEW_TYPE_LAYOUT_TABLAYOUT = 30;
    public static final int VIEW_TYPE_LAYOUT_VIEWPAGER = 31;
    public static final int VIEW_TYPE_LAYOUT_BOTTOMNAVIGATIONVIEW = 32;
    public static final int VIEW_TYPE_WIDGET_BADGEVIEW = 33;
    public static final int VIEW_TYPE_WIDGET_PATTERNLOCKVIEW = 34;
    public static final int VIEW_TYPE_WIDGET_WAVESIDEBAR = 35;
    public static final int VIEW_TYPE_LAYOUT_CARDVIEW = 36;
    public static final int VIEW_TYPE_LAYOUT_COLLAPSINGTOOLBARLAYOUT = 37;
    public static final int VIEW_TYPE_LAYOUT_TEXTINPUTLAYOUT = 38;
    public static final int VIEW_TYPE_LAYOUT_SWIPEREFRESHLAYOUT = 39;
    public static final int VIEW_TYPE_LAYOUT_RADIOGROUP = 40;
    public static final int VIEW_TYPE_WIDGET_MATERIALBUTTON = 41;
    public static final int VIEW_TYPE_WIDGET_SIGNINBUTTON = 42;
    public static final int VIEW_TYPE_WIDGET_CIRCLEIMAGEVIEW = 43;
    public static final int VIEW_TYPE_WIDGET_LOTTIEANIMATIONVIEW = 44;
    public static final int VIEW_TYPE_WIDGET_YOUTUBEPLAYERVIEW = 45;
    public static final int VIEW_TYPE_WIDGET_OTPVIEW = 46;
    public static final int VIEW_TYPE_WIDGET_CODEVIEW = 47;
    public static final int VIEW_TYPE_WIDGET_RECYCLERVIEW = 48;

    public static int getViewTypeResId(int type) {
        switch (type) {
            case VIEW_TYPE_LAYOUT_LINEAR:
                return R.drawable.widget_linear_horizontal;

            case VIEW_TYPE_LAYOUT_RELATIVE:
                return R.drawable.widget_relative_layout;

            case VIEW_TYPE_LAYOUT_HSCROLLVIEW:
                return R.drawable.widget_horizon_scrollview;

            case VIEW_TYPE_WIDGET_BUTTON:
                return R.drawable.widget_button;

            case VIEW_TYPE_WIDGET_TEXTVIEW:
                return R.drawable.widget_text_view;

            case VIEW_TYPE_WIDGET_EDITTEXT:
                return R.drawable.widget_edit_text;

            case VIEW_TYPE_WIDGET_IMAGEVIEW:
                return R.drawable.widget_image_view;

            case VIEW_TYPE_WIDGET_WEBVIEW:
                return R.drawable.widget_web_view;

            case VIEW_TYPE_WIDGET_PROGRESSBAR:
                return R.drawable.widget_progress_bar;

            case VIEW_TYPE_WIDGET_LISTVIEW:
                return R.drawable.widget_list_view;

            case VIEW_TYPE_WIDGET_SPINNER:
                return R.drawable.widget_spinner;

            case VIEW_TYPE_WIDGET_CHECKBOX:
                return R.drawable.widget_check_box;

            case VIEW_TYPE_LAYOUT_VSCROLLVIEW:
                return R.drawable.widget_scrollview;

            case VIEW_TYPE_WIDGET_SWITCH:
                return R.drawable.widget_switch;

            case VIEW_TYPE_WIDGET_SEEKBAR:
                return R.drawable.widget_seek_bar;

            case VIEW_TYPE_WIDGET_CALENDARVIEW:
                return R.drawable.widget_calendarview;

            case VIEW_TYPE_WIDGET_FAB:
                return R.drawable.widget_fab;

            case VIEW_TYPE_WIDGET_ADVIEW:
                return R.drawable.widget_admob;

            case VIEW_TYPE_WIDGET_MAPVIEW:
                return R.drawable.widget_google_map;
            
            
            case VIEW_TYPE_WIDGET_RADIOBUTTON:
                return R.drawable.widget_radio_button;

            case VIEW_TYPE_WIDGET_RATINGBAR:
                return R.drawable.color_star_24;

            case VIEW_TYPE_WIDGET_VIDEOVIEW:
                return R.drawable.widget_mediaplayer;

            case VIEW_TYPE_WIDGET_SEARCHVIEW:
                return R.drawable.ic_search_color_96dp;

            case VIEW_TYPE_WIDGET_AUTOCOMPLETETEXTVIEW:
            case VIEW_TYPE_WIDGET_MULTIAUTOCOMPLETETEXTVIEW:
            case VIEW_TYPE_LAYOUT_TEXTINPUTLAYOUT:
                return R.drawable.widget_edit_text;

            case VIEW_TYPE_WIDGET_GRIDVIEW:
            case VIEW_TYPE_WIDGET_RECYCLERVIEW:
                return R.drawable.grid_3_48;

            case VIEW_TYPE_WIDGET_ANALOGCLOCK:
            case VIEW_TYPE_WIDGET_TIMEPICKER:
            case VIEW_TYPE_WIDGET_DIGITALCLOCK:
                return R.drawable.widget_timer;

            case VIEW_TYPE_WIDGET_DATEPICKER:
                return R.drawable.date_span_96;

            case VIEW_TYPE_LAYOUT_TABLAYOUT:
                return R.drawable.widget_tab_layout;

            case VIEW_TYPE_LAYOUT_VIEWPAGER:
                return R.drawable.widget_view_pager;

            case VIEW_TYPE_LAYOUT_BOTTOMNAVIGATIONVIEW:
                return R.drawable.widget_bottom_view;

            case VIEW_TYPE_WIDGET_BADGEVIEW:
                return R.drawable.item_badge_view;

            case VIEW_TYPE_WIDGET_PATTERNLOCKVIEW:
                return R.drawable.widget_pattern_lock_view;

            case VIEW_TYPE_WIDGET_WAVESIDEBAR:
                return R.drawable.widget_wave_side_bar;

            case VIEW_TYPE_LAYOUT_CARDVIEW:
                return R.drawable.widget_cardview;

            case VIEW_TYPE_LAYOUT_COLLAPSINGTOOLBARLAYOUT:
                return R.drawable.widget_collapsing_toolbar;

            case VIEW_TYPE_LAYOUT_SWIPEREFRESHLAYOUT:
                return R.drawable.widget_swipe_refresh;

            case VIEW_TYPE_LAYOUT_RADIOGROUP:
                return R.drawable.widget_radiogroup;

            case VIEW_TYPE_WIDGET_MATERIALBUTTON:
                return R.drawable.widget_material_button;

            case VIEW_TYPE_WIDGET_SIGNINBUTTON:
                return R.drawable.google_48;

            case VIEW_TYPE_WIDGET_CIRCLEIMAGEVIEW:
                return R.drawable.widget_circle_image;

            case VIEW_TYPE_WIDGET_LOTTIEANIMATIONVIEW:
                return R.drawable.widget_lottie;

            case VIEW_TYPE_WIDGET_YOUTUBEPLAYERVIEW:
                return R.drawable.widget_youtube;

            case VIEW_TYPE_WIDGET_OTPVIEW:
                return R.drawable.event_google_signin;

            case VIEW_TYPE_WIDGET_CODEVIEW:
                return R.drawable.widget_code_view;
            default:
                return R.drawable.widget_module;
        }
    }
}
