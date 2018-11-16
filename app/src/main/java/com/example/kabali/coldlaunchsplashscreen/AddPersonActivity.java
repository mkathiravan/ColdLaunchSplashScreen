package com.example.kabali.coldlaunchsplashscreen;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddPersonActivity extends AppCompatActivity {


    private EditText editTextName, editTextEmail, editTextMobileNo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_person);

        editTextName = findViewById(R.id.editName);
        editTextEmail = findViewById(R.id.editEmail);
        editTextMobileNo = findViewById(R.id.editMobileNo);

        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePersonInfo();
            }
        });
    }

    private void savePersonInfo() {

        final String name = editTextName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String mobileno = editTextMobileNo.getText().toString().trim();

        if (name.isEmpty()) {
            editTextName.setError("Name required");
            editTextName.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Email required");
            editTextEmail.requestFocus();
            return;
        }

        if (mobileno.isEmpty()) {
            editTextMobileNo.setError("MobileNo required");
            editTextMobileNo.requestFocus();
            return;
        }


        class SavePerson extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                Person task = new Person();
                task.setName(name);
                task.setEmail(email);
                task.setMobileNo(mobileno);
                task.setFinished(false);

                //adding to database


                DatabaseClient.getmInstance(getApplicationContext()).getAppDatabase().personDao().insert(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SavePerson st = new SavePerson();
        st.execute();
    }


}

