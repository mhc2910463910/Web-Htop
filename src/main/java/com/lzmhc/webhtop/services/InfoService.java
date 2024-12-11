package com.lzmhc.webhtop.services;

import com.lzmhc.webhtop.dto.GlobalMemoryDto;
import com.lzmhc.webhtop.dto.InfoDto;
import com.lzmhc.webhtop.dto.OperatingSystemDto;
import com.lzmhc.webhtop.dto.ProcessorDto;
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
//    private OperatingSystem getOperatingSystem(){
//        return systemInfo.getOperatingSystem();
//    }
    //        获取操作系统
    private ComputerSystem getComputerSystem(){
        return systemInfo.getHardware().getComputerSystem();
    }
    //       物理硬件，包括BIOS和主板等
//    private CentralProcessor getCentralProcessor(){
//        return systemInfo.getHardware().getProcessor();
//    }
    //      处理器
//    private GlobalMemory getGlobalMemory(){
//        return systemInfo.getHardware().getMemory();
//    }
    //      内存
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
        String name = centralProcessor.getProcessorIdentifier().getProcessorID();
        if(name.contains("@")){
            name = name.substring(0, name.indexOf('@')-1);
        }
        processorDto.setName(name.trim());

        int coreCount = centralProcessor.getLogicalProcessorCount();
        processorDto.setCoreCount(coreCount+((coreCount>1)?"Cores":"Core"));
        processorDto.setClockSpeed(getConvertedFrequency(centralProcessor.getCurrentFreq()));

        String BitDepthPrefix = centralProcessor.getProcessorIdentifier().isCpu64bit()?"64":"32";
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
    public InfoDto getInfo() {
        InfoDto infoDto = new InfoDto();
        infoDto.setProcessorDto(this.getProcessor());
        infoDto.setOperatingSystemDto(this.getOperatingSystem());
        infoDto.setGlobalMemoryDto(this.getGlobalMemory());
        return infoDto;
    }
}
