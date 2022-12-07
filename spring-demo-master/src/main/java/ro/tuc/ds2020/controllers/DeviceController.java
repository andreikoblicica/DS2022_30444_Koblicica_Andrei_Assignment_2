package ro.tuc.ds2020.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.tuc.ds2020.dtos.ConsumptionDTO;
import ro.tuc.ds2020.dtos.DeviceDTO;
import ro.tuc.ds2020.services.DeviceService;

import javax.validation.Valid;
import java.sql.Date;
import java.util.List;

import static ro.tuc.ds2020.UrlMapping.*;

@RestController
@RequestMapping
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }


    @CrossOrigin
    @GetMapping(DEVICES)
    public ResponseEntity<List<DeviceDTO>> allDevices() {
        List<DeviceDTO> deviceDTOS=deviceService.findAllDevices();
        return new ResponseEntity<>(deviceDTOS, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping(USER_DEVICES)
    public ResponseEntity<List<DeviceDTO>> findByUserId(@PathVariable Long id) {
        List<DeviceDTO> deviceDTOS=deviceService.findByUserId(id);
        return new ResponseEntity<>(deviceDTOS, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(DEVICES)
    public ResponseEntity<DeviceDTO> create(@Valid @RequestBody DeviceDTO deviceDTO) {
        DeviceDTO device = deviceService.create(deviceDTO);
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    @CrossOrigin
    @PutMapping(DEVICES)
    public  ResponseEntity<DeviceDTO> update(@Valid @RequestBody DeviceDTO deviceDTO) {
        DeviceDTO device = deviceService.update(deviceDTO);
        return new ResponseEntity<>(device,HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping(DEVICES+DEVICE_ID)
    public void delete(@PathVariable Long id) {
        deviceService.delete(id);
    }

    @CrossOrigin
    @GetMapping(FILTERS)
    public List<ConsumptionDTO> findConsumption(@PathVariable Long id, @PathVariable String date) {
        return deviceService.getConsumptionByDeviceIdAndDate(id,date);
    }
}
