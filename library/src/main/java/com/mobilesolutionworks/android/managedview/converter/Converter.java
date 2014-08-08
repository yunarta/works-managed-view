package com.mobilesolutionworks.android.managedview.converter;

/**
 * Created by yunarta on 31/5/14.
 */
public interface Converter
{
    String getValue(Object value);

    Object setValue(String value);
}
