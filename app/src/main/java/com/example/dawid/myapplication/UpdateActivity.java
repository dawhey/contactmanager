package com.example.dawid.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class UpdateActivity extends ActionBarActivity {

    private String editName;
    private String editPhone;
    private String editEmail;
    private int editId;

    EditText editTextName;
    EditText editTextMail;
    EditText editTextPhone;
    Button btnUpdateContact;
    DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        dbHandler = new DatabaseHandler(getApplicationContext());

        final Bundle b = getIntent().getExtras();
        editName = b.getString("name");
        editPhone = b.getString("phone");
        editEmail = b.getString("email");
        editId = b.getInt("id");

        editTextName = (EditText) findViewById(R.id.TextName);
        editTextMail = (EditText) findViewById(R.id.TextMail);
        editTextPhone = (EditText) findViewById(R.id.TextPhone);
        btnUpdateContact = (Button) findViewById(R.id.buttonUpdateContact);

        editTextName.setText(editName);
        editTextPhone.setText(editPhone);
        editTextMail.setText(editEmail);
        btnUpdateContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact c = new Contact(editId, editTextName.getText().toString(), editTextPhone.getText().toString(), editTextMail.getText().toString());
                dbHandler.updateContact(c, editId);
                Toast.makeText(getApplicationContext(), "Contact updated.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(UpdateActivity.this, MainActivity.class);
                MainActivity.Contacts.set(b.getInt("pos"), c);
                //startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
