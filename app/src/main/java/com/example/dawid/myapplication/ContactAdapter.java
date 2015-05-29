package com.example.dawid.myapplication;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dawid on 2015-05-24.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder>
{
    public static List<Contact> contactList;
    DatabaseHandler dbHandler;
    CardView cardclicked;

    public ContactAdapter(List<Contact> c)
    {
        contactList = c;
    }

    @Override
    public ContactHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_element, viewGroup, false);
        ContactHolder contactHolder = new ContactHolder(v);
        return contactHolder;
    }

    @Override
    public int getItemCount()
    {
        return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactHolder contactHolder, int i)
    {
        contactHolder.holderName.setText(contactList.get(i).getName());
        contactHolder.holderPhone.setText(contactList.get(i).getPhonenumber());
        contactHolder.holderEmail.setText(contactList.get(i).getEmail());
    }


    public class ContactHolder extends RecyclerView.ViewHolder
    {
        protected TextView holderName, holderEmail, holderPhone;

        public ContactHolder(final View listItemView)
        {
            super(listItemView);
            listItemView.setClickable(true);

            holderEmail = (TextView) listItemView.findViewById(R.id.email);
            holderName = (TextView) listItemView.findViewById(R.id.name);
            holderPhone = (TextView) listItemView.findViewById(R.id.phonenumber);
            dbHandler = new DatabaseHandler(listItemView.getContext());

            listItemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    cardclicked = (CardView) v;
                    Contact c = MainActivity.Contacts.get(getPosition());

                    if (c.isSelected()) {
                        c.setSelected(false);
                        cardclicked.setCardBackgroundColor(Color.parseColor("#2196F3"));
                        MainActivity.SelectedItems.remove(c.getId());
                    } else {
                        c.setSelected(true);
                        cardclicked.setCardBackgroundColor(Color.parseColor("#42A5F5"));
                        MainActivity.SelectedItems.add(c.getId());
                    }

                    unselectAll();
                }
            });
        }
    }
    public void deleteView(int position)
    {
        contactList.remove(position);
        if (position == 0)
            dbHandler.deleteFirst();
        else
            dbHandler.deleteContact(position);

        notifyItemRemoved(position);
    }

    public void unselectAll()
    {
        for (int i = 0; i < MainActivity.Contacts.size(); i++)
        {
            MainActivity.Contacts.get(i).setSelected(false);
        }
    }
}
