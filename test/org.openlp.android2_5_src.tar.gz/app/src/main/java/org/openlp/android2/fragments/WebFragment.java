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

import android.app.Fragment;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import org.openlp.android2.R;
import org.openlp.android2.common.OpenLPHttpClient;

public class WebFragment extends Fragment {

    protected String curURL;
    private OpenLPHttpClient httpClient;
    private WebView webview;

    public WebFragment(){
        super();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        httpClient = new OpenLPHttpClient(getActivity());
        View view = inflater.inflate(R.layout.fragment_web, container, false);

        if (curURL != null) {
            webview = (WebView) view.findViewById(R.id.webPage);
            webview.getSettings().setJavaScriptEnabled(true);
            webview.getSettings().setBuiltInZoomControls(true);
            webview.getSettings().setLoadWithOverviewMode(true);
            webview.getSettings().setUseWideViewPort(true);
            webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            webview.setScrollbarFadingEnabled(true);
            webview.setWebViewClient(new webClient());
            webview.loadUrl(String.format("%s%s", httpClient.getBaseUrl(), curURL));
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        if (webview != null) {
            webview.destroy();
        }
        super.onDestroyView();
    }

    private class webClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed(); // Ignore SSL certificate errors
        }
    }
}
