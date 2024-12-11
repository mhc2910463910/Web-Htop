package com.lzmhc.webhtop.services;

import com.lzmhc.webhtop.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OperatingSystem;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InfoService {
    @Autowired
    private SystemInfo systemInfo;

    /**
     * processor info
     * @param hertzArray
     * @return
     */
    private String getConvertedFrequency(long[] hertzArray){
        long totalFrequency = Arrays.stream(hertzArray).sum();
        long hertz = totalFrequency / hertzArray.length;
        if((hertz/1E+6)>999){
            return (Math.round((hertz / 1E+9)*10.0)/10.0)+" GHz";
        }else{
            return Math.round(hertz/1E+6)+" MHz";
        }
    }
    private String getConvertedFrequency(long hertz){
        if((hertz/1E+6)>999){
            return (Math.round((hertz / 1E+9)*10.0)/10.0)+" GHz";
        }else{
            return Math.round(hertz/1E+6)+" MHz";
        }
    }
    /**
     * Processor info
     * @return
     */
    private ProcessorDto getProcessor(){
        ProcessorDto processorDto = new ProcessorDto();
        CentralProcessor centralProcessor = systemInfo.getHardware().getProcessor();
        CentralProcessor.ProcessorIdentifier processorIdentifier = centralProcessor.getProcessorIdentifier();
        String name = processorIdentifier.getName();
        processorDto.setName(name.trim());
        int coreCount = centralProcessor.getLogicalProcessorCount();
        processorDto.setCoreCount(coreCount);
        processorDto.setMaxFreq(getConvertedFrequency(centralProcessor.getMaxFreq()));
        processorDto.setCurrentFreq(getConvertedFrequency(centralProcessor.getCurrentFreq()));
        String BitDepthPrefix = processorIdentifier.isCpu64bit()?"64":"32";
        processorDto.setBitDepth(BitDepthPrefix+"-bit");
        return processorDto;
    }
    /**
     * OperationSystem info
     * @return
     */
    private OperatingSystemDto getOperatingSystem(){
//        操作系统
        OperatingSystemDto operatingSystemDto=new OperatingSystemDto();
        OperatingSystem operatingSystem = systemInfo.getOperatingSystem();
        operatingSystemDto.setBitness(operatingSystem.getBitness());
        operatingSystemDto.setFamily(operatingSystemDto.getFamily());
        operatingSystemDto.setVersionInfo(operatingSystem.getVersionInfo());
        Long timestamp = operatingSystem.getSystemBootTime();
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp*1000));
        operatingSystemDto.setSystemboottime(date);
        return operatingSystemDto;
    }
    /**
     * Memory info
     * @return
     */
    private GlobalMemoryDto getGlobalMemory(){
//        全局内存
        GlobalMemoryDto globalMemoryDto=new GlobalMemoryDto();
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        double availabel = memory.getAvailable()/1024.0/1024.0/1024.0;
        globalMemoryDto.setAvailableMemory(String.format("%.2f",availabel));
        double total = memory.getTotal()/1024.0/1024.0/1024.0;
        globalMemoryDto.setTotalMemory(String.format("%.2f", total));
        double used = total-availabel;
        globalMemoryDto.setUsedMemory(String.format("%.2f", used));
        double virtualUsedMemory=memory.getVirtualMemory().getSwapUsed()/1024.0/1024.0/1024.0;
        globalMemoryDto.setVirtualUsedMemory(String.format("%.2f", virtualUsedMemory));
        double virtualMemory=memory.getVirtualMemory().getSwapTotal()/1024.0/1024.0/1024.0;
        globalMemoryDto.setVirtuallMemory(String.format("%.2f", virtualMemory));
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

    /**
     * Storage info
     * @return
     */
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
