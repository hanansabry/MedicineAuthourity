package com.app.medicineauthourity;

import com.app.medicineauthourity.data.author.AuthorRepository;
import com.app.medicineauthourity.domain.author.AuthorRepositoryImpl;
import com.app.medicineauthourity.domain.author.usecases.LoginUseCase;
import com.app.medicineauthourity.domain.author.usecases.RegisterUseCase;

public class Injection {
    public static LoginUseCase getLoginUseCase() {
        return new LoginUseCase(getAuthorRepository());
    }

    private static AuthorRepository getAuthorRepository() {
        return new AuthorRepositoryImpl();
    }

    public static RegisterUseCase getRegisterUseCase() {
        return new RegisterUseCase(getAuthorRepository());
    }
}
