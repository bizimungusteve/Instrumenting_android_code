package com.sbt.Keep.Helper;


public class SpreadsheetHelper {
    private static final int REQUEST_LOAD_SPREADSHEET = 0;

    private static final java.lang.String SPREADSHEET_AUTHTYPE = "oauth2:https://spreadsheets.google.com/feeds/";

    private static final java.lang.String SPREADSHEET_FEED_URL = "https://spreadsheets.google.com/feeds/spreadsheets/private/full";

    private static final java.lang.String SPREADSHEET_NAME = "日常開資";

    private static java.lang.String ITEM_PAID_BY = "Android";

    private java.lang.String authToken;

    private java.util.HashMap<java.lang.String, java.lang.String> ITEM_PAID_BY_MAP;

    private android.app.Activity context;

    public SpreadsheetHelper(android.app.Activity context) {
        com.sbt.Keep.Helper.SpreadsheetHelper.this.context = context;
        ITEM_PAID_BY_MAP = new java.util.HashMap<java.lang.String, java.lang.String>();
        ITEM_PAID_BY_MAP.put("pursylaw@gmail.com", "多寶魚");
        ITEM_PAID_BY_MAP.put("tsangchungwing@gmail.com", "河童");
    }

    private com.google.gdata.client.spreadsheet.SpreadsheetService getService() {
        com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential googleCredential = com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential.usingOAuth2(context, "https://spreadsheets.google.com/feeds", "https://docs.google.com/feeds");
        com.sbt.Keep.Helper.SpreadsheetHelper.ITEM_PAID_BY = googleCredential.getAllAccounts()[0].name;
        googleCredential.setSelectedAccountName(com.sbt.Keep.Helper.SpreadsheetHelper.ITEM_PAID_BY);
        com.google.api.client.auth.oauth2.Credential credential = new GoogleCredential.Builder().setTransport(com.google.api.client.extensions.android.http.AndroidHttp.newCompatibleTransport()).setJsonFactory(new com.google.api.client.json.gson.GsonFactory()).setRequestInitializer(googleCredential).build();
        credential.setAccessToken(authToken);
        com.google.gdata.client.spreadsheet.SpreadsheetService spreadsheetService = new com.google.gdata.client.spreadsheet.SpreadsheetService("com.sbt.keep-v1");
        spreadsheetService.setOAuth2Credentials(credential);
        return spreadsheetService;
    }

    protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        if (resultCode == (android.app.Activity.RESULT_OK)) {
            switch (requestCode) {
                case com.sbt.Keep.Helper.SpreadsheetHelper.REQUEST_LOAD_SPREADSHEET :
                    startSync(((com.sbt.Keep.Data.Expense)(data.getSerializableExtra("expense"))));
                    break;
            }
        } 
    }

    public static int notificationCount = 0;

    private void processData(final com.sbt.Keep.Data.Expense expense) throws com.google.gdata.util.ServiceException, java.io.IOException {
        com.google.gdata.client.spreadsheet.SpreadsheetQuery query = new com.google.gdata.client.spreadsheet.SpreadsheetQuery(new java.net.URL(com.sbt.Keep.Helper.SpreadsheetHelper.SPREADSHEET_FEED_URL));
        query.setTitleQuery(com.sbt.Keep.Helper.SpreadsheetHelper.SPREADSHEET_NAME);
        query.setTitleExact(true);
        android.os.AsyncTask<com.google.gdata.client.spreadsheet.SpreadsheetQuery, java.lang.Integer, java.lang.String> asyncTask = new android.os.AsyncTask<com.google.gdata.client.spreadsheet.SpreadsheetQuery, java.lang.Integer, java.lang.String>() {
            protected void onPostExecute(java.lang.String result) {
                if ((result != null) && (result != "[]")) {
                    java.lang.String notifyContext = ((com.sbt.Keep.Helper.SpreadsheetHelper.SPREADSHEET_NAME) + " Updated, ") + result;
                    android.widget.Toast.makeText(context, notifyContext, android.widget.Toast.LENGTH_LONG).show();
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context).setSmallIcon(android.R.drawable.stat_sys_upload_done).setContentTitle("$$$$").setContentText(notifyContext);
                    android.content.Intent resultIntent = new android.content.Intent(context , com.sbt.Keep.MainActivity.class);
                    android.app.TaskStackBuilder stackBuilder = android.app.TaskStackBuilder.create(context);
                    stackBuilder.addNextIntent(resultIntent);
                    android.app.PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, android.app.PendingIntent.FLAG_UPDATE_CURRENT);
                    mBuilder.setContentIntent(resultPendingIntent);
                    android.app.NotificationManager mNotificationManager = ((android.app.NotificationManager)(context.getSystemService(android.content.Context.NOTIFICATION_SERVICE)));
                    mNotificationManager.notify(((com.sbt.Keep.Helper.SpreadsheetHelper.notificationCount)++), mBuilder.build());
                } 
            }

            @java.lang.Override
            protected java.lang.String doInBackground(com.google.gdata.client.spreadsheet.SpreadsheetQuery... params) {
                com.google.gdata.client.spreadsheet.SpreadsheetService service = getService();
                try {
                    com.google.gdata.data.spreadsheet.SpreadsheetFeed feed = service.query(params[0], com.google.gdata.data.spreadsheet.SpreadsheetFeed.class);
                    java.util.List<com.google.gdata.data.spreadsheet.SpreadsheetEntry> files = feed.getEntries();
                    if ((files.size()) > 0) {
                        java.util.List<com.google.gdata.data.spreadsheet.WorksheetEntry> worksheets = files.get(0).getWorksheets();
                        com.google.gdata.data.spreadsheet.WorksheetEntry worksheetEntry = worksheets.get(0);
                        com.google.gdata.data.spreadsheet.ListFeed lists = service.getFeed(worksheetEntry.getListFeedUrl(), com.google.gdata.data.spreadsheet.ListFeed.class);
                        boolean isNew = false;
                        com.google.gdata.data.spreadsheet.ListEntry entry = null;
                        for (com.google.gdata.data.spreadsheet.ListEntry e : lists.getEntries()) {
                            com.google.gdata.data.spreadsheet.CustomElementCollection customElements = e.getCustomElements();
                            if ((((com.google.gdata.util.common.base.StringUtil.isEmptyOrWhitespace(customElements.getValue("date"))) && (com.google.gdata.util.common.base.StringUtil.isEmptyOrWhitespace(customElements.getValue("amount")))) && (com.google.gdata.util.common.base.StringUtil.isEmptyOrWhitespace(customElements.getValue("item")))) && (com.google.gdata.util.common.base.StringUtil.isEmptyOrWhitespace(customElements.getValue("paidby")))) {
                                entry = e;
                                break;
                            } 
                        }
                        if (entry == null) {
                            entry = new com.google.gdata.data.spreadsheet.ListEntry();
                            isNew = true;
                        } 
                        java.util.Date postDate = new java.util.Date();
                        java.util.Locale.setDefault(java.util.Locale.US);
                        entry.getCustomElements().setValueLocal("date", ((java.lang.String)(android.text.format.DateFormat.format("yyyy-MM-dd h:mm:ss", postDate))));
                        entry.getCustomElements().setValueLocal("year", ((java.lang.String)(android.text.format.DateFormat.format("yyyy", postDate))));
                        entry.getCustomElements().setValueLocal("month", ((java.lang.String)(android.text.format.DateFormat.format("M", postDate))));
                        entry.getCustomElements().setValueLocal("weekday", ((java.lang.String)(android.text.format.DateFormat.format("EEE", postDate))));
                        entry.getCustomElements().setValueLocal("type", "週不時");
                        entry.getCustomElements().setValueLocal("amount", java.lang.Double.toString(expense.getAmount()));
                        entry.getCustomElements().setValueLocal("item", expense.getItem());
                        entry.getCustomElements().setValueLocal("paidby", ITEM_PAID_BY_MAP.get(com.sbt.Keep.Helper.SpreadsheetHelper.ITEM_PAID_BY));
                        if (isNew) {
                            service.insert(worksheetEntry.getListFeedUrl(), entry);
                        } else {
                            entry.update();
                        }
                        return expense.toString();
                    } else {
                    }
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                } catch (com.google.gdata.util.ServiceException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        asyncTask.execute(query);
    }

    @android.annotation.TargetApi(value = android.os.Build.VERSION_CODES.JELLY_BEAN)
    public void startSync(final com.sbt.Keep.Data.Expense expense) {
        android.accounts.AccountManager am = android.accounts.AccountManager.get(context);
        android.accounts.Account[] accounts = am.getAccountsByType(GoogleAccountManager.ACCOUNT_TYPE);
        am.getAuthToken(accounts[0], com.sbt.Keep.Helper.SpreadsheetHelper.SPREADSHEET_AUTHTYPE, null, false, new android.accounts.AccountManagerCallback<android.os.Bundle>() {
            @java.lang.Override
            public void run(android.accounts.AccountManagerFuture<android.os.Bundle> future) {
                try {
                    android.os.Bundle result = future.getResult();
                    authToken = result.getString(android.accounts.AccountManager.KEY_AUTHTOKEN);
                    if ((authToken) == null) {
                        android.content.Intent intent = ((android.content.Intent)(result.get(android.accounts.AccountManager.KEY_INTENT)));
                        context.startActivityForResult(intent, com.sbt.Keep.Helper.SpreadsheetHelper.REQUEST_LOAD_SPREADSHEET);
                    } else {
                        processData(expense);
                    }
                } catch (android.accounts.OperationCanceledException e) {
                    e.printStackTrace();
                } catch (android.accounts.AuthenticatorException e) {
                    e.printStackTrace();
                } catch (java.io.IOException e) {
                    e.printStackTrace();
                } catch (com.google.gdata.util.ServiceException e) {
                    e.printStackTrace();
                }
            }
        }, null);
    }
}

