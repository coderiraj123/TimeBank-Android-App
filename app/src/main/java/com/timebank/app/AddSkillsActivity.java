
package com.timebank.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class AddSkillsActivity extends AppCompatActivity {

    EditText SkillName, SkillDescription, AvailableTime;
    Spinner Experience;
    Button btnAddSkill;
View back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_skills);

        // Connect XML views
        SkillName = findViewById(R.id.etSkillName);
        SkillDescription = findViewById(R.id.etSkillDescription);
        AvailableTime = findViewById(R.id.etAvailableTime);
        Experience = findViewById(R.id.spExperience);
        btnAddSkill = findViewById(R.id.btnAddSkill);

        // Spinner options
        String[] levels = {
                "Select Experience level",
                "Beginner",
                "Intermediate",
                "Expert"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                levels
        );

        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );

        Experience.setAdapter(adapter);

        btnAddSkill.setOnClickListener(v -> saveSkill());
    }

    private void saveSkill() {

        String name = SkillName.getText().toString().trim();
        String description = SkillDescription.getText().toString().trim();
        String time = AvailableTime.getText().toString().trim();
        String experience = Experience.getSelectedItem().toString();

        if (name.isEmpty()) {
            SkillName.setError("Enter skill name");
            return;
        }

        if (description.isEmpty()) {
            SkillDescription.setError("Enter description");
            return;
        }

        if (experience.equals("Select Experience")) {
            Toast.makeText(this, "Select experience level",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (time.isEmpty()) {
            AvailableTime.setError("Enter available time");
            return;
        }

        try {
            android.content.SharedPreferences prefs =
                    getSharedPreferences("TimeBankPrefs", MODE_PRIVATE);

            String oldData = prefs.getString("skills", "[]");

            JSONArray skillsArray = new JSONArray(oldData);

            JSONObject skill = new JSONObject();
            skill.put("name", name);
            skill.put("description", description);
            skill.put("experience", experience);
            skill.put("time", time);

            skillsArray.put(skill);

            prefs.edit()
                    .putString("skills", skillsArray.toString())
                    .apply();

            Toast.makeText(this, "Skill Added Successfully!",
                    Toast.LENGTH_LONG).show();

            SkillName.setText("");
            SkillDescription.setText("");
            AvailableTime.setText("");
            Experience.setSelection(0);

        } catch (Exception e) {
            Toast.makeText(this, "Unable to save skill",
                    Toast.LENGTH_SHORT).show();
        }
        back=findViewById(R.id.backh);
        back.setOnClickListener(view -> {
            Intent i=new Intent(this, HomeActivity.class);
            startActivity(i);
        });
    }
}
