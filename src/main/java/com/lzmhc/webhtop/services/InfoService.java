package com.lzmhc.webhtop.services;

import com.lzmhc.webhtop.dto.InfoDto;
import com.lzmhc.webhtop.dto.ProcessorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.OperatingSystem;
import java.util.Arrays;

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
    public InfoDto getInfo() throws Exception{
        InfoDto infoDto = new InfoDto();
        infoDto.setProcessorDto(getProcessor());
        return infoDto;
    }
}
