package com.sbt.Keep;


public class MainActivity extends android.app.Activity {
    protected static final int REQUEST_CREATE_SPREADSHEET = 1;

    protected static final int REQUEST_LOAD_SPREADSHEET = 0;

    protected static final java.lang.String SPREADSHEET_AUTHTYPE = "oauth2:https://spreadsheets.google.com/feeds/";

    private com.sbt.Keep.ExpenseAdapter historyAdapter;

    private final android.app.Activity context = com.sbt.Keep.MainActivity.this;

    @java.lang.Override
    protected void onCreate(final android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.sbt.Keep.R.layout.activity_main);
        setupUI();
    }

    @java.lang.Override
    public boolean onCreateOptionsMenu(final android.view.Menu menu) {
        getMenuInflater().inflate(com.sbt.Keep.R.menu.activity_main, menu);
        android.view.MenuItem clearHistoryMenu = menu.findItem(com.sbt.Keep.R.id.clear_history);
        clearHistoryMenu.setOnMenuItemClickListener(new android.view.MenuItem.OnMenuItemClickListener() {
            @java.lang.Override
            public boolean onMenuItemClick(android.view.MenuItem item) {
                return false;
            }
        });
        return true;
    }

    private void setupUI() {
        final android.widget.ListView historyView = ((android.widget.ListView)(findViewById(com.sbt.Keep.R.id.historyView)));
        historyAdapter = new com.sbt.Keep.ExpenseAdapter(com.sbt.Keep.MainActivity.this , android.R.layout.simple_list_item_1);
        historyView.setAdapter(historyAdapter);
        final android.widget.Button addButton1 = ((android.widget.Button)(findViewById(com.sbt.Keep.R.id.addButton1)));
        final android.widget.Button addButton2 = ((android.widget.Button)(findViewById(com.sbt.Keep.R.id.addButton2)));
        final android.widget.Button addButton3 = ((android.widget.Button)(findViewById(com.sbt.Keep.R.id.addButton3)));
        final android.widget.Button addButtonOthers = ((android.widget.Button)(findViewById(com.sbt.Keep.R.id.addButtonOthers)));
        final android.widget.Button addButton5 = ((android.widget.Button)(findViewById(com.sbt.Keep.R.id.addButton5)));
        final android.widget.Button addButton6 = ((android.widget.Button)(findViewById(com.sbt.Keep.R.id.addButton6)));
        final android.widget.Button addButton7 = ((android.widget.Button)(findViewById(com.sbt.Keep.R.id.addButton7)));
        final android.widget.Button addButton8 = ((android.widget.Button)(findViewById(com.sbt.Keep.R.id.addButton8)));
        final android.widget.Button addButton9 = ((android.widget.Button)(findViewById(com.sbt.Keep.R.id.addButton9)));
        final android.widget.Button addButton10 = ((android.widget.Button)(findViewById(com.sbt.Keep.R.id.addButton10)));
        final android.widget.Button addButton11 = ((android.widget.Button)(findViewById(com.sbt.Keep.R.id.addButton11)));
        final android.widget.Button addButton12 = ((android.widget.Button)(findViewById(com.sbt.Keep.R.id.addButton12)));
        com.sbt.Keep.Helper.AddButtonClickListener addButtonClickListener = new com.sbt.Keep.Helper.AddButtonClickListener();
        addButtonClickListener.setHistoryAdapter(historyAdapter);
        addButtonClickListener.setActivity(com.sbt.Keep.MainActivity.this);
        addButton1.setOnClickListener(addButtonClickListener);
        addButton2.setOnClickListener(addButtonClickListener);
        addButton3.setOnClickListener(addButtonClickListener);
        addButton5.setOnClickListener(addButtonClickListener);
        addButton6.setOnClickListener(addButtonClickListener);
        addButton7.setOnClickListener(addButtonClickListener);
        addButton8.setOnClickListener(addButtonClickListener);
        addButton9.setOnClickListener(addButtonClickListener);
        addButton10.setOnClickListener(addButtonClickListener);
        addButton11.setOnClickListener(addButtonClickListener);
        addButton12.setOnClickListener(addButtonClickListener);
        addButtonOthers.setOnClickListener(new android.view.View.OnClickListener() {
            @java.lang.Override
            public void onClick(android.view.View arg0) {
                /* you can add code here */;
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                android.view.LayoutInflater li = android.view.LayoutInflater.from(context);
                android.view.View promptsView = li.inflate(com.sbt.Keep.R.layout.prompt, null);
                builder.setView(promptsView);
                final android.widget.EditText userInput = ((android.widget.EditText)(promptsView.findViewById(com.sbt.Keep.R.id.editTextDialogUserInput)));
                userInput.setText("未定義");
                builder.setCancelable(true);
                builder.setPositiveButton("Ok", new android.content.DialogInterface.OnClickListener() {
                    @java.lang.Override
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        com.sbt.Keep.Helper.AddButtonClickListener addButtonClickListener = new com.sbt.Keep.Helper.AddButtonClickListener();
                        addButtonClickListener.setHistoryAdapter(historyAdapter);
                        addButtonClickListener.setItem(userInput.getText().toString());
                        addButtonClickListener.setActivity(context);
                        addButtonClickListener.onClick(null);
                    }
                });
                builder.create();
                builder.show();
                userInput.requestFocus();
                userInput.selectAll();
                userInput.postDelayed(new java.lang.Runnable() {
                    @java.lang.Override
                    public void run() {
                        android.view.inputmethod.InputMethodManager keyboard = ((android.view.inputmethod.InputMethodManager)(getSystemService(android.content.Context.INPUT_METHOD_SERVICE)));
                        keyboard.showSoftInput(userInput, 0);
                    }
                }, 200);
            }
        });
        final android.widget.EditText priceText = ((android.widget.EditText)(findViewById(com.sbt.Keep.R.id.priceText)));
        priceText.setImeOptions(android.view.inputmethod.EditorInfo.IME_ACTION_DONE);
        priceText.setImeActionLabel(addButtonOthers.getText(), android.view.inputmethod.EditorInfo.IME_ACTION_DONE);
        priceText.setOnEditorActionListener(new android.widget.TextView.OnEditorActionListener() {
            @java.lang.Override
            public boolean onEditorAction(android.widget.TextView v, int actionId, android.view.KeyEvent event) {
                if (actionId == (android.view.inputmethod.EditorInfo.IME_ACTION_DONE)) {
                    final android.widget.Button addButton = ((android.widget.Button)(((android.app.Activity)(v.getContext())).findViewById(com.sbt.Keep.R.id.addButtonOthers)));
                    addButton.performClick();
                    return true;
                } 
                return false;
            }
        });
        priceText.requestFocus();
        com.sbt.Keep.MainActivity.this.setTitle("");
    }
}

