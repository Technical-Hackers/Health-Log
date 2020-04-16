package com.example.healthlog.handler;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.example.healthlog.HealthLog;
import com.example.healthlog.R;
import com.example.healthlog.model.Doctor;
import com.example.healthlog.model.Patient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NewPatientHandler {

    // COMPLETED(DJ) allot patient to doctor using desired logic

    // activity
    Context context;
    // Date object for dob
    Date date;

    // dialog
    Dialog dialog;

    // editText
    EditText name;
    EditText address;
    EditText dob;
    EditText floor;
    EditText roomNo;
    EditText bedNo;

    // button
    Button submit;
    Button cancel;

    // textView
    TextView id;

    // String
    String patientId;

    // firebase reference
    FirebaseFirestore mRef;

    int size;

    public NewPatientHandler(Context context) {
        this.context = context;
        // this.activity = activity;
        setUp();
    }

    void setUp() {
        mRef = FirebaseFirestore.getInstance();
        fetchPatientMetaData();
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.new_patient_layout);
        dialog
                .getWindow()
                .setLayout(
                        RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        id = dialog.findViewById(R.id.new_patient_layout_id_tV);

        name = dialog.findViewById(R.id.new_patient_layout_name_eT);
        address = dialog.findViewById(R.id.new_patient_layout_address_eT);
        dob = dialog.findViewById(R.id.new_patient_layout_dob_eT);
        floor = dialog.findViewById(R.id.new_patient_layout_floor_eT);
        roomNo = dialog.findViewById(R.id.new_patient_layout_roomNo_eT);
        bedNo = dialog.findViewById(R.id.new_patient_layout_bedNo_eT);

        cancel = dialog.findViewById(R.id.new_patient_layout_cancel_btn);
        submit = dialog.findViewById(R.id.new_patient_layout_submit_btn);

        (dob)
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                    pickDate();
                                }
                            }
                        });

        cancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        destroy();
                    }
                });

        submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        createNewPatient();
                    }
                });

        name.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

                    @Override
                    public void afterTextChanged(Editable editable) {
                        id.setText("Patient id: " + getCompleteId());
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    void pickDate() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(context);

        datePickerDialog.setOnDateSetListener(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        dob.setText(day + "/" + month + "/" + year);
                        date = new Date(year, month, day);
                    }
                });

        datePickerDialog.show();
    }

    // COMPLETED(Danish) implement method
    void createNewPatient() {
        /*
        * 1. Create new patient object
        * 2. Initiate server
        * 3. Upload data
        */
        List<String> location = new ArrayList<>();
        location.add(floor.getText().toString().trim());
        location.add(bedNo.getText().toString().trim());
        location.add(roomNo.getText().toString().trim());

        final Patient patient = new Patient();

        patient.setId(getCompleteId());
        patient.setName(name.getText().toString().trim());
        patient.setAddress(address.getText().toString().trim());
        patient.setDob(dob.getText().toString().trim());
        patient.setLocation(location);
        patient.setRecentLog("Log");
        patient.setStatus("Active");
        patient.setAge(calculateAge());

        final CollectionReference patientRef =
                mRef.collection("Hospital").document(HealthLog.ID).collection("Patient");

        patientRef
                .document(patient.getId())
                .set(patient)
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    patientRef
                                            .document(patient.getId())
                                            .update("dateAdded", FieldValue.serverTimestamp())
                                            .addOnCompleteListener(
                                                    new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                // COMPLETED(DJ) call @updatePatientMetaData() and move below logic
                                                                // in that function
                                                                updatePatientMetaData(patientRef);
                                                            }
                                                        }
                                                    });
                                }
                            }
                        });
    }

    // COMPLETED(DJ) update meta-data
    void updatePatientMetaData(CollectionReference patientRef) {
        final DocumentReference metaDataRef = patientRef.document("meta-data");

        mRef.runTransaction(
                        new Transaction.Function<Void>() {
                            @Nullable
                            @Override
                            public Void apply(@NonNull Transaction transaction)
                                    throws FirebaseFirestoreException {
                                transaction.update(metaDataRef, "size", FieldValue.increment(1));
                                transaction.update(metaDataRef, "active", FieldValue.increment(1));
                                return null;
                            }
                        })
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                fetchDoctor();
                            }
                        });
    }

    void fetchDoctor() {
        mRef.collection("Hospital")
                .document(HealthLog.ID)
                .collection("Doctor")
                .orderBy("noOfPatients", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(
                        new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    List<Doctor> doctors = new ArrayList<>();
                                    for (DocumentSnapshot d : task.getResult()) {
                                        doctors.add(d.toObject(Doctor.class));
                                    }
                                    setRoutine(doctors);
                                }
                            }
                        });
    }

    void setRoutine(List<Doctor> doctors) {
        Doctor currentDoctor = null;
        for (int i = 0; i < doctors.size() - 1; i++) {
            if ((doctors.get(i).getNoOfPatients() - 1) == doctors.get(i + 1).getNoOfPatients()
                    && doctors.get(i + 1).getStatus().equals("Available")) {
                currentDoctor = doctors.get(i + 1);
                break;
            }
        }
        if (currentDoctor == null) {
            currentDoctor = doctors.get(0);
        }
        toast("server initialising");
        initiateRoutineServer(currentDoctor);
    }

    void initiateRoutineServer(final Doctor doctor) {

        final CollectionReference doctorRef =
                mRef.collection("Hospital").document(HealthLog.ID).collection("Doctor");
        DocumentReference patientRef =
                mRef.collection("Hospital")
                        .document(HealthLog.ID)
                        .collection("Patient")
                        .document(getCompleteId());

        doctorRef
                .document(doctor.getId())
                .collection("Routine")
                .document("Routine")
                .update("patientList", FieldValue.arrayUnion(patientRef))
                .addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    toast("REf added ");
                                    doctorRef
                                            .document(doctor.getId())
                                            .update("noOfPatients", FieldValue.increment(1))
                                            .addOnCompleteListener(
                                                    new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            toast("done");
                                                            destroy();
                                                        }
                                                    });
                                } else {

                                    Log.i("NEWPATIENTHANDLER:>>", task.getException().getLocalizedMessage());
                                    toast(task.getException().getLocalizedMessage());
                                }
                            }
                        });
    }

    void toast(String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    String calculateAge() {
        String result = "";

        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(date.getYear(), date.getMonth(), date.getDay());

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        result = (new Integer(age)).toString();

        return result;
    }

    String getCompleteId() {
        return patientId + (name.getText().toString().trim()).split(" ")[0];
    }

    void fetchPatientMetaData() {
        mRef.collection("Hospital")
                .document(HealthLog.ID)
                .collection("Patient")
                .document("meta-data")
                .get()
                .addOnCompleteListener(
                        new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful() && task.getResult() != null) {
                                    size = task.getResult().getDouble("size").intValue();
                                    patientId = "P" + size + "_";
                                    id.setText("Patient id: " + patientId);
                                }
                            }
                        });
    }

    public void setName(String name) {
        this.name.setText(name);
    }

    public void setAddress(String address) {
        this.address.setText(address);
    }

    public void setDob(String dob) {
        this.dob.setText(dob);
    }

    public void setFloor(String floor) {
        this.floor.setText(floor);
    }

    public void setRoomNo(String roomNo) {
        this.roomNo.setText(roomNo);
    }

    public void setBedNo(String bedNo) {
        this.bedNo.setText(bedNo);
    }

    public void init() {
        dialog.show();
    }

    public void destroy() {
        dialog.dismiss();
    }
}
