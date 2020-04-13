package com.example.healthlog.handler;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.healthlog.R;
import com.example.healthlog.interfaces.DialogClickListener;
import com.example.healthlog.model.Patient;

public class PatientLogHandler {

    DialogClickListener dialogClickListener;

    Context context;
    Activity activity;

    String log;
    EditText logEdit;

    RadioGroup radioGroup;
    RadioButton checkedRadioButton;

    Patient patient;

    public PatientLogHandler(Context context, Activity activity) {
        this.activity = activity;
        this.context = context;

        if(dialog == null) {
            setUp();
        }
    }

    public PatientLogHandler(DialogClickListener dialogClickListener, Context context, Activity activity) {
        this.dialogClickListener = dialogClickListener;
        this.context = context;
        this.activity = activity;
    }

    Dialog dialog;

    void setUp() {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_log);
        dialog.getWindow().setLayout(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        logEdit = dialog.findViewById(R.id.doctor_activity_addlog_editText);
        radioGroup = dialog.findViewById(R.id.doctor_Activity_dialog_patientStatus_radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                checkedRadioButton = dialog.findViewById(i);
            }
        });

        Button save = dialog.findViewById(R.id.doctor_activity_addlog_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkedRadioButton == null){
                    dialogClickListener.onSaveClicked(logEdit.getText().toString().trim(), null);
                }else{
                    dialogClickListener.onSaveClicked(logEdit.getText().toString().trim(), checkedRadioButton.getText().toString());
                }
            }
        });
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

    public void setDialogClickListener(DialogClickListener dialogClickListener) {
        this.dialogClickListener = dialogClickListener;
    }
}
