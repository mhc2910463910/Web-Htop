package com.lzmhc.webhtop.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProcessorDto {
        private String name;
//        名称
        private String coreCount;
//        核心数
        private String clockSpeed;
//        时钟速度
        private String bitDepth;
//        位
}
