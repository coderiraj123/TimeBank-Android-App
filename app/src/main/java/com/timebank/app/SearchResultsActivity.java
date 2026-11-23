package com.timebank.app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SearchResultsActivity extends AppCompatActivity {
    Button btnsrequest;
    TextView sname,p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search_results);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sname=findViewById(R.id.skillname);
        String S=getIntent().getStringExtra("Skill_name");
        sname.setText("Student who can teach "+ S);
        p=findViewById(R.id.sk);
        String P=getIntent().getStringExtra("Skill_name");
        sname.setText("Skill: "+ P+" Programming");
        btnsrequest=findViewById(R.id.btnsr);
        btnsrequest.setOnClickListener(view -> {
            Toast.makeText(this,"Request send successfully!",Toast.LENGTH_LONG).show();
        });
    }
}