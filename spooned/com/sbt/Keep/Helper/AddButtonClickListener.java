package com.sbt.Keep.Helper;


public class AddButtonClickListener implements android.view.View.OnClickListener {
    private com.sbt.Keep.ExpenseAdapter historyAdapter;

    private android.app.Activity activity;

    private java.lang.String item;

    @java.lang.Override
    public void onClick(android.view.View v) {
        /*
************************
 
*you can add code here*
 
***********************/;
        final android.widget.EditText priceText = ((android.widget.EditText)(getActivity().findViewById(com.sbt.Keep.R.id.priceText)));
        final android.text.Editable text = priceText.getText();
        if ((text.length()) > 0) {
            com.sbt.Keep.Data.Expense expense = new com.sbt.Keep.Data.Expense();
            expense.setId(java.util.UUID.randomUUID().toString());
            expense.setAmount(java.lang.Double.parseDouble(text.toString()));
            if (com.google.gdata.util.common.base.StringUtil.isEmpty(getItem())) {
                expense.setItem(((android.widget.Button)(v)).getText().toString());
            } else {
                expense.setItem(getItem());
            }
            com.sbt.Keep.Helper.AddButtonClickListener.this.historyAdapter.add(expense);
            com.sbt.Keep.Helper.AddButtonClickListener.this.historyAdapter.notifyDataSetChanged();
            priceText.setText("");
            final android.widget.ListView historyView = ((android.widget.ListView)(getActivity().findViewById(com.sbt.Keep.R.id.historyView)));
            final int newPosition = (getHistoryAdapter().getCount()) - 1;
            historyView.setSelection(newPosition);
            com.sbt.Keep.Helper.SpreadsheetHelper helper = new com.sbt.Keep.Helper.SpreadsheetHelper(activity);
            helper.startSync(expense);
        } 
    }

    public com.sbt.Keep.ExpenseAdapter getHistoryAdapter() {
        return historyAdapter;
    }

    public void setHistoryAdapter(com.sbt.Keep.ExpenseAdapter historyAdapter) {
        com.sbt.Keep.Helper.AddButtonClickListener.this.historyAdapter = historyAdapter;
    }

    public android.app.Activity getActivity() {
        return activity;
    }

    public void setActivity(android.app.Activity activity) {
        com.sbt.Keep.Helper.AddButtonClickListener.this.activity = activity;
    }

    public java.lang.String getItem() {
        return item;
    }

    public void setItem(java.lang.String item) {
        com.sbt.Keep.Helper.AddButtonClickListener.this.item = item;
    }
}

