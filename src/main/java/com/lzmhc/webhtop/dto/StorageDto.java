package com.lzmhc.webhtop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StorageDto {
    private String mainStorage;
    /**
     *
     */
    private String total;
    private String diskCount;
    private String swapAmount;
}
