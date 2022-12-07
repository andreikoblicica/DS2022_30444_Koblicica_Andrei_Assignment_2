package ro.tuc.ds2020.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class ConsumptionEntry {


    @Id
    @Column
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="device_id", nullable=false)
    private Device device;

    @Column
    private Timestamp timestamp;

    @Column
    private Float consumption;

    public ConsumptionEntry(Device device, Timestamp timestamp, Float consumption) {
        this.device = device;
        this.timestamp = timestamp;
        this.consumption = consumption;
    }
}
