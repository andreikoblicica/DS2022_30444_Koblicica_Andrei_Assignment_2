package ro.tuc.ds2020.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import ro.tuc.ds2020.consumer.MessageConsumer;
import ro.tuc.ds2020.dtos.ConsumptionDTO;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.builders.DeviceBuilder;
import ro.tuc.ds2020.entities.ConsumptionEntry;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.ConsumptionRepository;
import ro.tuc.ds2020.repositories.DeviceRepository;
import ro.tuc.ds2020.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;


@Service
public class DeviceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceService.class);
    private final DeviceRepository deviceRepository;
    private final UserRepository userRepository;
    private final ConsumptionRepository consumptionRepository;
    private final MessageConsumer messageConsumer;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository, UserRepository userRepository, ConsumptionRepository consumptionRepository, MessageConsumer messageConsumer) {
        this.deviceRepository = deviceRepository;
        this.userRepository = userRepository;
        this.consumptionRepository = consumptionRepository;
        this.messageConsumer=messageConsumer;
    }

    public Device findById(Long id) {
        return deviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Device not found: " + id));
    }

    public List<DeviceDTO> findAllDevices() {
        List<Device> deviceList = deviceRepository.findAll();
        return deviceList.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    public List<DeviceDTO> findByUserId(Long id) {
        List<Device> deviceList = deviceRepository.findByUserId(id);
        return deviceList.stream()
                .map(DeviceBuilder::toDeviceDTO)
                .collect(Collectors.toList());
    }

    public DeviceDTO create(DeviceDTO deviceDTO) {
        Device device=  DeviceBuilder.toEntity(deviceDTO);
        User user=userRepository.findByUsername(deviceDTO.getUsername()).orElse(null);
        device.setUser(user);
        Device saved=deviceRepository.save(device);
        messageConsumer.getCurrentDeviceConsumptions().put(saved.getId(),new ConsumptionDTO(25));
        messageConsumer.getDeviceHasExceeded().put(saved.getId(),new AtomicBoolean(false));
        return DeviceBuilder.toDeviceDTO(saved);

    }

    public void delete(Long id){
        deviceRepository.deleteById(id);
    }

    public DeviceDTO update(DeviceDTO deviceDTO){
        Device device = findById(deviceDTO.getId());
        User user=userRepository.findByUsername(deviceDTO.getUsername()).orElse(null);
        device.setAddress(deviceDTO.getAddress());
        device.setDescription(deviceDTO.getDescription());
        device.setMaxHourlyConsumption(deviceDTO.getMaxHourlyConsumption());
        device.setUser(user);
        return DeviceBuilder.toDeviceDTO(
                deviceRepository.save(device)
        );
    }

    public List<ConsumptionDTO> getConsumptionByDeviceIdAndDate(Long id, String timestamp){
        List<ConsumptionEntry> consumptionEntries=consumptionRepository.findByFilters(id, timestamp);
        return DeviceBuilder.toConsumptionDTOS(consumptionEntries);
    }





}
