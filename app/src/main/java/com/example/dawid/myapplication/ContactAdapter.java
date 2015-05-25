package com.example.dawid.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Dawid on 2015-05-24.
 */
public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder>
{
    private List<Contact> contactList;

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
        DatabaseHandler dbHandler;

        public ContactHolder(final View listItemView)
        {
            super(listItemView);

            holderEmail = (TextView) listItemView.findViewById(R.id.email);
            holderName = (TextView) listItemView.findViewById(R.id.name);
            holderPhone = (TextView) listItemView.findViewById(R.id.phonenumber);
            dbHandler = new DatabaseHandler(listItemView.getContext());

            listItemView.setOnLongClickListener(new View.OnLongClickListener() {


                @Override
                public boolean onLongClick(View v)
                {
                    Toast.makeText(listItemView.getContext(), holderName.getText().toString() + " deleted",Toast.LENGTH_SHORT).show();
                    deleteView(getPosition());
                    return false;
                }
            });
        }

        public void deleteView(int position)
        {
            contactList.remove(position);
            notifyItemRemoved(position);
            dbHandler.deleteContact(position);
        }

    }
}
