package cn.abovesky.shopping.service.impl;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.dao.SaleRecordMapper;
import cn.abovesky.shopping.domain.Goods;
import cn.abovesky.shopping.service.ISaleRecordService;
import cn.abovesky.shopping.util.DateUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by snow on 2014/5/8.
 */
@Transactional(rollbackFor = Exception.class)
@Service(ISaleRecordService.SERVICE_NAME)
public class SaleRecordServiceImpl implements ISaleRecordService {
    @Autowired
    private SaleRecordMapper saleRecordMapper;

    @Override
    public Float countTotalPrice(BaseConditionVO vo, String status) {
        vo.setSaleCountDate(DateUtil.getSaleCountDate(status));
        return saleRecordMapper.countTotalPrice(vo) == null ? 0 : saleRecordMapper.countTotalPrice(vo);
    }

    @Override
    public Integer countNumber(BaseConditionVO vo, String status) {
        vo.setSaleCountDate(DateUtil.getSaleCountDate(status));
        return saleRecordMapper.countNumber(vo) == null ? 0 : saleRecordMapper.countNumber(vo);
    }

    @Override
    public List<Goods> getGoodsRecommend(Integer size) {
        RowBounds rowBounds = new RowBounds(0, size);
        return saleRecordMapper.getGoodsRecommend(DateUtil.getSaleCountDate("3"), rowBounds);
    }

}
