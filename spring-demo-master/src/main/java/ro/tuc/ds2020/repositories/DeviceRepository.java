package ro.tuc.ds2020.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.tuc.ds2020.entities.Device;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findById(Long id);

    List<Device> findAll();

    void deleteById(Long id);

    List<Device> findByUserId(Long id);
}