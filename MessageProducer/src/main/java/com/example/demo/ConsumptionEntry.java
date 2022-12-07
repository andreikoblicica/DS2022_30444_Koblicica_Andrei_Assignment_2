package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ConsumptionEntry {
    private Timestamp timestamp;
    private Long deviceId;
    private Float consumption;
}
