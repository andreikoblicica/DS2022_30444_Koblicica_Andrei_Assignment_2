package ro.tuc.ds2020.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Device {

    @Id
    @Column
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column
    private String description;

    @Column
    private String address;

    @Column
    private float maxHourlyConsumption;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;


    @OneToMany(mappedBy="device",fetch=FetchType.EAGER)
    private List<ConsumptionEntry> consumption;

    public Device(String description, String address, float maxHourlyConsumption, User user, List<ConsumptionEntry> consumption) {
        this.description = description;
        this.address = address;
        this.maxHourlyConsumption = maxHourlyConsumption;
        this.user = user;
        this.consumption = consumption;
    }

    public Device(String description, String address, float maxHourlyConsumption) {
        this.description = description;
        this.address = address;
        this.maxHourlyConsumption = maxHourlyConsumption;
    }
}
