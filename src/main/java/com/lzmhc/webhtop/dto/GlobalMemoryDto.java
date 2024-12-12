package com.lzmhc.webhtop.dto;

import lombok.Getter;
import lombok.Setter;
import oshi.hardware.PhysicalMemory;
import oshi.hardware.VirtualMemory;

import java.util.List;

@Getter
@Setter
public class GlobalMemoryDto {
    private String availableMemory;
    /**
     * 可用物理内存
     */
    private String usedMemory;
    /**
     * 已用物理内存
     */
    private String totalMemory;
    /**
     * 物理内存
     */
    private String virtualUsedMemory;
    /**
     * 已用虚拟内存
     */
    private String virtuallMemory;
    /**
     * 虚拟内存
     */
    private double percentage;
    /**
     * 已用/总的比率
     */
    private String ramTypeOrOsBitDepth;
    /**
     * Ram type / OsBit
     */
    private String procCount;
    /**
     * Processes Count field
     */
}
