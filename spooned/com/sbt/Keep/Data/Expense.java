package com.sbt.Keep.Data;


public class Expense implements java.io.Serializable {
    private static final long serialVersionUID = 695677476826163868L;

    private double amount;

    private java.util.Date date;

    private java.lang.String id;

    private java.lang.String item;

    private java.lang.String paidBy;

    private com.sbt.Keep.Data.UploadStatus status;

    private java.lang.String type;

    private java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("0.##");

    @java.lang.Override
    public java.lang.String toString() {
        return ((com.sbt.Keep.Data.Expense.this.item) + ", $") + (decimalFormat.format(com.sbt.Keep.Data.Expense.this.amount));
    }

    public Expense() {
    }

    public double getAmount() {
        return amount;
    }

    public java.util.Date getDate() {
        return date;
    }

    public java.lang.String getId() {
        return id;
    }

    public java.lang.String getItem() {
        return item;
    }

    public java.lang.String getPaidBy() {
        return paidBy;
    }

    public com.sbt.Keep.Data.UploadStatus getStatus() {
        return status;
    }

    public java.lang.String getType() {
        return type;
    }

    public void setAmount(final double amount) {
        com.sbt.Keep.Data.Expense.this.amount = amount;
    }

    public void setDate(final java.util.Date date) {
        com.sbt.Keep.Data.Expense.this.date = date;
    }

    public void setId(final java.lang.String id) {
        com.sbt.Keep.Data.Expense.this.id = id;
    }

    public void setItem(final java.lang.String item) {
        com.sbt.Keep.Data.Expense.this.item = item;
    }

    public void setPaidBy(final java.lang.String paidBy) {
        com.sbt.Keep.Data.Expense.this.paidBy = paidBy;
    }

    public void setStatus(final com.sbt.Keep.Data.UploadStatus status) {
        com.sbt.Keep.Data.Expense.this.status = status;
    }

    public void setType(final java.lang.String type) {
        com.sbt.Keep.Data.Expense.this.type = type;
    }
}

