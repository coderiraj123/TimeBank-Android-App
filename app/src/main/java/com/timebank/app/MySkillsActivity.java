
package com.timebank.app;

import android.os.Bundle;
import android.content.SharedPreferences;
import android.app.AlertDialog;
import android.graphics.Color;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.cardview.widget.CardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MySkillsActivity extends AppCompatActivity {

    LinearLayout sc;
    View back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_skills);

        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main), (v, insets) -> {
                    Insets systemBars = insets.getInsets(
                            WindowInsetsCompat.Type.systemBars());

                    v.setPadding(systemBars.left, systemBars.top,
                            systemBars.right, systemBars.bottom);

                    return insets;
                });

        sc = findViewById(R.id.skillsContainer);

        back = findViewById(R.id.backh);
        back.setOnClickListener(v -> finish());

        loadSkills();
    }

    private void loadSkills() {

        sc.removeAllViews();

        SharedPreferences prefs =
                getSharedPreferences("TimeBankPrefs", MODE_PRIVATE);

        String oldData = prefs.getString("skills", "[]");

        try {
            JSONArray skillarray = new JSONArray(oldData);

            if (skillarray.length() == 0) {
                TextView emp = new TextView(this);
                emp.setText("No skills added yet");
                emp.setTextSize(20);
                emp.setPadding(25, 15, 25, 15);
                sc.addView(emp);
                return;
            }

            for (int i = 0; i < skillarray.length(); i++) {

                JSONObject skill = skillarray.getJSONObject(i);

                String name = skill.getString("name");
                String des = skill.getString("description");
                String exper = skill.getString("experience");
                String time = skill.getString("time");

                // Create CardView
                CardView card = getCardView();

                // Card content
                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(20, 20, 20, 20);

                TextView details = new TextView(this);

                details.setText(
                        "Skill: " + name +
                                "\nDescription: " + des +
                                "\nExperience: " + exper +
                                "\nAvailable Time: " + time
                );

                details.setTextSize(17);
                details.setTextColor(Color.rgb(24, 49, 83));

                layout.addView(details);

                // Buttons layout
                LinearLayout buttonLayout = new LinearLayout(this);
                buttonLayout.setOrientation(LinearLayout.HORIZONTAL);

                // Edit button
                Button edit = new Button(this);
                edit.setText("Edit");
                edit.setTextColor(Color.WHITE);
                edit.setBackgroundTintList(
                        android.content.res.ColorStateList.valueOf(
                                Color.rgb(27, 96, 184)));

                // Delete button
                Button delete = new Button(this);
                delete.setText("Delete");
                delete.setTextColor(Color.WHITE);
                delete.setBackgroundTintList(
                        android.content.res.ColorStateList.valueOf(
                                Color.rgb(200, 55, 55)));

                LinearLayout.LayoutParams buttonParams =
                        new LinearLayout.LayoutParams(
                                0,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                1);

                buttonParams.setMargins(5, 10, 5, 0);

                buttonLayout.addView(edit, buttonParams);
                buttonLayout.addView(delete, buttonParams);

                layout.addView(buttonLayout);
                card.addView(layout);

                sc.addView(card);

                final int position = i;

                edit.setOnClickListener(v ->
                        editSkill(position));

                delete.setOnClickListener(v ->
                        deleteSkill(position));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Unable to load skills",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    private CardView getCardView() {
        CardView card = new CardView(this);

        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);

        cardParams.setMargins(0, 10, 0, 10);

        card.setLayoutParams(cardParams);
        card.setRadius(18);
        card.setCardElevation(5);
        card.setUseCompatPadding(true);
        card.setCardBackgroundColor(Color.WHITE);
        return card;
    }

    // Delete skill
    private void deleteSkill(int position) {

        new AlertDialog.Builder(this)
                .setTitle("Delete Skill")
                .setMessage("Are you sure you want to delete this skill?")
                .setPositiveButton("Yes", (dialog, which) -> {

                    try {
                        SharedPreferences prefs =
                                getSharedPreferences(
                                        "TimeBankPrefs", MODE_PRIVATE);

                        JSONArray array = new JSONArray(
                                prefs.getString("skills", "[]"));

                        array.remove(position);

                        prefs.edit()
                                .putString("skills", array.toString())
                                .apply();

                        Toast.makeText(this, "Skill Deleted",
                                Toast.LENGTH_SHORT).show();

                        loadSkills();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    // Edit skill
    private void editSkill(int po) {

        try {
            SharedPreferences prefs =
                    getSharedPreferences("TimeBankPrefs", MODE_PRIVATE);

            JSONArray array = new JSONArray(
                    prefs.getString("skills", "[]"));

            JSONObject skill = array.getJSONObject(po);

            LinearLayout editLayout = new LinearLayout(this);
            editLayout.setOrientation(LinearLayout.VERTICAL);
            editLayout.setPadding(25, 10, 25, 10);

            EditText name = new EditText(this);
            name.setHint("Skill Name");
            name.setText(skill.getString("name"));

            EditText description = new EditText(this);
            description.setHint("Description");
            description.setText(skill.getString("description"));

            EditText experience = new EditText(this);
            experience.setHint("Experience");
            experience.setText(skill.getString("experience"));

            EditText time = new EditText(this);
            time.setHint("Available Time");
            time.setText(skill.getString("time"));

            editLayout.addView(name);
            editLayout.addView(description);
            editLayout.addView(experience);
            editLayout.addView(time);

            new AlertDialog.Builder(this)
                    .setTitle("Edit Skill")
                    .setView(editLayout)
                    .setPositiveButton("Save", (dialog, which) -> {

                        String newName = name.getText().toString().trim();
                        String newDes = description.getText().toString().trim();
                        String newExper = experience.getText().toString().trim();
                        String newTime = time.getText().toString().trim();

                        if (newName.isEmpty() ||
                                newDes.isEmpty() ||
                                newExper.isEmpty() ||
                                newTime.isEmpty()) {

                            Toast.makeText(this,
                                    "Please fill all fields",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }

                        try {
                            skill.put("name", newName);
                            skill.put("description", newDes);
                            skill.put("experience", newExper);
                            skill.put("time", newTime);

                            array.put(po, skill);

                            prefs.edit()
                                    .putString("skills", array.toString())
                                    .apply();

                            Toast.makeText(this,
                                    "Skill Updated Successfully",
                                    Toast.LENGTH_SHORT).show();

                            loadSkills();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
