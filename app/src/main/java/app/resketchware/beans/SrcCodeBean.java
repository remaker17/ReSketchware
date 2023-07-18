package app.resketchware.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class SrcCodeBean implements Parcelable {

    private String pkgName;
    private String source;
    private String srcFilename;

    public static final Parcelable.Creator<SrcCodeBean> CREATOR = new Parcelable.Creator<>() {
        @Override
        public SrcCodeBean createFromParcel(Parcel source) {
            return new SrcCodeBean(source);
        }

        @Override
        public SrcCodeBean[] newArray(int size) {
            return new SrcCodeBean[size];
        }
    };

    public SrcCodeBean(String sourceFilename, String content) {
        srcFilename = sourceFilename;
        source = content;
    }

    public SrcCodeBean(Parcel other) {
        pkgName = other.readString();
        srcFilename = other.readString();
        source = other.readString();
    }

    public static Parcelable.Creator<SrcCodeBean> getCreator() {
        return CREATOR;
    }

    public String getPackageName() {
        return pkgName;
    }

    public String getSource() {
        return source;
    }

    public String getSrcFilename() {
        return srcFilename;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pkgName);
        dest.writeString(srcFilename);
        dest.writeString(source);
    }
}