package com.lzmhc.webhtop.dto;

import lombok.Getter;
import lombok.Setter;
import oshi.hardware.Baseboard;
import oshi.hardware.Firmware;

@Getter
@Setter
public class ComputerSystemDto {
    private String manufacturer;
    /**
     * BIOS制造商
     */
    private String model;
    /**
     * 产品名称
     */
    private String version;
    /**
     * 版本
     */
    private String release_date;
    /**
     * 发布日期
     */
}
