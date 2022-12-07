package ro.tuc.ds2020.consumer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ConsumptionReading {
    private Timestamp timestamp;
    private Long deviceId;
    private Float consumption;
}
