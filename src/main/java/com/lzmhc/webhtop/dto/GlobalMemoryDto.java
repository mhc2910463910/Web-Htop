package com.lzmhc.webhtop.dto;

import lombok.Getter;
import lombok.Setter;
import oshi.hardware.PhysicalMemory;
import oshi.hardware.VirtualMemory;

import java.util.List;

@Getter
@Setter
public class GlobalMemoryDto {
    private long available;
//    可用内存
    private long pagesize;
//    字节数
    private List<PhysicalMemory> physicalMemoryList;
//    物理内存
    private long total;
//    实际物理内存
    private VirtualMemory virtualMemory;
//    虚拟内存
}
