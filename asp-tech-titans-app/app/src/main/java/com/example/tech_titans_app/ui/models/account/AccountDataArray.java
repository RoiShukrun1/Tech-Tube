package com.example.tech_titans_app.ui.models.account;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

public class AccountDataArray extends Application {
    private static AccountDataArray instance;
    private List<AccountData> accountDataArray;

    private AccountDataArray() {
        accountDataArray = new ArrayList<>();
    }
    public static synchronized AccountDataArray getInstance() {
        if (instance == null) {
            instance = new AccountDataArray();
        }
        return instance;
    }
    public List<AccountData> getAccountArray() {
        return accountDataArray;
    }

    public void addAccount(AccountData accountData) {
        accountDataArray.add(accountData);
    }

    public int getLength() {
        return accountDataArray.size();
    }
}
