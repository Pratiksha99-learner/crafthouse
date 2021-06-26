package com.mystore.crafthouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Payment extends AppCompatActivity {

    private static final int TEZ_REQUEST_CODE = 123;

    private static final String GOOGLE_TEZ_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
    private static final int UPI_PAYMENT = 100;
    //EditText amount, note, name, upivirtualid;

    TextView amount;
    String name,note,upivirtualid;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        /*send = (Button) findViewById(R.id.send);
        amount = (EditText) findViewById(R.id.amount_et);
        note = (EditText) findViewById(R.id.note);
        name = (EditText) findViewById(R.id.name);
        upivirtualid = (EditText) findViewById(R.id.upi_id);*/

        send = (Button) findViewById(R.id.send);
        amount = (TextView) findViewById(R.id.amount_et);
        note = "Test UPI Payment";
        name = "Ganesh Garad";
        upivirtualid = "Q454292678@ybl";

        Button payButton = findViewById(R.id.send);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Getting the values from the EditTexts
                /*if (TextUtils.isEmpty(name.getText().toString().trim())) {
                    Toast.makeText(Payment.this, " Name is invalid", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(upivirtualid.getText().toString().trim())) {
                    Toast.makeText(Payment.this, " UPI ID is invalid", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(note.getText().toString().trim())) {
                    Toast.makeText(Payment.this, " Note is invalid", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(amount.getText().toString().trim())) {
                    Toast.makeText(Payment.this, " Amount is invalid", Toast.LENGTH_SHORT).show();
                } else {
                    payUsingUpi(name.getText().toString(),upivirtualid.getText().toString(),note.getText().toString(),amount.getText().toString());
                }*/

                payUsingUpi(name.toString(),upivirtualid,note,amount.toString());
            }


        });

    }//END OF ONCREATE()

    private void payUsingUpi(String nm, String uid, String nt, String amt) {

        Log.e("main ", "name " + nm + "--up--" + uid + "--" + nt + "--" + amt);

        Uri uri =
                new Uri.Builder()
                        .scheme("upi")
                        .authority("pay")
                        .appendQueryParameter("pa",uid)
                        .appendQueryParameter("pn",nm)
                        .appendQueryParameter("mc", "")
                        .appendQueryParameter("tr", "") //123456789
                        .appendQueryParameter("tn", "test transaction note")
                        .appendQueryParameter("am", "1")
                        .appendQueryParameter("cu", "INR")
                        //.appendQueryParameter("url", "https://test.merchant.website")
                        .build();
        /*Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.setPackage(GOOGLE_TEZ_PACKAGE_NAME);
        startActivityForResult(intent, TEZ_REQUEST_CODE);*/

        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);
        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");
        // check if intent resolves
        if(null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(Payment.this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEZ_REQUEST_CODE) {
            // Process based on the data in response.
            Log.d("result", data.getStringExtra("Status"));

            switch (requestCode){

                case UPI_PAYMENT:
                    if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                        if (data != null) {
                            String trxt = data.getStringExtra("response");
                            Log.e("UPI", "onActivityResult: " + trxt);
                            ArrayList<String> dataList = new ArrayList<>();
                            dataList.add(trxt);
                            upiPaymentDataOperation(dataList, "PhonePe");
                        } else {
                            Log.e("UPI", "onActivityResult: " + "Return data is null");
                            ArrayList<String> dataList = new ArrayList<>();
                            dataList.add("nothing");
                            upiPaymentDataOperation(dataList, "PhonePe");
                        }
                    } else {
                        //when user simply back without payment
                        Log.e("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList,"PhonePe");
                    }
                    break;

            }
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data, String mode) {
        if (isConnectionAvailable(Payment.this)) {
            String str = data.get(0);
            Log.e("UPIPAY", "upiPaymentDataOperation: " + str);
            String paymentCancel = "";
            if (str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String[] response = str.split("&");
            for (String s : response) {
                String[] equalStr = s.split("=");
                if (equalStr.length >= 2) {
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    } else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                } else {
                    paymentCancel = "Payment cancelled by user.";
                }
            }
            if (status.equals("success")) {
                //Code to handle successful transaction here.
                Toast.makeText(Payment.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "payment successfull: " + approvalRefNo);
            } else if ("Payment cancelled by user.".equals(paymentCancel)) {
                Toast.makeText(Payment.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "Cancelled by user: " + approvalRefNo);
            } else {
                Toast.makeText(Payment.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
                Log.e("UPI", "failed payment: " + approvalRefNo);
            }
        } else {
            Log.e("UPI", "Internet issue: ");
            Toast.makeText(Payment.this, "Internet connection is not available. Please check and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }


}//END OF MAIN()