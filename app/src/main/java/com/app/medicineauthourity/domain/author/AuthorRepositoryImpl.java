package com.app.medicineauthourity.domain.author;

import com.app.medicineauthourity.data.author.AuthorRepository;
import com.app.medicineauthourity.data.model.Author;

import androidx.lifecycle.MutableLiveData;

public class AuthorRepositoryImpl implements AuthorRepository {
    @Override
    public void login(String username, String password, MutableLiveData<Boolean> success) {
        if (username.equals("hanan") && password.equals("123")) {
            success.setValue(true);
        } else {
            success.setValue(false);
        }
    }

    @Override
    public void register(Author author, MutableLiveData<Boolean> success) {
        if (author.getUserName().equals("hanan")) {
            success.setValue(true);
        } else {
            success.setValue(false);
        }
    }
}
