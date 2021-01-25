package com.app.medicineauthourity.domain.author;

import com.app.medicineauthourity.data.author.AuthorRepository;
import com.app.medicineauthourity.data.model.Author;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class AuthorRepositoryImpl implements AuthorRepository {

    private final FirebaseAuth auth;

    public AuthorRepositoryImpl() {
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void login(String email, String password, MutableLiveData<Boolean> success) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        success.setValue(task.isSuccessful());
                    }
                });
    }

    @Override
    public void register(Author author, MutableLiveData<Boolean> success) {
        auth.createUserWithEmailAndPassword(author.getEmail(), author.getPassword())
                .addOnCompleteListener(task -> {
                    success.setValue(task.isSuccessful());
                });
    }
}
