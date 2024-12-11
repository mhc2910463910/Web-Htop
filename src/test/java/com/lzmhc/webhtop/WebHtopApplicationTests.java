package com.lzmhc.webhtop;

import com.lzmhc.webhtop.controllers.InfoController;
import com.lzmhc.webhtop.dto.InfoDto;
import com.lzmhc.webhtop.services.InfoService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
//测试运行器，并加载SpringBoot测试注解
@SpringBootTest
//标记单元测试类，加载项目的上下文环境ApplicationContext
class WebHtopApplicationTests {
    @Autowired
    private InfoController infoController;
    @Test
    public void getOperatingSystemInfo() {
        InfoDto body = (InfoDto) infoController.getInfo().getBody();
        System.out.println(body.getOperatingSystemDto().getBitness());
//        操作系统的位数
        System.out.println(body.getOperatingSystemDto().getVersionInfo().getVersion());
        System.out.println(body.getOperatingSystemDto().getVersionInfo().getBuildNumber());
        System.out.println(body.getOperatingSystemDto().getVersionInfo().getCodeName());
        System.out.println(body.getOperatingSystemDto().getSystemboottime());
    }
    @Test
    public void getMemoryInfo(){
        InfoDto body=(InfoDto) infoController.getInfo().getBody();
        System.out.println((double) (body.getGlobalMemoryDto().getAvailable()/1024.0/1024.0/1024.0) + " GB");
//        System.out.println(body.getGlobalMemoryDto().getPhysicalMemoryList());
//        需要root权限
        System.out.println(body.getGlobalMemoryDto().getVirtualMemory());
        System.out.println((double)(body.getGlobalMemoryDto().getPagesize()/1024.0) +" KB");
        System.out.println((double)(body.getGlobalMemoryDto().getTotal()/1024.0/1024.0/1024.0) +" GB");
    }

    @Test
    public void getProcessorInfo(){
        InfoDto body=(InfoDto) infoController.getInfo().getBody();
        System.out.println(body.getProcessorDto().getBitDepth());
        System.out.println(body.getProcessorDto().getCoreCount());
        System.out.println(body.getProcessorDto().getCurrentFreq());
        System.out.println(body.getProcessorDto().getMaxFreq());
        System.out.println(body.getProcessorDto().getPhysicalProcessorCount());
        System.out.println(body.getProcessorDto().getName());
    }
    @Test
    public void getComputerSystemInfo(){
        InfoDto body = (InfoDto) infoController.getInfo().getBody();
        System.out.println(body.getComputerSystemDto().getBaseboard());
        System.out.println(body.getComputerSystemDto().getFirmware());
        System.out.println(body.getComputerSystemDto().getManufacturer());
        System.out.println(body.getComputerSystemDto().getSerialNumber());
    }
    @Test
    public void getPowerSourcesInfo(){
        InfoDto body = (InfoDto) infoController.getInfo().getBody();
        System.out.println(body.getPowerSourceList());
    }
    @Test
    public void getStorageInfo(){
        InfoDto body = (InfoDto) infoController.getInfo().getBody();
        System.out.println(body.getStorageDto().getMainStorage());
        System.out.println(body.getStorageDto().getDiskCount());
        System.out.println(body.getStorageDto().getTotal());
        System.out.println(body.getStorageDto().getSwapAmount());
    }
}
