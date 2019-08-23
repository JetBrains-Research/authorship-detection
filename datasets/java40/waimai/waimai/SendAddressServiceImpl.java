package cn.abovesky.shopping.service.impl;

import cn.abovesky.shopping.dao.SendAddressMapper;
import cn.abovesky.shopping.domain.SendAddress;
import cn.abovesky.shopping.service.ISendAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by snow on 2014/4/29.
 */
@Transactional(rollbackFor = Exception.class)
@Service(ISendAddressService.SERVICE_NAME)
public class SendAddressServiceImpl implements ISendAddressService {
    @Autowired
    private SendAddressMapper sendAddressMapper;

    @Override
    public void add(Integer userId, String address) {
        SendAddress sendAddress = new SendAddress();
        sendAddress.setUserId(userId);
        sendAddress.setAddress(address);
        if (sendAddressMapper.countByNameAndAddress(sendAddress) < 1) {
            sendAddressMapper.insert(sendAddress);
        }
    }

    @Override
    public List<SendAddress> findById(Integer userId) {
        return sendAddressMapper.findById(userId);
    }
}
