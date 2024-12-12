package com.lzmhc.webhtop;

import com.lzmhc.webhtop.controllers.InfoController;
import com.lzmhc.webhtop.dto.InfoDto;
import com.lzmhc.webhtop.dto.StorageDto;
import com.lzmhc.webhtop.services.InfoService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import oshi.hardware.GraphicsCard;

import java.util.List;

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
        System.out.println("物理内存利用率: "+body.getGlobalMemoryDto().getPercentage()+" %");
        System.out.println("虚拟内存: "+body.getGlobalMemoryDto().getVirtuallMemory()+" GB");
        System.out.println("已用虚拟内存: "+body.getGlobalMemoryDto().getVirtualUsedMemory()+" GB");
        System.out.println("内存类型/位: "+body.getGlobalMemoryDto().getRamTypeOrOsBitDepth());
        System.out.println("进程数: "+body.getGlobalMemoryDto().getProcCount());
    }

    @Test
    public void getProcessorInfo(){
        InfoDto body=(InfoDto) infoController.getInfo().getBody();
        System.out.println("位数: "+body.getProcessorDto().getBitDepth());
        System.out.println("CPU核心: "+body.getProcessorDto().getCoreCount());
        System.out.println("当前频率："+body.getProcessorDto().getCurrentFreq());
        System.out.println("最大频率: "+body.getProcessorDto().getMaxFreq());
        System.out.println("处理器: "+body.getProcessorDto().getName());
        System.out.println("CPU温度: "+body.getProcessorDto().getSensoresTemperature());
        System.out.println("CPU电压: "+body.getProcessorDto().getSensorsVoltage());
        System.out.println("风扇速度: "+body.getProcessorDto().getSensoresSpeedList());
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
        System.out.println("电源名称: "+body.getPowerDto().getName());
        System.out.println("设备名称: "+body.getPowerDto().getDeviceName());
        System.out.println("电压: "+body.getPowerDto().getVoltage());
        System.out.println("是否接入电源: "+body.getPowerDto().isPowerOnLine());
        System.out.println("是否在充电: "+body.getPowerDto().isCharging());
        System.out.println("是否放电: "+body.getPowerDto().isDischarging());
        System.out.println("当前容量: "+body.getPowerDto().getCurrentCapacity());
        System.out.println("设计容量: "+body.getPowerDto().getDesignCapacity());
        System.out.println("最大容量: "+body.getPowerDto().getMaxCapacity());
        System.out.println("电池性质: "+body.getPowerDto().getChemistry());
        System.out.println("电池制造商: "+body.getPowerDto().getManufacturer());
    }
    @Test
    public void getStorageInfo(){
        InfoDto body = (InfoDto) infoController.getInfo().getBody();
        List<StorageDto> storageDtoList = body.getStorageDtoList();
        for(StorageDto storageDto:storageDtoList) {
            System.out.println("磁盘模型: " + storageDto.getMainStorage());
            System.out.println("磁盘数量: " + storageDto.getDiskCount());
            System.out.println("磁盘空间: " + storageDto.getTotal());
        }
    }
    @Test
    public void getGraphicsCardsInfo(){
        InfoDto body = (InfoDto) infoController.getInfo().getBody();
        List<GraphicsCard> graphicsCardList = body.getGraphicsCardDto().getGraphicsCardList();
        for( GraphicsCard graphicsCard: graphicsCardList){
            System.out.println("显卡名称: "+graphicsCard.getName());
            System.out.println("供应商: "+graphicsCard.getVendor());
            System.out.println("显存: "+graphicsCard.getVRam()/1024.0/1024.0 +" MB");
        }

    }
}
