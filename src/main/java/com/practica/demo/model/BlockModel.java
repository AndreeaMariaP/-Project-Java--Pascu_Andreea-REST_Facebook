package com.practica.demo.model;

public class BlockModel {

    private Integer blockId;
    private Integer userId;
    private Integer blockedId;

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBlockedId() {
        return blockedId;
    }

    public void setBlockedId(Integer blockedId) {
        this.blockedId = blockedId;
    }


    public BlockModel(Integer blockId, Integer userId, Integer blockedId) {

        this.blockId = blockId;
        this.userId = userId;
        this.blockedId = blockedId;
    }

    public BlockModel() {

    }
}
