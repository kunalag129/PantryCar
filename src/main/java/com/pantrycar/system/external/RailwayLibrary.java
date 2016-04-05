package com.pantrycar.system.external;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.pantrycar.system.representations.railway.PnrDetailsLibResponse;
import com.pantrycar.system.representations.railway.TrainDetailsList;
import com.pantrycar.system.representations.railway.TrainRouteLibResponse;
import com.pantrycar.system.utils.Utils;

import java.io.IOException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kunal.agarwal on 19/11/15.
 */
public class RailwayLibrary {
    private static RailwayLibrary instance = null;
    final static private String[] getPnrParams = new String[]{"pnr", "format", "pbapikey", "pbapisign"};
    final static private String[] getTrainRouteParams = new String[]{"train", "format", "pbapikey", "pbapisign"};
    final static private String[] getTrainBetweenLocationsParams = new String[]{"fscode", "tscode", "date", "format", "pbapikey", "pbapisign"};
    final static private String erailApiKey = "cca37432-1450-46f8-99f8-b00ff66f09e8";
    final static private String railwayApiKey = "66385";
    final static private String railPnrApiPublicKey = "059da6313f8a4f63fc3b3edfd6fe38c8";
    final static private String railPnrApiPrivateKey = "9d41fbb42d4486f6680b9d5b459b7cb1";
    final static private String railPnrApiUrl = "http://railpnrapi.com/api/";
    final static private ObjectMapper MAPPER = new ObjectMapper();

    private RailwayLibrary() {
        MAPPER.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
    }

    public static RailwayLibrary get() {
        if (instance == null)
            instance = new RailwayLibrary();
        return instance;
    }

    public PnrDetailsLibResponse getPnrDetails(String pnr) {
        Map<String, String> details = new HashMap<>();
        details.put("pnr", pnr);
        details = modifyAndGetHmac(details);
        String url = railPnrApiUrl + "check_pnr" + getUrlString(getPnrParams, details);
        int retryCount = 0;

        HttpResponse<String> response;
        try {
            response = Unirest.get(url).asString();
            while (response.getStatus() != 200 && retryCount < 5) {
                response = Unirest.get(url).asString();
                retryCount++;
            }
            return MAPPER.readValue(response.getBody(), PnrDetailsLibResponse.class);
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public TrainRouteLibResponse getTrainRouteDetails(String trainNo) {
        Map<String, String> details = new HashMap<>();
        details.put("train", trainNo);
        details = modifyAndGetHmac(details);
        String url = railPnrApiUrl + "route" + getUrlString(getTrainRouteParams, details);
        try {
            HttpResponse<String> response = Unirest.get(url).asString();
            return MAPPER.readValue(response.getBody(), TrainRouteLibResponse.class);
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Map<String, String> modifyAndGetHmac(Map<String, String> details) {
        details.put("format", "json");
        details.put("pbapikey", railPnrApiPublicKey);
        try {
            details.put("pbapisign", Utils.getHmacSignature(details, railPnrApiPrivateKey));
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return details;
    }

    private String getUrlString(String[] getParams, Map<String, String> details) {
        String paramsUrl = "";
        for (int i = 0; i < getParams.length; i++) {
            paramsUrl += "/" + getParams[i] + "/" + details.get(getParams[i]);
        }
        return paramsUrl;
    }

    public TrainDetailsList getTrainsBetweenLocations(String source, String destination, String date) {
        Map<String, String> details = new HashMap<>();
        details.put("fscode", source);
        details.put("tscode", destination);
        details.put("date", date);
        details = modifyAndGetHmac(details);
        String url = railPnrApiUrl + "trains_between_stations" + getUrlString(getTrainBetweenLocationsParams, details);
        try {
            HttpResponse<String> response = Unirest.get(url).asString();
            return MAPPER.readValue(response.getBody(), TrainDetailsList.class);
        } catch (UnirestException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
