package com.app.medicineauthourity.domain.author.usecases;

import com.app.medicineauthourity.data.author.AuthorRepository;

import androidx.lifecycle.MutableLiveData;

public class LoginUseCase {

    private AuthorRepository authorRepository;

    public LoginUseCase(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void execute(String username, String password, MutableLiveData<Boolean> success) {
        authorRepository.login(username, password, success);
    }
}
