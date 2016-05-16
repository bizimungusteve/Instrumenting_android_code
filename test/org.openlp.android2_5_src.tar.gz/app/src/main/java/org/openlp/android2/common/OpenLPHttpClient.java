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


import java.security.KeyStore;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import com.loopj.android.http.AsyncHttpClient;
import org.openlp.android2.R;

/**
 * Personalised HttpClient to be used throughout OpenLP with customisable
 * parameters.
 */
public class OpenLPHttpClient {

    private final String LOG_TAG = OpenLPHttpClient.class.getName();
    private Context context;
    private Boolean useSSL = Boolean.FALSE;

    public OpenLPHttpClient(Context context) {
        this.context = context;
    }

    public String getAbsoluteUrl(AsyncHttpClient client) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        String urlBase = getBaseUrl();

        Log.d(LOG_TAG, "Base Url set to " + urlBase);

        String userid = sharedPrefs.getString(context.getString(R.string.key_userid), "openlp");

        String password = sharedPrefs.getString(context.getString(R.string.key_password), "password");

        Log.d(LOG_TAG, "Credentials set to " + userid + " : " + password);
        client.setBasicAuth(userid,password);

        int connectionTimeout = context.getResources().getInteger(
                R.integer.connectionTimeoutDefaultValue);

        if (sharedPrefs.getBoolean(context.getString(R.string.key_enable_custom_timeout), false)) {
            Log.d(LOG_TAG, "Overriding Connection and Socket timeouts");

            connectionTimeout = Integer.parseInt(sharedPrefs.getString(context.getString(R.string.key_connection_timeout),
                    String.valueOf(context.getResources().getInteger(R.integer.connectionTimeoutDefaultValue))
            ));
        }
        client.setTimeout(connectionTimeout);
        if (useSSL){
            try {
                KeyStore trustStore = KeyStore.getInstance((KeyStore.getDefaultType()));
                trustStore.load(null, null);
                OpenLPSSLSocketFactory sf = new OpenLPSSLSocketFactory(trustStore);
                sf.setHostnameVerifier((OpenLPSSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER));
                client.setSSLSocketFactory(sf);
            }
            catch (Exception e){
                Log.d(LOG_TAG, "Unable to support SSL");
            }
        }
        return urlBase;
    }

    public String getBaseUrl(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        useSSL = sharedPrefs.getBoolean(context.getString(R.string.key_ssl_use), false);
        String host = sharedPrefs.getString(context.getString(R.string.key_host),
                context.getString(R.string.host_default_value));
        String port = sharedPrefs.getString(context.getString(R.string.key_port), "4316");

        return String.format("http%s://%s:%s", useSSL ? "s" : "", host, port);

    }

}
