package com.pantrycar.system.services;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.pantrycar.system.core.Password;
import com.pantrycar.system.dao.PasswordDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by kunal.agarwal on 15/11/15.
 */
public class PasswordService {
    private static final Logger logger = LoggerFactory.getLogger(PasswordService.class);
    private final Provider<PasswordDAO> daoProvider;

    @Inject
    public PasswordService(Provider<PasswordDAO> daoProvider) {
        this.daoProvider = daoProvider;
    }

    void resetPassword(Password password, String newPass) {
        password.setPassword(newPass);
        daoProvider.get().persist(password);
    }
}
