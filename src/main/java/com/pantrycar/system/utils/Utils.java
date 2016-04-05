package com.pantrycar.system.utils;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.security.SignatureException;
import java.text.Normalizer;
import java.util.*;

/**
 * Created by kunal.agarwal on 31/03/15.
 */
public class Utils {

    public static String getHmacSignature(Map<String, String> params, String key) throws SignatureException {

        return HMACSignature.getSignature(getDataString(params), key);
    }

    private static String getDataString(Map<String, String> params) {
        SortedSet<String> set = new TreeSet<String>();
        set.addAll(params.keySet());
        String dataString = "";

        for (Iterator it = set.iterator(); it.hasNext(); ) {
            dataString += params.get(it.next()).toLowerCase();
        }
        return dataString;
    }

    public static String toPrettyURL(String string) {
        return Normalizer.normalize(string.toLowerCase(), Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .replaceAll("[^\\p{Alnum}]+", "-");
    }

    public static Criterion getCriteriaForDateRanges(String propertyName, Date from, Date to) {
        if (from != null && to != null)
            return Restrictions.between(propertyName, from, to);
        if (from != null && to == null)
            return Restrictions.ge(propertyName, from);
        if (from == null && to != null)
            return Restrictions.le(propertyName, to);
        return null;
    }
}
