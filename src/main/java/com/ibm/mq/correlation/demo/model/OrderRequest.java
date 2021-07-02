package com.ibm.mq.correlation.demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderRequest {

    private String message;
    private String identifier;

}
