package com.example.navigationdrawer.ui.myalarms;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyalarmsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyalarmsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("");
    }

    public LiveData<String> getText() {
        return mText;
    }
}