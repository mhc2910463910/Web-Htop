package com.lzmhc.webhtop.dto;

import lombok.Getter;
import lombok.Setter;
import oshi.hardware.PhysicalMemory;
import oshi.hardware.VirtualMemory;

import java.util.List;

@Getter
@Setter
public class GlobalMemoryDto {
    private String available;
//    可用内存
    private String total;
//    物理内存
    private VirtualMemory virtualMemory;
//    虚拟内存
}
