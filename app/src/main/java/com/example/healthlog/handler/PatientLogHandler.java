package com.example.healthlog.handler;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.healthlog.R;
import com.example.healthlog.model.Patient;

public class PatientLogHandler {

    Context context;
    Activity activity;

    String log;
    EditText logEdit;

    Patient patient;

    public PatientLogHandler(Context context, Activity activity) {
        this.activity = activity;
        this.context = context;

        if(dialog == null) {
            setUp();
        }
    }

    Dialog dialog;

    void setUp() {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_log);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        logEdit = dialog.findViewById(R.id.doctor_activity_addlog_editText);
        this.log = logEdit.toString();
    }

    public void init() {
        dialog.show();
    }

    public void destroyDialog() {
        dialog.dismiss();
    }

    public String getLog() {
        return this.log;
    }
}
