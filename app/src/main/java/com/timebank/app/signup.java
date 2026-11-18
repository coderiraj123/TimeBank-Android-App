package com.timebank.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class signup extends AppCompatActivity {
    EditText Name,Email,Password,ConfirmPass;
    TextView Already;
    Button btnsignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Name=findViewById(R.id.etName);
        Email=findViewById(R.id.etEmail);
        Password=findViewById(R.id.etPassword);
        ConfirmPass=findViewById(R.id.etConfirmPassword);
        btnsignup=findViewById(R.id.btnSignup);
        Already=findViewById(R.id.tvLogin);
        btnsignup.setOnClickListener(view -> {
            String n=Name.getText().toString().trim();
            String p=Password.getText().toString().trim();
            String c=ConfirmPass.getText().toString().trim();
            String e=Email.getText().toString().trim();
            if(n.isEmpty()){
                Name.setError("FIeld is required!");
            } else if (e.isEmpty()|| !Patterns.EMAIL_ADDRESS.matcher(e).matches()) {
                Email.setError("Email is invalid!");

            } else if (p.isEmpty()||p.length()<8) {
                Password.setError("password must contain 8 character");

            } else if (!p.equals(c)) {
                ConfirmPass.setError("not match");

            }
            else {
                SharedPreferences per=getSharedPreferences("Userdata",MODE_PRIVATE);
                per.edit().putString("name",n)
                        .putString("Email",e)
                        .putString("password", p)
                        .apply();
                Toast.makeText(signup.this,"Let's Go to improve skills",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(signup.this,login.class);
                startActivity(intent);
                finish();


            }

        });
        Already.setOnClickListener(view -> {
            Intent I=new Intent(signup.this, login.class);
            startActivity(I);
        });
    }
}