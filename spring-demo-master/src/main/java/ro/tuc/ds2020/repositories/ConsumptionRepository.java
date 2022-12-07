package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ro.tuc.ds2020.entities.ConsumptionEntry;
import ro.tuc.ds2020.entities.Device;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface ConsumptionRepository extends JpaRepository<ConsumptionEntry, Long> {
    @Query(value="SELECT * FROM consumption_entry e WHERE e.device_id = ?1 and e.timestamp like %?2%",nativeQuery = true)
    List<ConsumptionEntry> findByFilters(Long device_id, String timestamp);
}
