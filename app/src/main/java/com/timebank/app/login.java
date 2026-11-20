package com.timebank.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.service.autofill.UserData;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class login extends AppCompatActivity {
    Button btnlogin;
    EditText email,password;
    TextView txtsignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        email=findViewById(R.id.loginemail);
        password=findViewById(R.id.etpass);
        btnlogin=findViewById(R.id.btnLogin);
        txtsignup=findViewById(R.id.tvSignup);
        btnlogin.setOnClickListener(view -> {
            String E=email.getText().toString().trim();
            String p=password.getText().toString().trim();
            if (E.isEmpty()) {
                email.setError("Email is required");
            }
            else if (p.isEmpty()) {
                password.setError("Password is required");
            }
            else {
                SharedPreferences preferences=getSharedPreferences("Userdata",MODE_PRIVATE);
                String savedemail=preferences.getString("Email","");
                String savedpassword=preferences.getString("password","");
                if(E.equals(savedemail)&&p.equals(savedpassword)){
                    Toast.makeText(login.this,
                            "Login Successful!",
                            Toast.LENGTH_SHORT).show();

                }
                else {
                    Toast.makeText(login.this,
                            "Invaild Data",
                            Toast.LENGTH_SHORT).show();

                }
            }
        });
        txtsignup.setOnClickListener(view -> {
            Intent back=new Intent(this,signup.class);
            startActivity(back);
        });

    }


}