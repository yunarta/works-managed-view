package com.mobilesolutionworks.android.managedview.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by yunarta on 31/5/14.
 */
public class DateConverter implements Converter
{
    protected DateFormat mFormat;

    public DateConverter(DateFormat format)
    {
        mFormat = format;
        mFormat.setTimeZone(TimeZone.getDefault());
    }

    @Override
    public String getValue(Object value)
    {
        Date date = (Date) value;
        if (date == null)
        {
            return null;
        }
        else
        {
            return mFormat.format(date);
        }
    }

    @Override
    public Object setValue(String value) {
        try
        {
            return mFormat.parse(value);
        }
        catch (ParseException e)
        {
            return null;
        }
    }
}
