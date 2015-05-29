package com.example.dawid.myapplication;

/**
 * Created by Dawid on 2015-05-03.
 */
public class Contact {

    private String _name, _email, _phonenumber;
    private int _id;
    private boolean _selected = false;

    public Contact(int id, String name, String phonenumber, String email) {
        _id = id;
        _name = name;
        _phonenumber = phonenumber;
        _email = email;

    }

    public String getName() {
        return _name;
    }

    public String getPhonenumber() {
        return _phonenumber;
    }

    public String getEmail() {
        return _email;
    }

    public int getId() {
        return _id;
    }

    public boolean isSelected() {
        return _selected;
    }

    public void setSelected(boolean mode)
    {
        _selected = mode;
    }
}
