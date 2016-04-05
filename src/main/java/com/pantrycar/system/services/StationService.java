package com.pantrycar.system.services;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.pantrycar.system.core.Station;
import com.pantrycar.system.dao.StationDAO;
import com.pantrycar.system.representations.railway.StationDetails;
import com.pantrycar.system.representations.railway.StationDetailsList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * Created by kunal.agarwal on 12/11/15.
 */
public class StationService {
    private static final Logger logger = LoggerFactory.getLogger(StationService.class);
    private final Provider<StationDAO> daoProvider;

    @Inject
    public StationService(Provider<StationDAO> stationDAOProvider) {
        this.daoProvider = stationDAOProvider;
    }

    public Station getUniqueStation(Map<String, Object> conditions) {
        return daoProvider.get().findUniqueByColumns(conditions);
    }

    public Station getStationByCode(String code) {
        return daoProvider.get().findUniqueByColumn("stationCode", code);
    }

    public List<Station> getStationByCode(List<String> codes) {
        return daoProvider.get().findByColumn("stationCode", codes);
    }

    public StationDetails onBoardNewStation(Station station) {
        if (getStationByCode(station.getStationCode()) != null)
            return new StationDetails().error(400, "Station with the specified code already exists in the system");

        return getStationDetails(daoProvider.get().persist(station));
    }

    public StationDetails getStationDetails(Station station) {
        StationDetails stationDetails = StationDetails.builder()
                .stationFullName(station.getStationFullName())
                .stationCode(station.getStationCode())
                .stationShortName(station.getStationShortName())
                .city(station.getCity())
                .state(station.getState())
                .isActive(station.isActive()).build();
        return stationDetails;
    }

    public StationDetails enableService(String stationCode) {
        Station station = getStationByCode(stationCode);
        if(station == null)
            return new StationDetails().error(400, "Station with the specified code doesnt exists in the system");
        station.setActive(true);
        station = daoProvider.get().persist(station);
        return getStationDetails(station);
    }

    public StationDetails disableService(String stationCode) {
        Station station = getStationByCode(stationCode);
        if(station == null)
            return new StationDetails().error(400, "Station with the specified code doesnt exists in the system");
        station.setActive(false);
        station = daoProvider.get().persist(station);
        return getStationDetails(station);
    }

    public StationDetails getStationDetailsByCode(String stationCode) {
        Station station = getStationByCode(stationCode);
        if(station == null)
            return new StationDetails().error(400, "Station with the specified code doesnt exists in the system");
        return getStationDetails(station);
    }

    public StationDetailsList getAllStations() {
        List<Station> stationList = daoProvider.get().list();
        StationDetailsList.StationDetailsListBuilder listBuilder = StationDetailsList.builder();
        for(Station station : stationList)
            listBuilder.station(getStationDetails(station));
        return listBuilder.build();
    }

    public StationDetailsList getServiceableStations() {
        List<Station> stationList = daoProvider.get().findByColumn("isActive", true);
        StationDetailsList.StationDetailsListBuilder listBuilder = StationDetailsList.builder();
        for(Station station : stationList)
            listBuilder.station(getStationDetails(station));
        return listBuilder.build();
    }
}
