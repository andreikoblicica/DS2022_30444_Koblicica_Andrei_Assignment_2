package ro.tuc.ds2020.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import ro.tuc.ds2020.entities.ConsumptionEntry;
import ro.tuc.ds2020.entities.User;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDTO extends RepresentationModel<DeviceDTO> {
    private Long id;
    private String description;
    private String address;
    private float maxHourlyConsumption;
    private String username;


    @Override
    public String toString() {
        return "DeviceDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", maxHourlyConsumption=" + maxHourlyConsumption +
                ", user=" + username +
                '}';
    }
}
