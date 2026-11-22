package com.timebank.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {
    Button btns,btnw,btnr,btnsearchskills;
    TextView HN;
    EditText searchs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btns=findViewById(R.id.btnaddskills);
        btnw=findViewById(R.id.btnwallet);
        btnr=findViewById(R.id.btnrequest);
        btnsearchskills=findViewById(R.id.btnSearchSkill);
        searchs=findViewById(R.id.etSearchSkill);
        btns.setOnClickListener(view -> {
            Toast.makeText(this,"Add Skills in Data",Toast.LENGTH_LONG).show();
        });
        btnw.setOnClickListener(view -> {
            Toast.makeText(this,"Time Wallet",Toast.LENGTH_LONG).show();
        });
        btnr.setOnClickListener(view -> {
            Toast.makeText(this,"My Requests",Toast.LENGTH_LONG).show();
        });
        btnsearchskills.setOnClickListener(view -> {
            String skills=searchs.getText().toString().trim();
            if(skills.isEmpty()){
                btnsearchskills.setError("bhai skills to da ");
            }
            else {
                Toast.makeText(this,
                        "Searching for: " + skills,
                        Toast.LENGTH_LONG).show();
            }
            Toast.makeText(this,"Search SkillsSearch Skills",Toast.LENGTH_LONG).show();
        });
        HN=findViewById(R.id.HomeName);
        SharedPreferences prefer=getSharedPreferences("Userdata",MODE_PRIVATE);
        String Name= prefer.getString("name","student");
        HN.setText("Hello "+ Name+ " !");

    }
}