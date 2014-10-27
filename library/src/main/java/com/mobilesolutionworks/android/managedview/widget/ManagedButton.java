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
import com.mobilesolutionworks.android.managedview.R;

/**
 * Created by yunarta on 30/5/14.
 */
public class ManagedButton extends Button {
    protected IObjectProvider mProvider;

    protected String mName;

    protected String mProperty;

    public ManagedButton(Context context) {
        super(context);
        initializeManaged(context, null);
    }

    public ManagedButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeManaged(context, attrs);
    }

    public ManagedButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initializeManaged(context, attrs);
    }

    private void initializeManaged(Context context, AttributeSet attrs) {
        if (isInEditMode()) {
            return;
        }

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ManagedButton);
        if (ta != null) {
            int n = ta.getIndexCount();
            for (int i = 0; i < n; i++) {
                int attr = ta.getIndex(i);
                if (attr == R.styleable.ManagedButton_name) {
                    mName = ta.getString(attr);
                } else if (attr == R.styleable.ManagedButton_property) {
                    mProperty = ta.getString(attr);
                }
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mProvider != null && !TextUtils.isEmpty(mProperty)) {
            Object value = mProvider.getProperty(mName, mProperty);
            if (value != null) {
                setText(String.valueOf(value));
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        if (mProvider != null && !TextUtils.isEmpty(mProperty)) {
            mProvider.onPropertyChanged(mName, mProperty, text.toString());
        }
    }

    public void setProvider(IObjectProvider provider) {
        mProvider = provider;
    }
}
