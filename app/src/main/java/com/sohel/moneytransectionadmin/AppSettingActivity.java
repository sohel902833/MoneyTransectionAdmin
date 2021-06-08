package com.sohel.moneytransectionadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.sohel.moneytransectionadmin.Api.ApiKey;
import com.sohel.moneytransectionadmin.Model.AppSettingModel;
import com.sohel.moneytransectionadmin.Services.Common;

import java.util.HashMap;

public class AppSettingActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText versionEt,priorityEt,cashoutEt,convertBalanceEt,sendBalanceEt,referBonusEt;
    private Button updateVersionButton,updateSettingButton;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_setting);
        init();

        updateVersionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String versionNo=versionEt.getText().toString();
                String priority=priorityEt.getText().toString();
                if(versionNo.isEmpty()){
                    versionEt.setError("Enter Application Version");
                    versionEt.requestFocus();
                }else if(priority.isEmpty()){
                    priorityEt.setError("Enter Priority");
                    priorityEt.requestFocus();
                }else{
                    HashMap<String,Object> hashMap=new HashMap<>();
                    hashMap.put("versionNo", Double.parseDouble(versionNo));
                    hashMap.put("priority",Integer.parseInt(priority));
                    updateAppSettings(hashMap);
                }


            }
        });
        updateSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cashOut=cashoutEt.getText().toString();
                String sendMoney=sendBalanceEt.getText().toString();
                String convertBalance=convertBalanceEt.getText().toString();
                String referBonus=referBonusEt.getText().toString();

                if(cashOut.isEmpty()){
                    Common.showEditTextError(cashoutEt,"Enter cashOut Charge.");
                }else if(sendMoney.isEmpty()){
                    Common.showEditTextError(sendBalanceEt,"Enter Send Money Charge.");
                }else if(convertBalance.isEmpty()){
                    Common.showEditTextError(convertBalanceEt,"Enter Convert Balance Charge.");
                }else if(referBonus.isEmpty()){
                    Common.showEditTextError(referBonusEt,"Enter Refer Bonus.");
                }else{
                    HashMap<String,Object> hashMap=new HashMap<>();
                    hashMap.put("cashOut", Integer.parseInt(cashOut));
                    hashMap.put("sendMoney",Integer.parseInt(sendMoney));
                    hashMap.put("convertBalance",Integer.parseInt(convertBalance));
                    hashMap.put("referBonus",Integer.parseInt(referBonus));
                    updateAppSettings(hashMap);

                }




            }
        });





    }
    private void init() {
        toolbar=findViewById(R.id.appBariId);
        setSupportActionBar(toolbar);
        this.setTitle("Application Setting");

        progressDialog=new ProgressDialog(this);
        versionEt=findViewById(R.id.versionEt);
        priorityEt=findViewById(R.id.priorityEt);
        cashoutEt=findViewById(R.id.cashoutEt);
        convertBalanceEt=findViewById(R.id.convertCashEt);
        sendBalanceEt=findViewById(R.id.sendBalanceEt);
        referBonusEt=findViewById(R.id.referBonusEt);
        updateVersionButton=findViewById(R.id.updateVersionButton);
        updateSettingButton=findViewById(R.id.updateSettingBtn);



    }
    @Override
    protected void onStart() {
        super.onStart();
        loadAppSetting();
    }

    private void loadAppSetting() {
        progressDialog.setMessage("Loading..");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiKey.getAppSetting()
                .addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    try {
                        progressDialog.dismiss();
                        AppSettingModel appSetting=snapshot.getValue(AppSettingModel.class);
                        versionEt.setText(""+appSetting.getVersionNo());
                        priorityEt.setText(""+appSetting.getPriority());
                        cashoutEt.setText(""+appSetting.getCashOut());
                        sendBalanceEt.setText(""+appSetting.getSendMoney());
                        convertBalanceEt.setText(""+appSetting.getConvertBalance());
                        referBonusEt.setText(""+appSetting.getReferBonus());

                    }catch(Exception e){
                        progressDialog.dismiss();
                        e.printStackTrace();
                    }
                }else{
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });
    }
    private void updateAppSettings(HashMap<String,Object> hashMap) {
        progressDialog.setMessage("Updating..");
        progressDialog.setCancelable(false);
        progressDialog.show();



        ApiKey.getAppSetting()
                .updateChildren(hashMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(AppSettingActivity.this, "Updated Successful", Toast.LENGTH_SHORT).show();
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(AppSettingActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}