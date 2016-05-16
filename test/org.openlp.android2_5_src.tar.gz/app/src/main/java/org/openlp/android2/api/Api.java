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
package org.openlp.android2.api;

/**
 * <h1>Routes:</h1>
 * <p/>
 * <p/>
 * <pre>
 * ``/``
 * Go to the web interface.
 *
 * ``/files/{filename}``
 *
 * ``/api/poll``
 * {"results": {"type": "controller"}}
 * Or, if there were no results, False::
 * {"results": False}
 *
 * ``/api/display/{hide|show}``
 * Blank or unblank the screen.
 *
 * ``/api/alert``
 * {"request": {"text": "<your alert text>"}}
 * ``/api/controller/{live|preview}/{action}``
 * ``next``
 * Load the next slide.
 *
 * ``previous``
 * Load the previous slide.
 *
 * ``set``
 * Set a specific slide. Requires an id return in a JSON-encoded dict like
 * this::
 *
 * {"request": {"id": 1}}
 *
 * ``first``
 * Load the first slide.
 *
 * ``last``
 * Load the last slide.
 *
 * ``text``
 * Fetches the text of the current song. The output is a JSON-encoded
 * dict which looks like this::
 *
 * {"result": {"slides": ["...", "..."]}}
 *
 * ``/api/service/{action}``
 * Perform ``{action}`` on the service manager (e.g. go live). Data is
 * passed as a json-encoded ``data`` parameter. Valid actions are:
 *
 * ``next``
 * Load the next item in the service.
 *
 * ``previous``
 *
 * ``set``
 * Set a specific item in the service. Requires an id returned in a
 * JSON-encoded dict like this::
 *
 * {"request": {"id": 1}}
 *
 * ``list``
 * Request a list of items in the service. Returns a list of items in the
 * current service in a JSON-encoded dict like this::
 *
 * {"results": {"items": [{...}, {...}]}}
 * """
 * </pre>
 */

public interface Api {

    public final String LIVE_BASE = "/api/controller/live/";
    public final String LIVE_NEXT = "/api/controller/live/next";
    public final String LIVE_PREVIOUS = "/api/controller/live/previous";
    public final String LIVE_TEXT = "/api/controller/live/text";
    public final String LIVE_SET = "/api/controller/live/set?data=";
    public final String STAGE_VIEW = "/stage";
    public final String LIVE_VIEW = "/main";

    public final String SERVICE_LIST = "/api/service/list";
    public final String SERVICE_SET = "/api/service/set?data=";

    public final String DISPLAY_SHOW = "/api/display/show";
    public final String DISPLAY_BLANK = "/api/display/blank";
    public final String DISPLAY_THEME = "/api/display/theme";
    public final String DISPLAY_DESKTOP = "/api/display/desktop";
    public final String POLL_STATUS = "/api/poll";

    public final String ALERT = "/api/alert?data=";

    public final String SEARCHABLE_PLUGINS = "/api/plugin/search";
    /**
     * This is a special string that uses the String.format() method. See
     * {@link String#format(String, Object...)}
     */
    public final String SEARCH_PLUGIN_FORMATTED = "/api/%s/search?data=";
    /**
     * Match intent extra key with regex since multiple plugins can be inserted
     */
    public final String SEARCH_PLUGIN_ADD = "/api/%s/add?data=";
    /**
     * Match intent extra key with regex since multiple plugins can be inserted
     */
    public final String SEARCH_PLUGIN_LIVE = "/api/%s/live?data=";
}
