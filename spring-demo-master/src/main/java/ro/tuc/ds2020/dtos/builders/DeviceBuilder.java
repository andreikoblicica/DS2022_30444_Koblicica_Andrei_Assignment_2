package ro.tuc.ds2020.dtos.builders;

import ro.tuc.ds2020.dtos.ConsumptionDTO;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.entities.ConsumptionEntry;
import ro.tuc.ds2020.entities.Device;
import ro.tuc.ds2020.entities.User;
import ro.tuc.ds2020.repositories.UserRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DeviceBuilder {

    private static UserRepository userRepository;

    private DeviceBuilder() {

    }



    public static DeviceDTO toDeviceDTO(Device device) {
        return new DeviceDTO(device.getId(), device.getDescription(), device.getAddress(),device.getMaxHourlyConsumption(),device.getUser().getUsername());
    }


    public static Device toEntity(DeviceDTO deviceDTO) {
        return new Device(
                deviceDTO.getDescription(),
                deviceDTO.getAddress(),
                deviceDTO.getMaxHourlyConsumption());
    }

    public static List<ConsumptionDTO> toConsumptionDTOS(List<ConsumptionEntry> consumptionEntries){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH");
        List<ConsumptionDTO> consumptionDTOS=new ArrayList<>();
        for(int i=0;i<24;i++){
            consumptionDTOS.add(new ConsumptionDTO(i));
        }
        for(ConsumptionEntry consumptionEntry: consumptionEntries){
            ConsumptionDTO consumptionDTO=consumptionDTOS.get(Integer.parseInt(simpleDateFormat.format(consumptionEntry.getTimestamp())));
            float currentHourConsumption=consumptionDTO.getConsumption();
            consumptionDTO.setConsumption(currentHourConsumption+consumptionEntry.getConsumption());
        }
        return  consumptionDTOS;
    }
}
