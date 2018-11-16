package com.example.kabali.coldlaunchsplashscreen;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class UpdatePersonActivity extends AppCompatActivity {


    private EditText editTextName, editTextEmail,editTextMobileNo;
    private CheckBox checkBoxFinished;
    private Button buttonUpdate;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_person);

        editTextName = (EditText)findViewById(R.id.updateeditTextName);
        editTextEmail = (EditText)findViewById(R.id.updateeditTextEmail);
        editTextMobileNo = (EditText)findViewById(R.id.updateeditTextPhone);

        checkBoxFinished = (CheckBox) findViewById(R.id.checkBoxFinished);

        buttonUpdate = (Button)findViewById(R.id.button_update);


        final Person person = (Person)getIntent().getSerializableExtra("person");

        loadPerson(person);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                updatePerson(person);

            }
        });



        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdatePersonActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteTask(person);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }
        });
    }


    private void loadPerson(Person person) {

        editTextName.setText(person.getName());
        editTextEmail.setText(person.getEmail());
        editTextMobileNo.setText(person.getMobileNo());
        checkBoxFinished.setChecked(person.isFinished());
    }

    private void updatePerson(final Person person) {

        final String name = editTextName.getText().toString();
        final String email = editTextEmail.getText().toString();
        final String phoneNo = editTextMobileNo.getText().toString();


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

        if (phoneNo.isEmpty()) {
            editTextMobileNo.setError("Phone required");
            editTextMobileNo.requestFocus();
            return;
        }

        class UpdatePerson extends AsyncTask<Void,Void,Void>
        {

            @Override
            protected Void doInBackground(Void... voids) {
                person.setName(name);
                person.setEmail(email);
                person.setMobileNo(phoneNo);
                person.setFinished(checkBoxFinished.isChecked());

                DatabaseClient.getmInstance(getApplicationContext()).getAppDatabase()
                        .personDao()
                        .update(person);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdatePersonActivity.this, MainActivity.class));
            }



        }


        UpdatePerson ut = new UpdatePerson();
        ut.execute();




    }



    private void deleteTask(final Person task) {
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getmInstance(getApplicationContext()).getAppDatabase()
                        .personDao()
                        .delete(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdatePersonActivity.this, MainActivity.class));
            }
        }

        DeleteTask dt = new DeleteTask();
        dt.execute();

    }


}
