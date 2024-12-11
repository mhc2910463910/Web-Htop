package com.lzmhc.webhtop.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProcessorDto {
        private String name;
        /**
         * 名称
         */
        private int coreCount;
        /**
         * 可用于处理的逻辑CPU核心数
         */
        private String maxFreq;
        /**
         * 最大频率
         */
        private String currentFreq;
        /**
         * 当前频率
         */
        private String bitDepth;
        /**
         * 位
         */
}
