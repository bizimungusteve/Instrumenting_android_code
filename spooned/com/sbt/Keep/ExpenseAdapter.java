package com.sbt.Keep;


public class ExpenseAdapter extends android.widget.ArrayAdapter<com.sbt.Keep.Data.Expense> {
    private java.util.ArrayList<com.sbt.Keep.Data.Expense> expenses = new java.util.ArrayList<com.sbt.Keep.Data.Expense>();

    public java.util.ArrayList<com.sbt.Keep.Data.Expense> getExpenses() {
        return expenses;
    }

    public ExpenseAdapter(android.content.Context context ,int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @java.lang.Override
    public void add(com.sbt.Keep.Data.Expense object) {
        super.add(object);
        com.sbt.Keep.ExpenseAdapter.this.expenses.add(object);
    }

    @java.lang.Override
    public android.view.View getView(int position, android.view.View convertView, android.view.ViewGroup parent) {
        android.widget.TextView view = ((android.widget.TextView)(super.getView(position, convertView, parent)));
        return view;
    }

    public void setExpenses(java.util.ArrayList<com.sbt.Keep.Data.Expense> expenses) {
        com.sbt.Keep.ExpenseAdapter.this.expenses = expenses;
    }
}

