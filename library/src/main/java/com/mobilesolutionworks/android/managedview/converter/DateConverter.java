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
