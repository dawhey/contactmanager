package com.example.dawid.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
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
    private Context mContext;

    public ContactAdapter(Context context, List<Contact> c)
    {
        mContext = context;
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

            listItemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    PopupMenu popup = new PopupMenu(v.getContext(), v);
                    popup.inflate(R.menu.popup_menu);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getItemId() == R.id.delete)
                                deleteView(getPosition());
                            else if (item.getItemId() == R.id.call)
                                call(getPosition());

                            return false;
                        }
                    });
                    popup.show();
                    return false;
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

    public void call(int i) {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + MainActivity.Contacts.get(i).getPhonenumber()));
        mContext.startActivity(callIntent);
    }
}
