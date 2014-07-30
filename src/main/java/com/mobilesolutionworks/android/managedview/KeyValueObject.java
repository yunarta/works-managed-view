package com.mobilesolutionworks.android.managedview;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yunarta on 30/5/14.
 */
public class KeyValueObject implements Parcelable
{
    public String id;

    public String name;

    public KeyValueObject()
    {
    }

    public KeyValueObject(String id, String name)
    {
        this.id = id;
        this.name = name;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(id);
        dest.writeString(name);
    }

    public static final Parcelable.Creator<KeyValueObject> CREATOR = new Parcelable.Creator<KeyValueObject>()
    {
        public KeyValueObject createFromParcel(Parcel in)
        {
            return new KeyValueObject(in);
        }

        public KeyValueObject[] newArray(int size)
        {
            return new KeyValueObject[size];
        }
    };

    private KeyValueObject(Parcel in)
    {
        id = in.readString();
        name = in.readString();
    }
}
