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

import android.util.Log;
import com.mobilesolutionworks.android.managedview.converter.Converter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by yunarta on 31/5/14.
 */
public class ManagedObjectProvider implements IObjectProvider
{
    protected Map<String, Object> mManagedMap;

    protected Map<String, Class> mClassMap;

    protected Map<String, Field> mFieldMap;
    protected Map<String, Converter> mConverterMap;

    protected Set<String> mMissingFields;

    public ManagedObjectProvider()
    {
        mManagedMap = new HashMap<String, Object>();

        mClassMap = new HashMap<String, Class>();

        mFieldMap = new HashMap<String, Field>();
        mConverterMap = new HashMap<String, Converter>();

        mMissingFields = new HashSet<String>();
    }

    public void setManaged(String name, Object value)
    {
        mManagedMap.put(name, value);
    }

    public void onPropertyChanged(String name, String property, String value)
    {
        Object managed = mManagedMap.get(name);
        if (managed != null)
        {
            String fieldKey = name + "." + property;
            Field field = mFieldMap.get(fieldKey);
            if (field == null)
            {
                if (mMissingFields.contains(fieldKey))
                {
                    return;
                }

                Class clazz = mClassMap.get(name);
                if (clazz == null)
                {
                    clazz = managed.getClass();
                    mClassMap.put(name, clazz);
                }

                try
                {
                    field = clazz.getField(property);
                }
                catch (NoSuchFieldException e)
                {
                    mMissingFields.add(fieldKey);
                    return;
                }

                mFieldMap.put(fieldKey, field);
            }

            if (field != null)
            {
                try
                {
                    Converter converter = mConverterMap.get(fieldKey);
                    if (converter != null)
                    {
                        field.set(managed, converter.setValue(value));
                    }
                    else
                    {
                        field.set(managed, value);
                    }
                }
                catch (IllegalAccessException e)
                {
                    // e.printStackTrace();
                }
            }
        }
    }

    public Object getProperty(String name, String property)
    {
        Object managed = mManagedMap.get(name);
        if (managed != null)
        {
            String fieldKey = name + "." + property;
            Field field = mFieldMap.get(fieldKey);
            if (field == null)
            {
                if (mMissingFields.contains(fieldKey))
                {
                    return null;
                }

                Class clazz = mClassMap.get(name);
                if (clazz == null)
                {
                    clazz = managed.getClass();
                    mClassMap.put(name, clazz);
                }

                try
                {
                    field = clazz.getField(property);
                }
                catch (NoSuchFieldException e)
                {
                    mMissingFields.add(fieldKey);
                    return null;
                }

                mFieldMap.put(fieldKey, field);
            }

            if (field != null)
            {
                try
                {
                    Converter converter = mConverterMap.get(fieldKey);
                    if (converter != null)
                    {
                        return converter.getValue(field.get(managed));
                    }
                    else
                    {
                        return field.get(managed);
                    }
                }
                catch (IllegalAccessException e)
                {
                    // e.printStackTrace();
                }
            }
        }

        return null;
    }

    public void setConverter(String property, Converter converter)
    {
        mConverterMap.put(property, converter);
    }
}
