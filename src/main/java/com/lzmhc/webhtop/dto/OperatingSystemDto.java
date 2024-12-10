package com.lzmhc.webhtop.dto;

import lombok.Getter;
import lombok.Setter;
import oshi.software.os.OperatingSystem;

@Setter
@Getter
public class OperatingSystemDto {
    private String family;
//    系统家族
    private int bitness;
//    操作系统的位数
    private String manufacturer;
//    操作系统制造商
    private int systemuptime;
//    操作系统启动时间
    private OperatingSystem.OSVersionInfo versionInfo;
//    操作系统版本信息
}