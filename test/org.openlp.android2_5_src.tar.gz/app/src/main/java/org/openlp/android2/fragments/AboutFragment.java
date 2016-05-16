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
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import org.openlp.android2.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AboutFragment extends Fragment {

    public AboutFragment() {
        // Empty constructor required for fragment subclasses
    }

    public static AboutFragment newInstance() {
        return new AboutFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        String date = new SimpleDateFormat("yyyy").format(new Date());

        StringBuilder html = new StringBuilder();
        html.append(getString(R.string.about_display_1));
        html.append("\n");
        html.append(getString(R.string.about_display_2));
        html.append(" http://www.openlp.org");
        html.append("\n\n\n");
        html.append(getString(R.string.about_display_4));
        html.append(" © 2004-").append(date).append(" Raoul Snyman\n");
        html.append(getString(R.string.about_display_5));
        html.append(" © 2004-").append(date);
        html.append("\nTim Bentley, Tomas Groth, Johan Mynhardt");
        html.append("\n\n\n");
        html.append(getString(R.string.about_display_6));
        html.append("\n");
        html.append(getString(R.string.about_display_7));
        html.append("\n");
        html.append(getString(R.string.about_display_8));

        ((TextView) view.findViewById(R.id.about_text)).setText(html);

        return view;
    }
}