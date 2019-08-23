package cn.abovesky.shopping.service.impl;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.dao.CreditLogMapper;
import cn.abovesky.shopping.domain.CreditLog;
import cn.abovesky.shopping.service.ICreditLogService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by snow on 2014/5/2.
 */
@Transactional(rollbackFor = Exception.class)
@Service(ICreditLogService.SERVICE_NAME)
public class CreditLogServiceImpl implements ICreditLogService {
    @Autowired
    private CreditLogMapper creditLogMapper;

    @Override
    public List<CreditLog> search(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<CreditLog> creditLogList = creditLogMapper.findPageBreakByCondition(vo, rb);
        vo.setTotalCount(creditLogMapper.getTotalCount(vo));
        return creditLogList;
    }
}
