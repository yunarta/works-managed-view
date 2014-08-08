package com.mobilesolutionworks.android.managedview;

/**
 * Created by yunarta on 30/5/14.
 */
public interface IObjectProvider
{
    void onPropertyChanged(String name, String property, String value);

    Object getProperty(String name, String property);
}
