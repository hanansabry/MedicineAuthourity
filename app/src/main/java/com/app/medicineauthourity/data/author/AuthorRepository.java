package com.app.medicineauthourity.data.author;

import com.app.medicineauthourity.data.model.Author;

import androidx.lifecycle.MutableLiveData;

public interface AuthorRepository {

    void login(String username, String password, MutableLiveData<Boolean> success);
    void register(Author author, MutableLiveData<Boolean> success);
}
