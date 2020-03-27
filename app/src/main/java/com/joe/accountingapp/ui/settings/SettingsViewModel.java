package com.joe.accountingapp.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.joe.accountingapp.bean.MoreFunction;

import java.util.ArrayList;

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<ArrayList<String>> mFuncName;

    public SettingsViewModel() {
        mText = new MutableLiveData<>();
        mFuncName = new MutableLiveData<>();

        mText.setValue("This is notifications fragment");

//        MoreFunction moreFunction = new MoreFunction("個人設定");
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("吃飯");
        arrayList.add("睡覺");
        arrayList.add("打東東");
        mFuncName.setValue(arrayList);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<ArrayList<String>> getFuncName() {
        return mFuncName;
    }
}