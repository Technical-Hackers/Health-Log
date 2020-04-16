package com.example.healthlog.handler;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import com.example.healthlog.R;
import com.example.healthlog.interfaces.DialogClickListener;
import com.example.healthlog.model.Doctor;
import com.example.healthlog.model.Patient;

public class PatientLogHandler {

    DialogClickListener dialogClickListener;

    Context context;

    String log;
    EditText logEdit;
    Button save;

    EditText verifyCodeEt;
    Button verifyCodeBtn;

    RadioGroup radioGroup;
    RadioButton checkedRadioButton;

    String verifyCode;

    Patient patient;

    public PatientLogHandler(Context context) {
        this.context = context;

        if (dialog == null) {
            setUp();
        }
    }

    public PatientLogHandler(DialogClickListener dialogClickListener, Context context) {
        this.dialogClickListener = dialogClickListener;
        this.context = context;
    }

    Dialog dialog;

    void setUp() {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_add_log);
        dialog
                .getWindow()
                .setLayout(
                        RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        logEdit = dialog.findViewById(R.id.doctor_activity_addlog_editText);
        radioGroup = dialog.findViewById(R.id.doctor_Activity_dialog_patientStatus_radioGroup);
        save = dialog.findViewById(R.id.doctor_activity_addlog_btn);
        verifyCodeEt = dialog.findViewById(R.id.doctor_activity_verifyCode_eT);
        verifyCodeBtn = dialog.findViewById(R.id.doctor_activity_verifyCode_btn);

        radioGroup.setOnCheckedChangeListener(
                new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        checkedRadioButton = dialog.findViewById(i);
                    }
                });


        save.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkedRadioButton == null) {
                            dialogClickListener.onSaveClicked(logEdit.getText().toString().trim(), null);
                        } else {
                            dialogClickListener.onSaveClicked(
                                    logEdit.getText().toString().trim(), checkedRadioButton.getText().toString());
                        }
                    }
                });

        verifyCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyCode(verifyCodeEt.getText().toString().trim());
            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                resetCode();
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

    // COMPLETED(DJ) verify code and update the ui

    public void verifyCode(String code){
        if (code.equals(verifyCode)){
            verifyCodeEt.setVisibility(View.GONE);
            verifyCodeBtn.setVisibility(View.GONE);
            logEdit.setVisibility(View.VISIBLE);
            radioGroup.setVisibility(View.VISIBLE);
            save.setVisibility(View.VISIBLE);
        }else{
            verifyCodeEt.setError("Invalid code");
        }
    }


    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public void resetCode(){
        verifyCodeEt.setVisibility(View.VISIBLE);
        verifyCodeBtn.setVisibility(View.VISIBLE);
        logEdit.setVisibility(View.GONE);
        radioGroup.setVisibility(View.GONE);
        save.setVisibility(View.GONE);
    }





}
