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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.openlp.android2.R;
import org.openlp.android2.api.Api;
import org.openlp.android2.common.JsonHelpers;
import org.openlp.android2.common.OpenLPDialog;
import org.openlp.android2.common.OpenLPHttpClient;

public class AlertDisplayDialog extends OpenLPDialog {
    private final String LOG_TAG = AlertDisplayDialog.class.getName();
    public AlertDialog dialog;

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
        View view = inflater.inflate(R.layout.alert_display_dialog, null);
        builder.setView(view);

        builder.setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AlertDisplayDialog.this.getDialog().cancel();
            }
        });
        builder.setNegativeButton(R.string.process, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog1, int id) {
                EditText text = (EditText) dialog.findViewById(R.id.alertText);
                requestAlert(text.getText().toString());
            }
        });
        dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogI) {
                Button btnNegative = dialog.getButton(Dialog.BUTTON_NEGATIVE);
                btnNegative.setTextSize(20);
                Button btnPositive = dialog.getButton(Dialog.BUTTON_POSITIVE);
                btnPositive.setTextSize(20);
            }
        });
        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG, "Resuming...");
    }

    public void processUpdate(String response) {
        Toast.makeText(context, "Alert Requested", Toast.LENGTH_SHORT).show();
    }

    public void requestAlert(String text) {
        try {
            String request = JsonHelpers.createRequestJSON("text", text);
            triggerTextRequest(String.format("%s%s", Api.ALERT, request));
            Log.d(LOG_TAG, String.format("Setting list data. apiBase(%s), text(%s)", Api.ALERT, text));
        } catch (JsonHelpers.JSONHandlerException e) {
            e.printStackTrace();
            Toast.makeText(context, "Request Failed", Toast.LENGTH_SHORT).show();
        }
    }
}
