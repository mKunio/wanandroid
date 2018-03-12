package com.kunio.wanandroidclient.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by zhc on 2018/3/7 0007.
 */

public class Knowledge implements Parcelable {
    private int childId;
    private String childTitle;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.childId);
        dest.writeString(this.childTitle);
    }

    public Knowledge() {
    }

    protected Knowledge(Parcel in) {
        this.childId = in.readInt();
        this.childTitle = in.readString();
    }

    public static final Parcelable.Creator<Knowledge> CREATOR = new Parcelable.Creator<Knowledge>() {
        @Override
        public Knowledge createFromParcel(Parcel source) {
            return new Knowledge(source);
        }

        @Override
        public Knowledge[] newArray(int size) {
            return new Knowledge[size];
        }
    };

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public String getChildTitle() {
        return childTitle;
    }

    public void setChildTitle(String childTitle) {
        this.childTitle = childTitle;
    }
}
