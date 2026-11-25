package com.timebank.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class MySkillsActivity extends AppCompatActivity {
    LinearLayout sc;
    View back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_skills);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        sc=findViewById(R.id.skillsContainer);
        back=findViewById(R.id.backh);
        back.setOnClickListener(v -> {
            finish();
        });
        loadSkills();

    }
    private void loadSkills(){
        SharedPreferences prefers=getSharedPreferences("TimeBankPrefs",MODE_PRIVATE);
        String oldData=prefers.getString("skills","[]");
        try {
            JSONArray skillarray=new JSONArray(oldData);
            if (skillarray.length()==0){
                TextView emp=new TextView(this);
                emp.setText("No skills added yet");
                emp.setTextSize(20);
                emp.setPadding(25,15,25,15);
                sc.addView(emp);
                return;
            }
            for (int i=0;i<skillarray.length();i++){
                JSONObject Skill =skillarray.getJSONObject((i));
                String name=Skill.getString("name");
                String des=Skill.getString("description");
                String exper=Skill.getString("experience");
                String T=Skill.getString("time");
                TextView sktxt=new TextView(this);
                sktxt.setText(
                        "Skill: " + name +
                                "\nDescription: " + des +
                                "\nExperience: " + exper +
                                "\nAvailable Time: " + T
                );
                sktxt.setTextSize(17);
                sktxt.setTextColor(0xFF183153);
                sktxt.setPadding(20,20,20,20);
                sc.addView(sktxt);





            }
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }
}