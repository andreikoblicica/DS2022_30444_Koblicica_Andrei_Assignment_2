package ro.tuc.ds2020.consumer;

import com.google.gson.Gson;
import lombok.Getter;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ro.tuc.ds2020.controllers.WebSocketController;
import ro.tuc.ds2020.dtos.ConsumptionDTO;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.dtos.WebSocketMessageDTO;
import ro.tuc.ds2020.entities.ConsumptionEntry;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.repositories.ConsumptionRepository;
import ro.tuc.ds2020.repositories.DeviceRepository;
import ro.tuc.ds2020.services.DeviceService;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;


@Getter
@Service
public class MessageConsumer {

    private final WebSocketController webSocketController;


    private final DeviceRepository deviceRepository;

    private HashMap<Long, ConsumptionDTO> currentDeviceConsumptions;
    private HashMap<Long, AtomicBoolean> deviceHasExceeded;

    private final ConsumptionRepository consumptionRepository;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");

    public MessageConsumer(DeviceRepository deviceRepository, ConsumptionRepository consumptionRepository, WebSocketController webSocketController){
        this.deviceRepository=deviceRepository;
        this.consumptionRepository=consumptionRepository;
        this.webSocketController=webSocketController;
        getAllDevices();
    }

    private void getAllDevices(){
        currentDeviceConsumptions=new HashMap<>();
        deviceHasExceeded=new HashMap<>();
        List<Device> devices=deviceRepository.findAll();
        for(Device deviceDTO: devices){
            currentDeviceConsumptions.put(deviceDTO.getId(),new ConsumptionDTO(25));
            deviceHasExceeded.put(deviceDTO.getId(),new AtomicBoolean(false));
        }
    }

    @RabbitListener(containerFactory = "rabbitListenerContainerFactory",queues="consumptionQueue")
    public void getMessage(final String consumption) throws IOException {
        System.out.println(consumption);
        ConsumptionReading reading=new Gson().fromJson(consumption,ConsumptionReading.class);
        Device device=deviceRepository.findById(reading.getDeviceId()).orElseThrow(() -> new EntityNotFoundException("Device not found: " + reading.getDeviceId()));
        ConsumptionDTO consumptionDTO=currentDeviceConsumptions.get(device.getId());

        int currentHour=Integer.parseInt(simpleDateFormat.format(reading.getTimestamp()));
        if(consumptionDTO.getHour()!=currentHour){
            consumptionDTO.setHour(currentHour);
            consumptionDTO.setConsumption(reading.getConsumption());
        }else{
            consumptionDTO.setConsumption(consumptionDTO.getConsumption()+ reading.getConsumption());
        }
        ConsumptionEntry consumptionEntry=new ConsumptionEntry(device,reading.getTimestamp(), reading.getConsumption());
        consumptionRepository.save(consumptionEntry);

        if(consumptionDTO.getConsumption()>device.getMaxHourlyConsumption() && !deviceHasExceeded.get(device.getId()).get()) {
            webSocketController.sendMessage(new WebSocketMessageDTO("Max hourly consumption exceeded for device ",device.getId()));
            deviceHasExceeded.get(device.getId()).set(true);

        }


    }
}
