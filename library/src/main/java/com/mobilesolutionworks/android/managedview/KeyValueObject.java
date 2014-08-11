/*
 * Copyright 2014-present Yunarta
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    public String toString() {
        return name;
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
