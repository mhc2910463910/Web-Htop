package com.lzmhc.webhtop.services;

import com.lzmhc.webhtop.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OperatingSystem;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class InfoService {
    @Autowired
    private SystemInfo systemInfo;
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
//        操作系统
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
//        全局内存
        GlobalMemoryDto globalMemoryDto=new GlobalMemoryDto();
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        globalMemoryDto.setAvailable(memory.getAvailable());
        globalMemoryDto.setPagesize(memory.getPageSize());
        globalMemoryDto.setPhysicalMemoryList(memory.getPhysicalMemory());
        globalMemoryDto.setTotal(memory.getTotal());
        globalMemoryDto.setVirtualMemory(memory.getVirtualMemory());
        return globalMemoryDto;
    }

    /**
     * BIOS info
     * @return
     */
    private ComputerSystemDto getComputerSystem(){
        ComputerSystemDto computerSystemDto = new ComputerSystemDto();
        ComputerSystem computerSystem = systemInfo.getHardware().getComputerSystem();
        Baseboard baseboard = computerSystem.getBaseboard();
        String manufacturer = baseboard.getManufacturer();
        String model = baseboard.getModel();
        Firmware firmware = computerSystem.getFirmware();
        String version = firmware.getVersion();
        String releaseDate = firmware.getReleaseDate();
        computerSystemDto.setManufacturer(manufacturer);
        computerSystemDto.setModel(model);
        computerSystemDto.setVersion(version);
        computerSystemDto.setRelease_date(releaseDate);
        return computerSystemDto;
    }

    /**
     * Storage info
     * @param bits
     * @return
     */
    private String getConvertedCapacity(long bits)
    {
        if ((bits / 1.049E+6) > 999)
        {
            if ((bits / 1.074E+9) > 999)
            {
                return (Math.round((bits / 1.1E+12) * 10.0) / 10.0) + " TiB";
            }
            else
            {
                return Math.round(bits / 1.074E+9) + " GiB";
            }
        }
        else
        {
            return Math.round(bits / 1.049E+6) + " MiB";
        }
    }
    private StorageDto getStorage(){
        StorageDto storageDto = new StorageDto();

        List<HWDiskStore> hwDiskStoreList = systemInfo.getHardware().getDiskStores();
        GlobalMemory globalMemory=systemInfo.getHardware().getMemory();

        Optional<HWDiskStore> hwDiskStoreOptional=hwDiskStoreList.stream().findFirst();
        if(hwDiskStoreOptional.isPresent()){
            String mainStorage=hwDiskStoreOptional.get().getModel();
            if(mainStorage.contains("(Standard disk drives)")){
                mainStorage=mainStorage.substring(0, mainStorage.indexOf("(Standard disk drives)")-1);
            }
            storageDto.setMainStorage(mainStorage.trim());
        }else{
            storageDto.setMainStorage("Undefined");
        }

        long total = hwDiskStoreList.stream().mapToLong(HWDiskStore::getSize).sum();
        storageDto.setTotal(getConvertedCapacity(total)+" Total");
        int diskCount = hwDiskStoreList.size();
        storageDto.setDiskCount(diskCount+((diskCount>1)? "Disks":"Disk"));
        storageDto.setSwapAmount(getConvertedCapacity(globalMemory.getVirtualMemory().getSwapTotal())+" Swap");
        return storageDto;
    }

    private Sensors getSensors(){
        return systemInfo.getHardware().getSensors();
    }
    //      CPU温度和风扇转速
    private List<GraphicsCard> getGraphicsCards(){
        return systemInfo.getHardware().getGraphicsCards();
    }
    //      显卡
    public InfoDto getInfo() {
        InfoDto infoDto = new InfoDto();
        infoDto.setProcessorDto(this.getProcessor());
        infoDto.setOperatingSystemDto(this.getOperatingSystem());
        infoDto.setGlobalMemoryDto(this.getGlobalMemory());
        infoDto.setComputerSystemDto(this.getComputerSystem());
        infoDto.setPowerSourceList(systemInfo.getHardware().getPowerSources());
        infoDto.setStorageDto(this.getStorage());
        return infoDto;
    }
}
