package com.practica.demo.service;

import com.practica.demo.db.dao.BlockRepository;
import com.practica.demo.db.dao.UserRepository;
import com.practica.demo.db.entity.Block;
import com.practica.demo.model.BlockModel;
import com.practica.demo.model.UserModel;
import com.practica.demo.model.exception.InvalidArgumentsException;
import com.practica.demo.model.exception.ValueNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.List;

@Service
public class BlockService {


    @Autowired
    UserManagementService userManagementService;

    @Autowired
    SessionService sessionService;

    @Autowired
    BlockRepository blockRepository;

    @Autowired
    UserRepository userRepository;


    public void blockUnblock(Integer userId, boolean blocked) throws InvalidArgumentsException, ValueNotFoundException {
        if(userId==null ){
            throw new InvalidArgumentsException("Invalid credentials");
        }
        UserModel userModel = userManagementService.getUserById(userId);
        if(userModel==null){
            throw new ValueNotFoundException("No data found");
        }
        if(blocked==true){
            Block block=new Block(userRepository.findUserById(sessionService.getSessionModel().getUserId()),userId);
            blockRepository.save(block);
        }
        if(blocked==false){
            if(blockRepository.getBlockByIds(sessionService.getSessionModel().getUserId(),userId)==null){
                throw new ValueNotFoundException("Block not found");
            }
            else{
                blockRepository.delete(blockRepository.getBlockByIds(sessionService.getSessionModel().getUserId(),userId));
            }

        }

    }

    public List<BlockModel> getBlockList() {
        List<BlockModel> blockList=blockRepository.getBlockList(sessionService.getSessionModel().getUserId());

        return blockList;
    }
}
