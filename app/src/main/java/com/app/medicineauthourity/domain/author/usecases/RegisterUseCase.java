package com.app.medicineauthourity.domain.author.usecases;

import com.app.medicineauthourity.data.author.AuthorRepository;
import com.app.medicineauthourity.data.model.Author;

import androidx.lifecycle.MutableLiveData;

public class RegisterUseCase {

    private AuthorRepository authorRepository;

    public RegisterUseCase(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void execute(Author author, MutableLiveData<Boolean> success) {
        authorRepository.register(author, success);
    }
}
