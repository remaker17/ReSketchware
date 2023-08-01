package app.resketchware.beans;

import android.os.Parcel;
import android.os.Parcelable;

import app.resketchware.R;

import com.google.gson.annotations.Expose;

public class EventBean implements Parcelable {

    // Types of events
    public static final int EVENT_TYPE_VIEW = 1;
    public static final int EVENT_TYPE_COMPONENT = 2;
    public static final int EVENT_TYPE_ACTIVITY = 3;
    public static final int EVENT_TYPE_DRAWER_VIEW = 4;
    public static final int EVENT_TYPE_ETC = 5;

    @Expose public String eventName;
    @Expose public int eventType;
    @Expose public String targetId;
    @Expose public int targetType;

    public EventBean(int eventType, int targetType, String targetId, String eventName) {
        this.eventType = eventType;
        this.targetType = targetType;
        this.targetId = targetId;
        this.eventName = eventName;
    }

    public static int getEventIconResource(int eventType, int targetType) {
        int icon = 0;
        switch (eventType) {
            case EVENT_TYPE_ACTIVITY:
                icon = R.drawable.widget_source;
                break;

            case EVENT_TYPE_VIEW:
            case EVENT_TYPE_DRAWER_VIEW:
                return ViewBean.getViewTypeResId(targetType);

            // case EVENT_TYPE_COMPONENT:
                // return ComponentBean.getIconResource(targetType);

            default:
                icon = R.drawable.widget_module;
                break;
        }
        return icon;
    }

    public static String getEventTypeName(int eventType) {
        switch (eventType) {
            case EVENT_TYPE_VIEW:
                return "view event";

            case EVENT_TYPE_COMPONENT:
                return "component event";

            case EVENT_TYPE_ACTIVITY:
                return "activity event";

            case EVENT_TYPE_DRAWER_VIEW:
                return "drawer view event";

            default:
                return "";
        }
    }

    public EventBean(Parcel other) {
        eventType = other.readInt();
        targetType = other.readInt();
        targetId = other.readString();
        eventName = other.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flag) {
        parcel.writeInt(eventType);
        parcel.writeInt(targetType);
        parcel.writeString(targetId);
        parcel.writeString(eventName);
    }

    public void copy(EventBean other) {
        eventType = other.eventType;
        targetType = other.targetType;
        targetId = other.targetId;
        eventName = other.eventName;
    }

    public String getEventKey() {
        return targetId + "_" + eventName;
    }

    public static void deleteEvent(
            String sc_id, EventBean event, ProjectFileBean projectFileBean) {}
}
