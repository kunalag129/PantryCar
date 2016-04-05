package com.pantrycar.system.services;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.pantrycar.system.core.TaxDetail;
import com.pantrycar.system.dao.TaxDetailDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by kunal.agarwal on 13/11/15.
 */
public class TaxDetailService {
    private static final Logger logger = LoggerFactory.getLogger(TaxDetailService.class);
    private final Provider<TaxDetailDAO> daoProvider;

    @Inject
    public TaxDetailService(Provider<TaxDetailDAO> daoProvider) {
        this.daoProvider = daoProvider;
    }

    private TaxDetail getUniqueTaxDetail(Map<String, Object> conditions) {
        return daoProvider.get().findUniqueByColumns(conditions);
    }

    public TaxDetail findOrCreate(TaxDetail taxDetail) {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("serviceTaxNumber", taxDetail.getServiceTaxNumber());
        conditions.put("vatNo", taxDetail.getVatNo());
        conditions.put("restaurant", taxDetail.getRestaurant());
        TaxDetail uniqueTaxDetail = getUniqueTaxDetail(conditions);
        if (uniqueTaxDetail == null)
            return daoProvider.get().persist(taxDetail);
        return uniqueTaxDetail;
    }
}
