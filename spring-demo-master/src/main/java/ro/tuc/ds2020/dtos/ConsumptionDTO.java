package ro.tuc.ds2020.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class ConsumptionDTO {
    private int hour;
    private float consumption;

    public ConsumptionDTO(int hour) {
        this.hour = hour;
        this.consumption=0;
    }

    public ConsumptionDTO(int hour, float consumption) {
        this.hour = hour;
        this.consumption = consumption;
    }
}
