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

package com.mobilesolutionworks.android.managedview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.Button;
import com.mobilesolutionworks.android.managedview.IObjectProvider;
import com.mobilesolutionworks.android.managedview.KeyValueObject;
import com.mobilesolutionworks.android.managedview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yunarta on 30/5/14.
 */
public class ManagedDropDown extends Button
{
    protected IObjectProvider mProvider;

    protected String mName;

    protected String mProperty;

    protected List<KeyValueObject> mList;

    public ManagedDropDown(Context context)
    {
        super(context);
        initializeManaged(context, null);
    }

    public ManagedDropDown(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initializeManaged(context, attrs);
    }

    public ManagedDropDown(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initializeManaged(context, attrs);
    }

    public List<KeyValueObject> getList()
    {
        return mList;
    }

    public void setList(List<KeyValueObject> list)
    {
        mList = list;
    }

    private void initializeManaged(Context context, AttributeSet attrs)
    {
        if (isInEditMode()) {
            return;
        }

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ManagedDropDown);
        if (ta != null)
        {
            int n = ta.getIndexCount();
            for (int i = 0; i < n; i++)
            {
                int attr = ta.getIndex(i);
                switch (attr)
                {
                    case R.styleable.ManagedDropDown_name:
                    {
                        mName = ta.getString(attr);
                        break;
                    }

                    case R.styleable.ManagedDropDown_property:
                    {
                        mProperty = ta.getString(attr);
                        break;
                    }

                    case R.styleable.ManagedDropDown_list:
                    {
                        int id = ta.getResourceId(attr, -1);
                        if (id != -1)
                        {
                            mList = new ArrayList<KeyValueObject>();

                            if (!isInEditMode())
                            {
                                String[] array = getResources().getStringArray(id);
                                for (int j = 0; j < array.length; j++)
                                {
                                    if (!TextUtils.isEmpty(array[j]))
                                    {
                                        mList.add(new KeyValueObject(String.valueOf(j), array[j]));
                                    }
                                }
                            }
                        }
                        break;
                    }

                }
            }
        }
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        updateValue();
    }

    public void updateValue() {
        if (mProvider != null && !TextUtils.isEmpty(mProperty) && mList != null)
        {
            Object value = mProvider.getProperty(mName, mProperty);
            if (value != null)
            {
                for (KeyValueObject kvo : mList)
                {
                    if (kvo.id.equals(value))
                    {
                        setText(kvo.name);
                        return;
                    }
                }
            }
        }
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
    }

    public void setText(CharSequence text, BufferType type)
    {
        super.setText(text, type);
        if (mProvider != null && !TextUtils.isEmpty(mProperty))
        {
            for (KeyValueObject kvo : mList)
            {
                if (kvo.name.equals(text.toString()))
                {
                    mProvider.onPropertyChanged(mName, mProperty, kvo.id);
                    return;
                }
            }
        }
    }

    public void setProvider(IObjectProvider provider)
    {
        mProvider = provider;
    }
}
