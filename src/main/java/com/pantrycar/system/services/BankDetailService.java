package com.pantrycar.system.services;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.pantrycar.system.core.BankDetail;
import com.pantrycar.system.dao.BankDetailDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kunal.agarwal on 13/11/15.
 */
public class BankDetailService {
    private static final Logger logger = LoggerFactory.getLogger(BankDetailService.class);
    private final Provider<BankDetailDAO> daoProvider;
    private final Provider<LocationService> locationServiceProvider;

    @Inject
    public BankDetailService(Provider<BankDetailDAO> bankDetailDAOProvider, Provider<LocationService> locationServiceProvider) {
        this.daoProvider = bankDetailDAOProvider;
        this.locationServiceProvider = locationServiceProvider;
    }

    private BankDetail BankDetail(Map<String, Object> conditions) {
        return daoProvider.get().findUniqueByColumns(conditions);
    }

    public BankDetail findOrCreate(BankDetail bankDetail) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("bankName", bankDetail.getBankName());
        conditions.put("bankAccountName", bankDetail.getBankAccountName());
        conditions.put("bankAccountNumber", bankDetail.getBankAccountNumber());
        conditions.put("restaurant", bankDetail.getRestaurant());
        BankDetail uniqueBankDetail = BankDetail(conditions);
        if (uniqueBankDetail == null) {
            bankDetail.setLocation(locationServiceProvider.get().findOrCreate(bankDetail.getLocation()));
            return daoProvider.get().persist(bankDetail);
        }
        return uniqueBankDetail;
    }
}
