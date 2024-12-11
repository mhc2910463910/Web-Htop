package com.lzmhc.webhtop.services;

import com.lzmhc.webhtop.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OperatingSystem;
import java.util.Arrays;
import java.util.List;

@Service
public class InfoService {
    @Autowired
    private SystemInfo systemInfo;
    private List<PowerSource> getPowerSources(){
        return systemInfo.getHardware().getPowerSources();
    }
    //      电池
    private List<HWDiskStore> getDiskStores(){
        return systemInfo.getHardware().getDiskStores();
    }
    //      物理硬盘
    private Sensors getSensors(){
        return systemInfo.getHardware().getSensors();
    }
    //      CPU温度和风扇转速
    private List<GraphicsCard> getGraphicsCards(){
        return systemInfo.getHardware().getGraphicsCards();
    }
    //      显卡

    private String getConvertedFrequency(long[] hertzArray){
        long totalFrequency = Arrays.stream(hertzArray).sum();
        long hertz = totalFrequency / hertzArray.length;
        if((hertz/1E+6)>999){
            return (Math.round((hertz / 1E+9)*10.0)/10.0)+" GHz";
        }else{
            return Math.round(hertz/1E+6)+" MHz";
        }
    }
    private ProcessorDto getProcessor(){
        ProcessorDto processorDto = new ProcessorDto();
        CentralProcessor centralProcessor = systemInfo.getHardware().getProcessor();
        CentralProcessor.ProcessorIdentifier processorIdentifier = centralProcessor.getProcessorIdentifier();
        String name = processorIdentifier.getProcessorID()+"_"
                +processorIdentifier.getName()+"_"
                +processorIdentifier.getVendor();
//        CPU的标识符
//        if(name.contains("@")){
//            name = name.substring(0, name.indexOf('@')-1);
//        }
        processorDto.setName(name.trim());
        int coreCount = centralProcessor.getLogicalProcessorCount();
//        可用于处理的CPU数量
        processorDto.setCoreCount(coreCount);
        processorDto.setMaxFreq(centralProcessor.getMaxFreq());
        processorDto.setCurrentFreq(getConvertedFrequency(centralProcessor.getCurrentFreq()));
        String BitDepthPrefix = processorIdentifier.isCpu64bit()?"64":"32";
        processorDto.setBitDepth(BitDepthPrefix+"-bit");
        return processorDto;
    }

    private OperatingSystemDto getOperatingSystem(){
        OperatingSystemDto operatingSystemDto=new OperatingSystemDto();
        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
        operatingSystemDto.setBitness(operatingSystem.getBitness());
        operatingSystemDto.setFamily(operatingSystemDto.getFamily());
        operatingSystemDto.setSystemuptime(operatingSystemDto.getSystemuptime());
        operatingSystemDto.setManufacturer(operatingSystemDto.getManufacturer());
        operatingSystemDto.setVersionInfo(operatingSystem.getVersionInfo());
        operatingSystemDto.setSystemboottime(operatingSystem.getSystemBootTime());
        return operatingSystemDto;
    }

    private GlobalMemoryDto getGlobalMemory(){
        GlobalMemoryDto globalMemoryDto=new GlobalMemoryDto();
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        globalMemoryDto.setAvailable(memory.getAvailable());
        globalMemoryDto.setPagesize(memory.getPageSize());
        globalMemoryDto.setPhysicalMemoryList(memory.getPhysicalMemory());
        globalMemoryDto.setTotal(memory.getTotal());
        globalMemoryDto.setVirtualMemory(memory.getVirtualMemory());
        return globalMemoryDto;
    }

    private ComputerSystemDto getComputerSystem(){
        ComputerSystemDto computerSystemDto = new ComputerSystemDto();
        ComputerSystem computerSystem = systemInfo.getHardware().getComputerSystem();
        computerSystemDto.setBaseboard(computerSystem.getBaseboard());
        computerSystemDto.setFirmware(computerSystem.getFirmware());
        computerSystemDto.setManufacturer(computerSystem.getManufacturer());
        computerSystemDto.setSerialNumber(computerSystem.getSerialNumber());
        return computerSystemDto;
    }
    //       物理硬件，包括BIOS和主板等
    public InfoDto getInfo() {
        InfoDto infoDto = new InfoDto();
        infoDto.setProcessorDto(this.getProcessor());
        infoDto.setOperatingSystemDto(this.getOperatingSystem());
        infoDto.setGlobalMemoryDto(this.getGlobalMemory());
        infoDto.setComputerSystemDto(this.getComputerSystem());
        return infoDto;
    }
}
