package com.raTags.mobileapplication;

public interface ActivityCallback {
    void onEditTextChange(String _id, String _spot);
    String getID();
    String getSpot();
}