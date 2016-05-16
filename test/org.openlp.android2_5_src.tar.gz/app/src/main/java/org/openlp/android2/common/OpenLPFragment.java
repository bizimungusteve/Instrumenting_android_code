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
package org.openlp.android2.common;


import android.app.ListFragment;
import android.content.Context;
import android.util.Log;
import android.view.View;

import android.widget.ListView;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import org.apache.http.Header;
import org.openlp.android2.R;
import org.openlp.android2.api.Api;

abstract public class OpenLPFragment extends ListFragment{

    private String LOG_TAG = OpenLPFragment.class.getName();
    public Context context;
    protected String calledURL;
    protected OpenLPHttpClient httpClient;
    protected String updateUrl;

    abstract public void itemClicked(int position);

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        itemClicked(position);
    }

    private static AsyncHttpClient client = new AsyncHttpClient();

    protected void refreshDisplay(){}
    protected void populateDisplay(String responseString, boolean inError) {}
    protected void processUpdate(String responseString, boolean inError) {}

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

    public void manageResponse(String response, boolean notInError) {
        if (calledURL.equals(updateUrl)) {
            populateDisplay(response, notInError);
        }else {
            processUpdate(response, notInError);
        }
    }

    public void next() {
        Log.d(LOG_TAG, "Going to next slide");
        triggerTextRequest(Api.LIVE_NEXT);
    }

    public void previous() {
        Log.d(LOG_TAG, "Going to previous slide");
        triggerTextRequest(Api.LIVE_PREVIOUS);
    }
}
