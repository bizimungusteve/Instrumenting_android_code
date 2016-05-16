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

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class JsonHelpers {

    private static String LOG_TAG = JsonHelpers.class.getName();

    public static String createRequestJSON(String key, String value) throws JSONHandlerException {
        try {
            String responseJSON;
            JSONObject jo = new JSONObject();
            jo.put(key, value);
            responseJSON = new JSONStringer().object().key("request").value(jo)
                    .endObject().toString();
            responseJSON = URLEncoder.encode(responseJSON, "UTF-8");
            return responseJSON;
        } catch (JSONException e) {
            throw new JSONHandlerException(e);
        } catch (UnsupportedEncodingException e) {
            throw new JSONHandlerException(e);
        }
    }

    public static class JSONHandlerException extends Exception {
        private static final long serialVersionUID = -6772307308404816615L;

        public JSONHandlerException(Throwable throwable) {
            super(throwable);
        }
    }
}
