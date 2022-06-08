package com.reddate.chinatrust.vo.request;

import java.io.Serializable;


public class ChangeHolderVo extends BaseVo implements Serializable {

    private String tokenId;
    private String newHolder;
    private String privateKey;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }


    public String getNewHolder() {
        return newHolder;
    }

    public void setNewHolder(String newHolder) {
        this.newHolder = newHolder;
    }
}
