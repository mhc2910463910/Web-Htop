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
        System.out.println("操作系统: "+body.getOperatingSystemDto().getFamily());
        System.out.println("位数: "+body.getOperatingSystemDto().getBitness());
        System.out.println("版本: "+body.getOperatingSystemDto().getVersionInfo().getVersion());
        System.out.println("操作系统版本号: "+body.getOperatingSystemDto().getVersionInfo().getBuildNumber());
        System.out.println("开机时间: "+body.getOperatingSystemDto().getSystemboottime());
    }
    @Test
    public void getMemoryInfo(){
        InfoDto body=(InfoDto) infoController.getInfo().getBody();
        System.out.println("物理内存: "+body.getGlobalMemoryDto().getTotalMemory() +" GB");
        System.out.println("可用物理内存: "+body.getGlobalMemoryDto().getAvailableMemory() + " GB");
        System.out.println("已用物理内存: "+body.getGlobalMemoryDto().getUsedMemory() + " GB");
        System.out.println("虚拟内存: "+body.getGlobalMemoryDto().getVirtuallMemory()+" GB");
        System.out.println("已用虚拟内存: "+body.getGlobalMemoryDto().getVirtualUsedMemory()+" GB");
    }

    @Test
    public void getProcessorInfo(){
        InfoDto body=(InfoDto) infoController.getInfo().getBody();
        System.out.println("位数: "+body.getProcessorDto().getBitDepth());
        System.out.println("CPU核心: "+body.getProcessorDto().getCoreCount());
        System.out.println("当前频率："+body.getProcessorDto().getCurrentFreq());
        System.out.println("最大频率: "+body.getProcessorDto().getMaxFreq());
        System.out.println("处理器: "+body.getProcessorDto().getName());
    }
    @Test
    public void getComputerSystemInfo(){
        InfoDto body = (InfoDto) infoController.getInfo().getBody();
        System.out.println("制造商: "+body.getComputerSystemDto().getManufacturer());
        System.out.println("产品名称: "+body.getComputerSystemDto().getModel());
        System.out.println("BIOS版本: "+body.getComputerSystemDto().getVersion());
        System.out.println("发布时间: "+body.getComputerSystemDto().getRelease_date());

    }
    @Test
    public void getPowerSourcesInfo(){
        InfoDto body = (InfoDto) infoController.getInfo().getBody();
        System.out.println(body.getPowerSourceList());
    }
    @Test
    public void getStorageInfo(){
        InfoDto body = (InfoDto) infoController.getInfo().getBody();
        System.out.println("磁盘模型: "+body.getStorageDto().getMainStorage());
        System.out.println("磁盘数量: "+body.getStorageDto().getDiskCount());
        System.out.println("磁盘空间: "+body.getStorageDto().getTotal());
    }
}
