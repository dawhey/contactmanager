package com.example.dawid.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    public static int itemselected;
    public static MenuItem delete, call;

    EditText editTextName;
    EditText editTextMail;
    EditText editTextPhone;
    Button btnAddContact;
    TabHost tabHost;
    TabHost.TabSpec tabSpec;
    public DatabaseHandler dbHandler;
    ContactAdapter contactAdapter;

    List<Contact> Contacts = new ArrayList<Contact>();
    RecyclerView contactView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InitVar();

    }

    public void InitVar() {
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

        dbHandler = new DatabaseHandler(getApplicationContext());

        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        contactView.setLayoutManager(llm);

        llm.setOrientation(LinearLayoutManager.VERTICAL);

        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editTextName.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Fill the 'Name' field first.", Toast.LENGTH_SHORT).show();
                } else {
                    Contact c = new Contact(dbHandler.getContactsCount(), editTextName.getText().toString(), editTextPhone.getText().toString(), editTextMail.getText().toString());

                    if (!contactExists(c)) {
                        dbHandler.createContact(c);
                        Contacts.add(c);
                        Toast.makeText(getApplicationContext(), editTextName.getText().toString() + " has been added to your Contacts.", Toast.LENGTH_SHORT).show();
                        editTextName.setText("");
                        editTextMail.setText("");
                        editTextPhone.setText("");
                        return;

                    } else
                        Toast.makeText(getApplicationContext(), "Contact already exists!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (dbHandler.getContactsCount() != 0)
            Contacts.addAll(dbHandler.getAllContacts());

        populateList();

    }

    public boolean contactExists(Contact c) {
        String name = c.getName();
        int contactCount = Contacts.size();

        for (int i = 0; i < contactCount; i++) {
            if (name.compareToIgnoreCase(Contacts.get(i).getName()) == 0)
                return true;
        }
        return false;
    }

    public void populateList() {
        contactAdapter = new ContactAdapter(Contacts);
        contactView.setAdapter(contactAdapter);
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        delete = menu.findItem(R.id.remove);
        call = menu.findItem(R.id.call);
        delete.setVisible(false);
        call.setVisible(false);

        return super.onPrepareOptionsMenu(menu);
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
        else if (id == R.id.remove)
        {
            contactAdapter.deleteView(itemselected);
            Toast.makeText(getApplicationContext(), "Contact deleted!", Toast.LENGTH_SHORT).show();
            delete.setVisible(false);
            call.setVisible(false);
        }

        return super.onOptionsItemSelected(item);
    }

}
