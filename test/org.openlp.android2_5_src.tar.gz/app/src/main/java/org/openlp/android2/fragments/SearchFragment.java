package org.openlp.android2.fragments;

import android.app.DialogFragment;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openlp.android2.R;
import org.openlp.android2.api.Api;
import org.openlp.android2.common.JsonHelpers;

import org.openlp.android2.common.OpenLPHttpClient;
import org.openlp.android2.dialogs.SearchSelectionDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**

 */
public class SearchFragment extends Fragment {

    private final String LOG_TAG = SearchFragment.class.getName();
    private Spinner spinner;
    private static AsyncHttpClient client = new AsyncHttpClient();
    public Context context;
    protected String calledURL;
    protected OpenLPHttpClient httpClient;
    protected String updateUrl;
    protected String searchedPlugin;
    protected Map<String, String> pluginMap = new HashMap<String, String>();

    public SearchFragment() {
        Log.d(LOG_TAG, "Constructor");
    }

    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        updateUrl = Api.SEARCHABLE_PLUGINS;
        httpClient = new OpenLPHttpClient(context);
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        spinner = (Spinner)view.findViewById(R.id.search_spinner);
        triggerTextRequest(Api.SEARCHABLE_PLUGINS);

        // Add search listener to text field
        EditText editText = (EditText)view.findViewById(R.id.search_text);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView tv, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // Now close the keyboard as finished with
                    View view = getActivity().getCurrentFocus();
                    if (view != null) {
                        InputMethodManager imm =
                                (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                    searchedPlugin = pluginMap.get(spinner.getSelectedItem().toString());
                    requestSearch(tv.getText().toString());
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void manageResponse(String response, boolean notInError) {
        if (calledURL.equals(updateUrl)) {
            populatePluginList(response, notInError);
        } else {
            populateListDisplay(response, notInError);
        }
    }

    private void populatePluginList(String response, Boolean notInError) {
        Log.i(LOG_TAG, "populatePluginList - entry");
        List<String> categories = new ArrayList<String>();
        pluginMap.clear();

        if (notInError) {
            try {
                JSONArray items = new JSONObject(response).getJSONObject("results").getJSONArray("items");
                for (int i = 0; i < items.length(); ++i) {
                    JSONArray item = items.getJSONArray(i);
                    categories.add(item.get(1).toString());
                    pluginMap.put(item.get(1).toString(),item.get(0).toString());
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, response);
                e.printStackTrace();
            }
            ArrayAdapter<String> LTRadapter = new ArrayAdapter<String>(getActivity(),
                    R.layout.spinner_list_item, categories);
            LTRadapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            spinner.setAdapter(LTRadapter);
            Log.i(LOG_TAG, "populatePluginList - exit");
        }
    }

    protected void triggerTextRequest(String url) {
        calledURL = url;
        Log.d(LOG_TAG, "Trigger Request for url " + url);
        String callurl = String.format("%s%s", httpClient.getAbsoluteUrl(client), url );
        client.get(callurl, null, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                // called when response HTTP status is "200 OK"
                manageResponse(responseString, true);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                if (statusCode == 401) {
                    Toast.makeText(context, R.string.httpreturn_unauthorised, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, R.string.unable, Toast.LENGTH_LONG).show();
                }
                manageResponse(responseString, false);
            }
        });
    }

    public void requestSearch(String text) {
        updateUrl = Api.SEARCH_PLUGIN_FORMATTED;
        try {
            String request = JsonHelpers.createRequestJSON("text", text);
            String url = String.format(Api.SEARCH_PLUGIN_FORMATTED, searchedPlugin);
            triggerTextRequest(String.format("%s%s", url, request));
            Log.d(LOG_TAG, String.format("Search request. apiBase(%s), text(%s)", searchedPlugin, text));
        } catch (JsonHelpers.JSONHandlerException e) {
            e.printStackTrace();
            Toast.makeText(context, "Search Request Failed", Toast.LENGTH_SHORT).show();
        }
    }

    public void populateListDisplay(String json, boolean notInError) {
        Log.i(LOG_TAG, "populateListDisplay - entry");
        ListView list = (ListView)getActivity().findViewById(R.id.searchlistView);
        final ArrayList<JSONArray> listitems = new ArrayList<JSONArray>();
        if (notInError) {
            try {
                JSONArray items = new JSONObject(json).getJSONObject("results").getJSONArray("items");
                for (int i = 0; i < items.length(); ++i) {
                    JSONArray item = items.getJSONArray(i);
                    listitems.add(item);
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG,json);
                e.printStackTrace();
            }
        }

        final StableArrayAdapter adapter = new StableArrayAdapter(context,
                        android.R.layout.simple_list_item_1, listitems);

        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, final View view,
                                    int position, long id) {
                final JSONArray item = (JSONArray) parent.getItemAtPosition(position);
                //Toast.makeText(context, "Item Pressed " + String.valueOf(position) + item,
                //        Toast.LENGTH_SHORT).show();
                String it = "";
                try {
                    it = (String)item.get(1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Bundle args = new Bundle();
                args.putString("plugin", searchedPlugin);
                args.putString("text", it);
                args.putString("key", Long.toString(id));
                DialogFragment newFragment = new SearchSelectionDialog();
                newFragment.setArguments(args);
                newFragment.show(getFragmentManager(), "TAG");

            }
        });
        Log.i(LOG_TAG, "populateListDisplay - exit");
    }

    private class StableArrayAdapter extends ArrayAdapter<JSONArray> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<JSONArray> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                JSONArray item = objects.get(i);
                try {
                    mIdMap.put(item.get(1).toString(), Integer.valueOf(item.get(0).toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            //User user = getItem(position);
            String item = null;
            try {
                item = getItem(position).get(1).toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
              convertView = LayoutInflater.from(getContext()).inflate(R.layout.search_result_row,
                      parent, false);
            }
            // Lookup view for data population
            TextView tvItem = (TextView) convertView.findViewById(R.id.searchListRow);
            // Populate the data into the template view using the data object
            tvItem.setText(item);
            // Return the completed view to render on screen
            return convertView;
        }

        @Override
        public long getItemId(int position) {
            String item = null;
            try {
                item = getItem(position).get(1).toString();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

}
