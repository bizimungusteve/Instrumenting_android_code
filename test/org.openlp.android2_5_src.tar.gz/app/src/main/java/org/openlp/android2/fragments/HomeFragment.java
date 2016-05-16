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
import android.widget.ImageView;
import org.openlp.android2.R;


public class HomeFragment extends Fragment {
    private View displayView;

    public HomeFragment() {
        // Empty constructor required for fragment subclasses
    }

    public static HomeFragment newInstance() {
         return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        displayView = inflater.inflate(R.layout.fragment_home, container, false);
        displayIcon();
        return displayView;
    }

    @Override
    public void onResume() {
        super.onResume();
        displayIcon();
    }

    private void displayIcon(){
        int imageId = getResources().getIdentifier("openlp_splash_screen","drawable", getActivity().getPackageName());
        ((ImageView) displayView.findViewById(R.id.home_image)).setImageResource(imageId);
    }
}
