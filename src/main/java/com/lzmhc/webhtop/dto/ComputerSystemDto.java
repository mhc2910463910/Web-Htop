package com.lzmhc.webhtop.dto;

import lombok.Getter;
import lombok.Setter;
import oshi.hardware.Baseboard;
import oshi.hardware.Firmware;

@Getter
@Setter
public class ComputerSystemDto {
    private Baseboard baseboard;
//    底板/主板
    private Firmware firmware;
//    系统固件
    private String manufacturer;
//    系统制造商
    private String serialNumber;
//    系统序列号
}
