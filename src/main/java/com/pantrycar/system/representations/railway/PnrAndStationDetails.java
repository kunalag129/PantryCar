package com.pantrycar.system.representations.railway;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PnrAndStationDetails extends BasicResponse {

    private String pnr;
    private String trainNum;
    private String trainName;
    private String srcStationCode;
    private String destStationCode;
    private String srcStationName;
    private String destStationName;
    private String date;
    private List<TrainStoppage> trainStoppages;
    private String boardingClass;
    private String chartedPrepared;
    private List<PassengerDetails> passengers;

}
