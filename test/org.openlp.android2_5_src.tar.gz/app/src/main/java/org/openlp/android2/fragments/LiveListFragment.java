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

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openlp.android2.R;
import org.openlp.android2.api.Api;
import org.openlp.android2.common.JsonHelpers;
import org.openlp.android2.common.OpenLPFragment;
import org.openlp.android2.common.OpenLPHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LiveListFragment extends OpenLPFragment {

    private int selected = 0;

    private String LOG_TAG = LiveListFragment.class.getName();

    public static LiveListFragment newInstance() {
        return new LiveListFragment();
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public LiveListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        updateUrl = Api.LIVE_TEXT;
        httpClient = new OpenLPHttpClient(context);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void populateDisplay(String json, boolean notInError) {
        Log.i(LOG_TAG, "populate_display - entry");
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
        selected = 0;

        if (notInError) {

            try {
                JSONArray items = new JSONObject(json).getJSONObject("results").getJSONArray("slides");
                for (int i = 0; i < items.length(); ++i) {
                    JSONObject item = items.getJSONObject(i);

                    HashMap<String, String> hm = new HashMap<String, String>();
                    hm.put("tag", item.getString("tag"));
                    if (item.getString("selected").equals("true")) {
                        selected = i;
                    }
                    hm.put("liveListNormal", Html.fromHtml(item.getString("html")).toString());
                    aList.add(hm);
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG,json);
                e.printStackTrace();
            }
        }

        // Keys used in Hashmap
        String[] from = {"tag", "liveListNormal", "liveListSelected"};

        // Ids of views in live_list_fragment
        int[] to = {R.id.tag, R.id.liveListNormal, R.id.liveListSelected};

        SharedPreferences prefs = context.getSharedPreferences(
                context.getString(R.string.key_shared_preferences),
                Context.MODE_PRIVATE);

        final int size = Integer.parseInt(prefs.getString(
                context.getString(R.string.key_text_size),
                String.valueOf(context.getResources().getInteger(
                        R.integer.textSizeDefaultValue))));

        // Instantiating an adapter to store each items
        ListAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList,
                R.layout.fragment_livelist, from, to) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(R.id.tag);
                text1.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
                TextView text2 = (TextView) view.findViewById(R.id.liveListNormal);
                text2.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
                if (selected == position) {
                    text2.setTextColor(Color.parseColor("#000000"));
                    text2.setTypeface(null, Typeface.BOLD_ITALIC);
                } else{
                    text2.setTypeface(null, Typeface.NORMAL);
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

    @Override
    public void refreshDisplay() {
        Log.d(LOG_TAG, "Resuming...");
        triggerTextRequest(Api.LIVE_TEXT);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "Resuming...");
        triggerTextRequest(Api.LIVE_TEXT);
        Log.d(LOG_TAG, "Resumed...");
    }

    public void processUpdate(String response, boolean inError) {
        triggerTextRequest(Api.LIVE_TEXT);

    }

    public void itemClicked(int position) {
        try {
            String request = JsonHelpers.createRequestJSON("id", Integer.toString(position));
            triggerTextRequest(String.format("%s%s", Api.LIVE_SET, request));
            Log.d(LOG_TAG, String.format("Setting list data. apiBase(%s), position(%s)",
                    Api.LIVE_SET, position));
        } catch (JsonHelpers.JSONHandlerException e) {
            e.printStackTrace();
            Toast.makeText(getActivity().getBaseContext(), "Request Failed", Toast.LENGTH_SHORT).show();
        }
    }

}
