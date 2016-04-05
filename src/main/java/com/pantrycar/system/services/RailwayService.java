package com.pantrycar.system.services;

import com.pantrycar.system.external.RailwayLibrary;
import com.pantrycar.system.representations.railway.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kunal.agarwal on 19/11/15.
 */
public class RailwayService {
    private static final Logger logger = LoggerFactory.getLogger(RailwayService.class);

    public PnrAndStationDetails getPnrDetails(String pnr) {
        PnrDetailsLibResponse pnrDetails = RailwayLibrary.get().getPnrDetails(pnr);
        TrainRouteLibResponse trainRouteDetails = RailwayLibrary.get()
                .getTrainRouteDetails(pnrDetails.getTrainNum()).sanitize();
        List<TrainStoppage> stops = getStationBetweenSourceAndDestinaton(trainRouteDetails, pnrDetails.getFromStation().getCode().trim(), pnrDetails.getToStation().getCode().trim());
        return buildPnrDetailsResponse(pnrDetails, stops);
    }

    public TrainDetailsList getTrainsBetweenLocations(String source, String destination, String date) {
        TrainDetailsList detailsList = RailwayLibrary.get().getTrainsBetweenLocations(source, destination, date);
        detailsList.sanitize().setDate(date);
        return detailsList;
    }

    private PnrAndStationDetails buildPnrDetailsResponse(PnrDetailsLibResponse pnrDetails, List<TrainStoppage> stops) {
        PnrAndStationDetails response = PnrAndStationDetails.builder().pnr(pnrDetails.getPnr())
                .trainNum(pnrDetails.getTrainNum())
                .trainName(pnrDetails.getTrainName())
                .srcStationCode(pnrDetails.getFromStation().getCode())
                .srcStationName(pnrDetails.getFromStation().getName())
                .destStationCode(pnrDetails.getToStation().getCode())
                .destStationName(pnrDetails.getToStation().getName())
                .date(pnrDetails.getDoj())
                .trainStoppages(stops)
                .boardingClass(pnrDetails.getBoardingClass())
                .chartedPrepared(pnrDetails.getChartPrepared())
                .passengers(pnrDetails.getPassengers()).build();
        return response;
    }

    private List<TrainStoppage> getStationBetweenSourceAndDestinaton(TrainRouteLibResponse routeDetails, String source, String destination) {
        return stationsBetweenAandB(routeDetails.getTrain().getRoute(), source, destination);
    }

    private List<TrainStoppage> stationsBetweenAandB(List<TrainStoppage> allTrainStoppages, String a, String b) {

        int i = 0;
        int startIndex = 0;
        int endIndex = allTrainStoppages.size() - 1;
        while (!allTrainStoppages.get(i).getStationCode().equals(a))
            i++;
        startIndex = i;
        while (!allTrainStoppages.get(i).getStationCode().equals(b))
            i++;
        endIndex = i + 1;
        return new ArrayList<>(allTrainStoppages.subList(startIndex, endIndex));
    }

    public PnrAndStationDetails getStationsBetweenLocations(String sourceCode, String destinationCode, String date, String trainNum) {
        TrainDetailsList trainList = getTrainsBetweenLocations(sourceCode, destinationCode, date);
        trainList.sanitize().setDate(date);
        boolean isTrainPresent = isTrainPresent(trainList.getTrains(), trainNum);
        if (isTrainPresent == false) {
            return new PnrAndStationDetails().error(404, "No train present between these locations on given date");
        }

        TrainRouteLibResponse trainRouteDetails = RailwayLibrary.get()
                .getTrainRouteDetails(trainNum).sanitize();
        List<TrainStoppage> stoppageList = getStationBetweenSourceAndDestinaton(trainRouteDetails, sourceCode, destinationCode);
        return buildStationListResponse(trainList, trainRouteDetails, stoppageList);
    }

    private PnrAndStationDetails buildStationListResponse(TrainDetailsList trainList, TrainRouteLibResponse trainRouteDetails, List<TrainStoppage> stoppageList) {
        PnrAndStationDetails response = PnrAndStationDetails.builder()
                .srcStationCode(trainList.getSrcStationCode())
                .srcStationName(trainList.getSrcStationName())
                .destStationCode(trainList.getDestStationCode())
                .destStationName(trainList.getDestStationName())
                .date(trainList.getDate())
                .trainNum(trainRouteDetails.getTrain().getNumber())
                .trainName(trainRouteDetails.getTrain().getName())
                .trainStoppages(stoppageList).build();
        return response;
    }

    public boolean isTrainPresent(List<TrainDetails> trains, String trainNum) {
        for (int i = 0; i < trains.size(); i++)
            if (trains.get(i).getTrainNum().equals(trainNum))
                return true;
        return false;
    }
}
