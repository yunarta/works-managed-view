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

package com.mobilesolutionworks.android.managedview.test;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;
import com.mobilesolutionworks.android.managedview.KeyValueObject;
import com.mobilesolutionworks.android.managedview.ManagedObjectProvider;
import com.mobilesolutionworks.android.managedview.converter.DateConverter;
import com.mobilesolutionworks.android.managedview.test.data.Info;
import works.ManagedButton;
import works.ManagedDropDown;
import works.ManagedEditText;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by yunarta on 11/8/14.
 */
public class DemoInFragment extends Fragment implements View.OnClickListener, LayoutInflater.Factory {

    ManagedObjectProvider mProvider;

    DatePickerDialog mPickerDialog;

    Info mInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInfo = new Info();
        mInfo.list = "3";
        mInfo.listResource = "1";
        mInfo.date = new Date();

        mProvider = new ManagedObjectProvider();
        mProvider.setConverter("info.date", new DateConverter(new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH)));
        mProvider.setManaged("info", mInfo);
    }

    @Override
    public LayoutInflater getLayoutInflater(Bundle savedInstanceState) {
        LayoutInflater inflater = super.getLayoutInflater(savedInstanceState);
        inflater = inflater.cloneInContext(inflater.getContext());

        inflater.setFactory(this);
        return inflater;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_demo, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<KeyValueObject> list = new ArrayList<KeyValueObject>();
        list.add(new KeyValueObject("0", "#0 - Key 0"));
        list.add(new KeyValueObject("2", "#1 - Key 2"));
        list.add(new KeyValueObject("4", "#2 - Key 4"));
        list.add(new KeyValueObject("3", "#3 - Key 5"));

        view.findViewById(R.id.btn_print).setOnClickListener(this);

        TextView textView = (TextView) view.findViewById(R.id.log);
        textView.setText(mInfo.toString());

        ManagedDropDown dropDown = (ManagedDropDown) getView().findViewById(R.id.info_list);
        dropDown.setList(list);

        view.findViewById(R.id.info_list).setOnClickListener(this);
        view.findViewById(R.id.info_listResource).setOnClickListener(this);
        view.findViewById(R.id.info_date).setOnClickListener(this);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        if ("works.ManagedEditText".equals(name)) {
            ManagedEditText view = new ManagedEditText(context, attrs);
            view.setProvider(mProvider);

            return view;
        } else if ("works.ManagedButton".equals(name)) {
            ManagedButton view = new ManagedButton(context, attrs);
            view.setProvider(mProvider);

            return view;
        } else if ("works.ManagedDropDown".equals(name)) {
            ManagedDropDown view = new ManagedDropDown(context, attrs);
            view.setProvider(mProvider);

            return view;
        } else {
            return null;
        }
    }

    @Override
    public void onClick(View v) {
        Activity activity = getActivity();

        switch (v.getId()) {
            case R.id.info_list: {
                ManagedDropDown dropDown = (ManagedDropDown) getView().findViewById(R.id.info_list);
                List<KeyValueObject> list = dropDown.getList();

                new AlertDialog.Builder(activity).
                        setSingleChoiceItems(new ArrayAdapter<KeyValueObject>(activity, android.R.layout.simple_list_item_1, list), -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ManagedDropDown dropDown = (ManagedDropDown) getView().findViewById(R.id.info_list);
                                List<KeyValueObject> list = dropDown.getList();

                                dropDown.setText(list.get(which).name);
                                dialog.dismiss();
                            }
                        }).
                        create().show();
                break;
            }

            case R.id.info_listResource: {
                new AlertDialog.Builder(activity).
                        setSingleChoiceItems(new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.listResources)), -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                String[] array = getResources().getStringArray(R.array.listResources);
                                ManagedDropDown dropDown = (ManagedDropDown) getView().findViewById(R.id.info_listResource);
                                dropDown.setText(array[which]);

                                dialog.dismiss();
                            }
                        }).
                        create().show();
                break;
            }

            case R.id.info_date: {
                Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                cal.setTime(mInfo.date);

                mPickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, monthOfYear);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        cal.set(Calendar.HOUR, 0);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);

                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
                        String date = sdf.format(cal.getTime());

                        ManagedButton button = (ManagedButton) getView().findViewById(R.id.info_date);
                        button.setText(date);

                        mPickerDialog.dismiss();
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
                mPickerDialog.show();
                break;
            }

            case R.id.btn_print: {
                TextView textView = (TextView) getView().findViewById(R.id.log);
                textView.setText(mInfo.toString());
                break;
            }
        }
    }
}
