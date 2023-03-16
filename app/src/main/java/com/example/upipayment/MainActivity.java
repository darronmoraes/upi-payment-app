package com.example.upipayment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText description, amount;
    Button pay;

    // UPI variables
    private static final int TEZ_REQUEST_CODE = 123;
    private static final String GOOGLE_TEZ_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        description = findViewById(R.id.et_description);
        amount = findViewById(R.id.et_amount);
        pay = findViewById(R.id.btn_pay);

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paymentStart();
            }
        });
    }

    private void paymentStart() {
        Uri uri = new Uri.Builder()
                .scheme("upi")
                .authority("pay")
                .appendQueryParameter("pa", "darrontaciomoraes14@oksbi")  // merchant
                .appendQueryParameter("pn", "Smart Transportation")
                .appendQueryParameter("tr", String.valueOf(System.currentTimeMillis()))
                .appendQueryParameter("tn", description.getText().toString())
                .appendQueryParameter("am", amount.getText().toString())
                .build();

        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri);
        intent.setPackage(GOOGLE_TEZ_PACKAGE_NAME);
        startActivityForResult(intent, TEZ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEZ_REQUEST_CODE) {
            Log.d("result", data.getStringExtra("Status"));
        }
    }
}