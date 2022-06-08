package com.reddate.chinatrust.vo.request;

import java.io.Serializable;

public class BaseVo implements Serializable {

    private String contractAddress;

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }
}
