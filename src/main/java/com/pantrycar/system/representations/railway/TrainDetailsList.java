package com.pantrycar.system.representations.railway;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pantrycar.system.representations.BasicResponse;
import lombok.*;

import java.util.List;

/**
 * Created by kunal.agarwal on 06/05/15.
 */

@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrainDetailsList extends BasicResponse {

    private String srcStationName;
    private String destStationName;
    private String srcStationCode;
    private String destStationCode;
    private StationDetails fromStation;
    private StationDetails toStation;
    private String date;
    private List<TrainDetails> trains;

    public TrainDetailsList sanitize() {
        this.setSrcStationCode(this.getFromStation().getCode());
        this.setSrcStationName(this.getFromStation().getName());
        this.setDestStationCode(this.getToStation().getCode());
        this.setDestStationName(this.getToStation().getName());

        for (TrainDetails train : trains) {
            train.setTrainName(train.getName());
            train.setTrainNum(train.getNumber());
        }
        return this;
    }

}
