package com.example.dawid.myapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {


    EditText editTextName;
    EditText editTextMail;
    EditText editTextPhone;
    Button btnAddContact;
    TabHost tabHost;
    TabHost.TabSpec tabSpec;

    ArrayList<Contact> Contacts = new ArrayList<Contact>();
    RecyclerView contactView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitVar();

    }

    public void InitVar()
    {

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextMail = (EditText) findViewById(R.id.editTextMail);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        btnAddContact = (Button) findViewById(R.id.buttonAddContact);
        contactView = (RecyclerView) findViewById(R.id.contactList);

        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        tabSpec = tabHost.newTabSpec("AddContact");
        tabSpec.setContent(R.id.tabAddContact);
        tabSpec.setIndicator("Add Contact");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("ContactList");
        tabSpec.setContent(R.id.tabList);
        tabSpec.setIndicator("Contact List");
        tabHost.addTab(tabSpec);

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        contactView.setLayoutManager(llm);

        llm.setOrientation(LinearLayoutManager.VERTICAL);

        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextName.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Fill the 'Name' field first !", Toast.LENGTH_SHORT).show();
                }
                else {
                    addContact(editTextName.getText().toString(),
                            editTextPhone.getText().toString(),
                            editTextMail.getText().toString());

                    Toast.makeText(getApplicationContext(), editTextName.getText().toString() + " has been added to your Contact List !", Toast.LENGTH_SHORT).show();

                    ContactAdapter contactAdapter = new ContactAdapter(Contacts);
                    contactView.setAdapter(contactAdapter);

                    editTextName.setText("");
                    editTextMail.setText("");
                    editTextPhone.setText("");

                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public void addContact(String name, String phone, String email)
    {
        Contacts.add(new Contact(name, phone, email));
    }

}
