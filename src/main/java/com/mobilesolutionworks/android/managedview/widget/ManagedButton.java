package com.mobilesolutionworks.android.managedview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;
import com.mobilesolutionworks.android.managedview.IObjectProvider;
import com.mobilesolutionworks.android.managedview.R;

/**
 * Created by yunarta on 30/5/14.
 */
public class ManagedButton extends Button
{
    @Override
    public void computeScroll()
    {
        super.computeScroll();
        Log.d("ManagedButton", "invalided");
    }

    protected IObjectProvider mProvider;

    protected String mName;

    protected String mProperty;

    public ManagedButton(Context context)
    {
        super(context);
        initializeManaged(context, null);
    }

    public ManagedButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initializeManaged(context, attrs);
    }

    public ManagedButton(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initializeManaged(context, attrs);
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
                }
            }
        }
    }

    @Override
    protected void onAttachedToWindow()
    {
        super.onAttachedToWindow();
        if (mProvider != null && !TextUtils.isEmpty(mProperty))
        {
            Object value = mProvider.getProperty(mName, mProperty);
            if (value != null)
            {
                setText(String.valueOf(value));
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
            mProvider.onPropertyChanged(mName, mProperty, text.toString());
        }
    }

    public void setProvider(IObjectProvider provider)
    {
        mProvider = provider;
    }
}
