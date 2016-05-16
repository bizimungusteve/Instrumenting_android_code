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

import android.app.DialogFragment;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import org.apache.http.Header;
import org.openlp.android2.R;
import org.openlp.android2.api.Api;


abstract public class OpenLPDialog extends DialogFragment {

    private final String LOG_TAG = OpenLPDialog.class.getName();
    protected String calledURL;
    protected OpenLPHttpClient httpClient;
    protected Context context;

    private static AsyncHttpClient client = new AsyncHttpClient();

    protected void populateDisplay(String responseString) {}
    protected void processUpdate(String responseString) {}
    protected void errorDisplay(int statusCode, String responseString) {}

    protected void triggerTextRequest(String url) {
        calledURL = url;
        Log.d(LOG_TAG, "Trigger Request for url " + url);
        String callurl = String.format("%s%s", httpClient.getAbsoluteUrl(client), url);

        client.get(callurl, null, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                // called when response HTTP status is "200 OK"
                manageResponse(responseString);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                if (statusCode == 401) {
                    Toast.makeText(context, R.string.httpreturn_unauthorised, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, R.string.unable, Toast.LENGTH_LONG).show();
                }
                errorDisplay(statusCode, responseString);
            }
        });
    }

    public void manageResponse(String response) {
        if (calledURL.equals(Api.POLL_STATUS)) {
            populateDisplay(response);
        }else {
            processUpdate(response);
        }
    }
}
