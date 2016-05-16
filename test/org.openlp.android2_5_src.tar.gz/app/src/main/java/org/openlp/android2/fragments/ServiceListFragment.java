/******************************************************************************
 * OpenLP - Open Source Lyrics Projection                                      *
 * --------------------------------------------------------------------------- *
 * Copyright (c) 2011-2015 OpenLP Android Developers                           *
 * --------------------------------------------------------------------------- *
 * This program is free software; you can redistribute it and/or modify it     *
 * under the terms of the GNU General Public License as published by the Free  *
 * Software Foundation; version 2 of the License.                              *
 *                                                                             *
 * This program is distributed in the hope that it will be useful, but WITHOUT *
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or       *
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for    *
 * more details.                                                               *
 *                                                                             *
 * You should have received a copy of the GNU General Public License along     *
 * with this program; if not, write to the Free Software Foundation, Inc., 59  *
 * Temple Place, Suite 330, Boston, MA 02111-1307 USA                          *
 *******************************************************************************/
package org.openlp.android2.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openlp.android2.OpenLP;
import org.openlp.android2.R;

import org.openlp.android2.api.Api;
import org.openlp.android2.common.JsonHelpers;
import org.openlp.android2.common.OpenLPFragment;
import org.openlp.android2.common.OpenLPHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ServiceListFragment extends OpenLPFragment {

    private final String LOG_TAG = ServiceListFragment.class.getName();
    private int selected = 0;

    public ServiceListFragment() {
    }

    public static ServiceListFragment newInstance() {
        ServiceListFragment fragment = new ServiceListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        updateUrl = Api.SERVICE_LIST;
        httpClient = new OpenLPHttpClient(context);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void refreshDisplay() {
        Log.d(LOG_TAG, "Resuming...");
        triggerTextRequest(Api.SERVICE_LIST);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "Resuming...");
        triggerTextRequest(Api.SERVICE_LIST);
        Log.d(LOG_TAG, "Resumed...");
    }

    public void processUpdate(String response, boolean inError) {
        triggerTextRequest(Api.SERVICE_LIST);
        ((OpenLP) getActivity()).refreshLiveFragment(R.id.container_right);
    }

    @Override
    public void populateDisplay(String json, boolean notInError) {
        Log.i(LOG_TAG, "populate_display - entry");
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();

        if (notInError) {
            try {
                JSONArray items = new JSONObject(json).getJSONObject("results").getJSONArray("items");

                for (int i = 0; i < items.length(); ++i) {
                    JSONObject item = items.getJSONObject(i);

                    HashMap<String, String> hm = new HashMap<String, String>();
                    if (item.getString("plugin").equals("songs")) {
                        hm.put("icon", Integer.toString(R.drawable.ic_my_library_music));
                    } else if (item.getString("plugin").equals("bibles")) {
                        hm.put("icon", Integer.toString(R.drawable.ic_my_library_books));
                    } else if (item.getString("plugin").equals("media")) {
                        hm.put("icon", Integer.toString(R.drawable.ic_local_movies));
                    } else if (item.getString("plugin").equals("presentations")) {
                        hm.put("icon", Integer.toString(R.drawable.ic_video_collection));
                    } else if (item.getString("plugin").equals("images")) {
                        hm.put("icon", Integer.toString(R.drawable.ic_image));
                    } else {
                        hm.put("icon", Integer.toString(R.drawable.ic_edit));
                    }
                    if (item.getString("selected").equals("true")) {
                        selected = i;
                    }
                    hm.put("title", item.getString("title"));
                    aList.add(hm);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Keys used in Hashmap
        String[] from = {"icon", "title"};

        // Ids of views in service_list_fragment
        int[] to = {R.id.icon, R.id.serviceListText};

        SharedPreferences prefs = context.getSharedPreferences(
                context.getString(R.string.key_shared_preferences),
                Context.MODE_PRIVATE);

        final int size = Integer.parseInt(prefs.getString(
                context.getString(R.string.key_text_size),
                String.valueOf(context.getResources().getInteger(
                        R.integer.textSizeDefaultValue))));

        // Instantiating an adapter to store each items
        ListAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList,
                R.layout.fragment_service_list, from, to) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(R.id.serviceListText);
                text1.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
                if (selected == position) {
                    text1.setTextColor(Color.parseColor("#000000"));
                    text1.setTypeface(null, Typeface.BOLD_ITALIC);
                } else{
                    text1.setTypeface(null, Typeface.NORMAL);
                }
                return view;
            }
        };
        setListAdapter(adapter);

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        Boolean autoscroll =
                sharedPrefs.getBoolean(context.getString(R.string.key_auto_scroll), false);

        if (autoscroll){
            getListView().setSelection(selected - 1);
        }
        Log.i(LOG_TAG, "populate_display - exit");
    }

    public void itemClicked(int position) {
        try {
            String request = JsonHelpers.createRequestJSON("id", Integer.toString(position));
            triggerTextRequest(String.format("%s%s", Api.SERVICE_SET, request));
            Log.d(LOG_TAG, String.format("Setting list data. apiBase(%s), position(%s)",
                    Api.SERVICE_SET, position));
        } catch (JsonHelpers.JSONHandlerException e) {
            e.printStackTrace();
            Toast.makeText(context, "Request Failed", Toast.LENGTH_SHORT).show();
        }
    }
}
