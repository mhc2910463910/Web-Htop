package com.lzmhc.webhtop.dto;

import lombok.Getter;
import lombok.Setter;
import oshi.hardware.GlobalMemory;
import oshi.hardware.PowerSource;

import java.util.List;

@Getter
@Setter
public class InfoDto {
    private ComputerSystemDto computerSystemDto;
    /**
     * 物理硬件
     */
    private ProcessorDto processorDto;
    /**
     * 处理器
     */
    private OperatingSystemDto operatingSystemDto;
    /**
     * 操作系统
     */
    private GlobalMemoryDto globalMemoryDto;
    /**
     * 内存
     */
    private List<PowerSource> powerSourceList;
    /**
     * 电池
     */
    private StorageDto storageDto;
    /**
     * 存储
     */
    private GraphicsCardDto graphicsCardDto;
    /**
     * 显卡
     */
}
