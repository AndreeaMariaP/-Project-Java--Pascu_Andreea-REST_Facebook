package com.practica.demo.db.entity;

import javax.persistence.*;

@Entity
@Table(name = "block", schema = "public")
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "block_id")
    private Integer blockId;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column (name = "blocked_id")
    private Integer blockedId;


    public Block() {
    }

    public Block(User user, Integer blockedId) {
        this.user = user;
        this.blockedId = blockedId;

    }

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getBlockedId() {
        return blockedId;
    }

    public void setBlockedId(Integer blockedId) {
        this.blockedId = blockedId;
    }

}
