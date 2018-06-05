package com.practica.demo.web;

import com.practica.demo.db.entity.Block;
import com.practica.demo.model.BlockModel;
import com.practica.demo.model.exception.InvalidArgumentsException;
import com.practica.demo.model.exception.ValueNotFoundException;
import com.practica.demo.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest")
public class BlockController {

    @Autowired
    BlockService blockService;

    @PostMapping("/users/{userId}/block")
    public void BlockUnblock(@RequestBody boolean blocked, @PathVariable("userId")Integer userId) throws ValueNotFoundException, InvalidArgumentsException {
        blockService.blockUnblock(userId,blocked);
    }

    @GetMapping("/my-user/blocked")
    public List<BlockModel> getBlockList(){
       return  blockService.getBlockList();
    }


}
