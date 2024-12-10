package com.lzmhc.webhtop.dto;

import lombok.Getter;
import lombok.Setter;
import oshi.hardware.GlobalMemory;

@Getter
@Setter
public class InfoDto {
    private ProcessorDto processorDto;
//    处理器
    private OperatingSystemDto operatingSystemDto;
//    操作系统
    private GlobalMemoryDto globalMemoryDto;
//    内存
}
