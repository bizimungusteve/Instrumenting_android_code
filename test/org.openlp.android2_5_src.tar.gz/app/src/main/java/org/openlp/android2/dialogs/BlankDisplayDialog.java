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
package org.openlp.android2.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;

import org.json.JSONException;
import org.json.JSONObject;
import org.openlp.android2.R;
import org.openlp.android2.api.Api;

import org.openlp.android2.common.OpenLPDialog;
import org.openlp.android2.common.OpenLPHttpClient;

public class BlankDisplayDialog extends OpenLPDialog {
    private final String LOG_TAG = BlankDisplayDialog.class.getName();
    public AlertDialog dialog;
    RadioButton desktop;
    RadioButton screen;
    RadioButton theme;
    RadioButton reset;

    /**
     * The system calls this only when creating the layout in a dialog.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.

        context = getActivity();
        httpClient = new OpenLPHttpClient(context);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        View view = inflater.inflate(R.layout.blank_display_dialog, null);
        builder.setView(view);

        reset = (RadioButton) view.findViewById(R.id.buttonReset);
        reset.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerTextRequest(Api.DISPLAY_SHOW);
            }
        });
        screen = (RadioButton) view.findViewById(R.id.buttonScreen);
        screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerTextRequest(Api.DISPLAY_BLANK);
            }
        });
        theme = (RadioButton) view.findViewById(R.id.buttonTheme);
        theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerTextRequest(Api.DISPLAY_THEME);
            }
        });
        desktop = (RadioButton) view.findViewById(R.id.buttonDesktop);
        desktop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                triggerTextRequest(Api.DISPLAY_DESKTOP);
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                BlankDisplayDialog.this.getDialog().cancel();
            }
        });
        dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogI) {
                Button btnNegative = dialog.getButton(Dialog.BUTTON_NEGATIVE);
                btnNegative.setTextSize(20);
            }
        });
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "Resuming...");
        triggerTextRequest(Api.POLL_STATUS);
        Log.d(LOG_TAG, "Resumed...");
    }

    public void processUpdate(String response) {
        triggerTextRequest(Api.POLL_STATUS);
    }

    public void populateDisplay(String json) {
        Log.d(LOG_TAG, "populateDisplay : " + json );
        reset_display();
        try {
            JSONObject item = new JSONObject(json).getJSONObject("results");
            if (item.getString("theme").equals("true")){
                theme.setChecked(true);
            } else {
                if (item.getString("blank").equals("true")){
                    screen.setChecked(true);
                } else{
                    if (item.getString("display").equals("true")){
                        desktop.setChecked(true);
                    } else{
                        reset.setChecked(true);
                    }
                }
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Exception with Json = " + json);
            e.printStackTrace();
        }
    }

    public void errorDisplay(int statusCode, String responseString) {
        Log.d(LOG_TAG, String.format("URL Error status code %d text %s", statusCode, responseString));
        reset_display();
    }

    private void reset_display(){
        screen.setChecked(false);
        theme.setChecked(false);
        desktop.setChecked(false);
        reset.setChecked(false);
    }
}
