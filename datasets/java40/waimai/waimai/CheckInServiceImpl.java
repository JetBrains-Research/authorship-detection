package cn.abovesky.shopping.service.impl;

import cn.abovesky.shopping.common.constant.Constants;
import cn.abovesky.shopping.dao.CheckInMapper;
import cn.abovesky.shopping.dao.CreditLogMapper;
import cn.abovesky.shopping.dao.UserMapper;
import cn.abovesky.shopping.domain.CheckIn;
import cn.abovesky.shopping.domain.CreditLog;
import cn.abovesky.shopping.exception.ServiceException;
import cn.abovesky.shopping.service.ICheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by snow on 2014/5/2.
 */
@Transactional(rollbackFor = Exception.class)
@Service(ICheckInService.SERVICE_NAME)
public class CheckInServiceImpl implements ICheckInService {
    @Autowired
    private CheckInMapper checkInMapper;
    @Autowired
    private CreditLogMapper creditLogMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public void add(CheckIn checkIn) throws ServiceException {
        Date now = new Date();
        if (checkInMapper.countTodayByUserId(checkIn.getUserId(), now) > 0) {
            throw new ServiceException("一天一签到就好");
        } else {
            checkIn.setDate(now);
            checkInMapper.insert(checkIn);

            CreditLog creditLog = new CreditLog();
            creditLog.setDate(checkIn.getDate());
            creditLog.setUserId(checkIn.getUserId());
            creditLog.setNumber(Constants.CHECKIN_CREDIT);
            creditLog.setDescription(Constants.CREDITLOG_CHECKIN);
            creditLogMapper.insert(creditLog);

            userMapper.addCredit(creditLog.getUserId(), creditLog.getNumber());
        }
    }
}
