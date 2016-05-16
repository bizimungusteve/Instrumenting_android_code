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
package org.openlp.android2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.app.ActionBar;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.view.WindowManager;

import org.openlp.android2.activities.SettingsActivity;
import org.openlp.android2.common.NavigationOptions;
import org.openlp.android2.dialogs.AlertDisplayDialog;
import org.openlp.android2.dialogs.BlankDisplayDialog;
import org.openlp.android2.fragments.AboutFragment;
import org.openlp.android2.fragments.HomeFragment;
import org.openlp.android2.fragments.LiveListFragment;
import org.openlp.android2.fragments.LiveWebFragment;
import org.openlp.android2.fragments.NavigationDrawerFragment;
import org.openlp.android2.fragments.SearchFragment;
import org.openlp.android2.fragments.ServiceListFragment;
import org.openlp.android2.fragments.StageWebFragment;


public class OpenLP extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private final String LOG_TAG = OpenLP.class.getName();
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doPreferenceCheck();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    /**
     * Check the preferences have been set at startup and if not redirect them to be set.
     *
     */
    protected void doPreferenceCheck() {
        SharedPreferences sharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        if (sharedPrefs.getString(getString(R.string.key_host), "NONE").equals("NONE")
                || sharedPrefs.getString(getString(R.string.key_host), null).equals(null)) {
            Log.d(LOG_TAG,
                    "URL preference not set. Starting preference activity...");
            Intent preferenceIntent = new Intent(this, SettingsActivity.class);
            startActivity(preferenceIntent);
        }
    }

    /**
     * Handle configuration change.
     *
     * @param newConfig The new Config.
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * Handle the selection of the Navigation Menu
     *
     * @param position Which item has been selected.
     */
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        switch (position) {
            case NavigationOptions.Home:
                singleTab();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, HomeFragment.newInstance())
                        .commit();
                mTitle = getString(R.string.home);
                toggerContainer(R.id.next_button, View.GONE);
                toggerContainer(R.id.prev_button, View.GONE);
                break;
            case NavigationOptions.ServiceList:
                singleTab();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ServiceListFragment.newInstance(), "servicelist")
                        .commit();
                mTitle = getString(R.string.service_list);
                toggerContainer(R.id.next_button, View.VISIBLE);
                toggerContainer(R.id.prev_button, View.VISIBLE);
                break;
            case NavigationOptions.LiveList:
                duelTab();
                fragmentManager.beginTransaction()
                        .replace(R.id.container_left, ServiceListFragment.newInstance(), "servicelist")
                        .commit();
                fragmentManager.beginTransaction()
                        .replace(R.id.container_right, LiveListFragment.newInstance())
                        .commit();
                mTitle = getString(R.string.live_list);
                toggerContainer(R.id.next_button, View.VISIBLE);
                toggerContainer(R.id.prev_button, View.VISIBLE);
                break;
            case NavigationOptions.StageView:
                singleTab();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, StageWebFragment.newInstance())
                        .commit();
                mTitle = getString(R.string.stage_view);
                toggerContainer(R.id.next_button, View.GONE);
                toggerContainer(R.id.prev_button, View.GONE);
                break;
            case NavigationOptions.LiveView:
                singleTab();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, LiveWebFragment.newInstance())
                        .commit();
                mTitle = getString(R.string.live_view);
                toggerContainer(R.id.next_button, View.GONE);
                toggerContainer(R.id.prev_button, View.GONE);
                break;
            case NavigationOptions.Search:
                singleTab();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, SearchFragment.newInstance())
                        .commit();
                mTitle = getString(R.string.action_search);
                toggerContainer(R.id.next_button, View.GONE);
                toggerContainer(R.id.prev_button, View.GONE);
                break;
            default:
                singleTab();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, HomeFragment.newInstance())
                        .commit();
                mTitle = getString(R.string.home);
                toggerContainer(R.id.next_button, View.GONE);
                toggerContainer(R.id.prev_button, View.GONE);
                break;
        }
    }

    /**
     * Set Display to allow for Duel Columns
     */
    protected void duelTab(){
        toggerContainer(R.id.container, View.GONE);
        toggerContainer(R.id.container_right, View.VISIBLE);
        toggerContainer(R.id.container_left, View.VISIBLE);
    }

    /**
     * Set Display to allow for Single Columns
     */
    protected void singleTab(){
        toggerContainer(R.id.container_right, View.GONE);
        toggerContainer(R.id.container_left, View.GONE);
        toggerContainer(R.id.container, View.VISIBLE);
    }

    /**
     *
     * @param container The container id to be accesses
     * @param direction What visibility to use on the container
     */
    protected void toggerContainer(int container, int direction){
        View cTainer = this.findViewById(container);
        if (cTainer != null) {
            cTainer.setVisibility(direction);
        }
    }

    public void restoreActionBar() {
        try {
            ActionBar actionBar = getActionBar();
            //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(mTitle);
        } catch (Exception e) {
            //noop
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.open_l, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_preferences:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_refresh:
                if (this.mTitle.equals(getString(R.string.service_list))) {
                    refreshServiceFragment(R.id.container);
                } else if (this.mTitle.equals(getString(R.string.live_list)) ) {
                    refreshServiceFragment(R.id.container_left);
                    refreshLiveFragment(R.id.container_right);
                }
                return true;
            case R.id.action_blank:
                new BlankDisplayDialog().show(getFragmentManager(), "BlankDialog");
                return true;
            case R.id.action_alert:
                new AlertDisplayDialog().show(getFragmentManager(), "AlertDialog");
                return true;
            case R.id.action_about:
                getFragmentManager().beginTransaction().replace(R.id.container,
                        new AboutFragment()).commit();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void refreshLiveFragment(int container){
        LiveListFragment fragment = (LiveListFragment)
                getFragmentManager().findFragmentById(container);
        if (fragment != null) {
            fragment.refreshDisplay();
        }
    }
    public void refreshServiceFragment(int container){
        ServiceListFragment fragment = (ServiceListFragment)
                getFragmentManager().findFragmentById(container);
        if (fragment != null) {
            fragment.refreshDisplay();
        }
    }

    public void next(View view) {
        ServiceListFragment serviceListFragment = (ServiceListFragment) getFragmentManager().findFragmentByTag("servicelist");
        serviceListFragment.next();
    }

    public void previous(View view) {
        ServiceListFragment serviceListFragment = (ServiceListFragment) getFragmentManager().findFragmentByTag("servicelist");
        serviceListFragment.previous();
    }
}
