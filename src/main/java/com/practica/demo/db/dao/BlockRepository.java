package com.practica.demo.db.dao;

import com.practica.demo.db.entity.Block;
import com.practica.demo.model.BlockModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BlockRepository extends JpaRepository<Block,Integer> {

    @Query("SELECT b FROM Block b WHERE b.user.id=:userId AND b.blockedId=:blockedId")
    Block getBlockByIds(@Param("userId")Integer userId, @Param("blockedId") Integer blockedId);

    @Query("SELECT new com.practica.demo.model.BlockModel(b.blockId,b.user.id, b.blockedId) FROM Block b WHERE b.blockId=:userId")
    List<BlockModel> getBlockList(@Param("userId")Integer userId);
}
