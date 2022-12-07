package com.example.demo;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

@Service
public class MessageSender {

    private static final Logger log = LoggerFactory.getLogger(MessageSender.class);
    @Autowired
    private final AmqpTemplate rabbitTemplate;
    private float currentConsumption=0;
    private LocalDateTime currentTimestamp=LocalDateTime.now();
    private final Scanner scanner;
    public MessageSender(final AmqpTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        try {
            this.scanner = new Scanner(new File("F:\\faculta an4sem1\\DS\\DS2022_30444_Koblicica_Andrei_Assignment_2\\MessageProducer\\sensor.csv"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    @Scheduled(fixedDelay = 1000L)
    public void sendMessage() throws IOException {
        if(scanner.hasNext()){
            float value=scanner.nextFloat();
            float consumption=value-currentConsumption;
            currentConsumption=value;
//            FileInputStream propsInput = new FileInputStream("src/main/resources/device.properties");
//            Properties prop = new Properties();
//            prop.load(propsInput);
//            Long deviceId= Long.valueOf(prop.getProperty("DEVICE_ID"));
            Long deviceId=Long.parseLong(System.getenv("DEVICE_ID"));
            System.out.println(deviceId);
            ConsumptionEntry consumptionEntry=new ConsumptionEntry(Timestamp.valueOf(currentTimestamp),deviceId,consumption);
            currentTimestamp=currentTimestamp.plusMinutes(10);
            log.info(consumptionEntry.getTimestamp()+" "+consumptionEntry.getConsumption());
            rabbitTemplate.convertAndSend("consumptionQueue", new Gson().toJson(consumptionEntry));

        }

    }
}