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

import android.content.Context;
import org.openlp.android2.R;

public class OpenLPHttpReturn {
    private int return_code = 0;
    private String data = null;
    private Context context;

    public OpenLPHttpReturn() {
        this.return_code = -1;
        this.data = "";
        this.context = null;
    }

    public OpenLPHttpReturn(int return_code, String data, Context context) {
        this.return_code = return_code;
        this.data = data;
        this.context = context;
    }

    public String getData() {
        return this.data;
    }

    public boolean isError() {
        return return_code != 0;
    }

    public boolean isSecurityError() {
        return return_code == 401;
    }

    public String getErrorMessage(String message) {
        return return_code == 401 ? this.context.getString(R.string.httpreturn_unauthorised) : message;
    }

    @Override
    public String toString() {
        return "HttpReturn{" + "data='" + data + '\'' + ", return code=" + return_code + '}';
    }
}
