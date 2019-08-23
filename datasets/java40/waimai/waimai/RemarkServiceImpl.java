package cn.abovesky.shopping.service.impl;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.dao.GoodsMapper;
import cn.abovesky.shopping.dao.RemarkMapper;
import cn.abovesky.shopping.domain.Remark;
import cn.abovesky.shopping.exception.ServiceException;
import cn.abovesky.shopping.service.IRemarkService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by snow on 2014/5/1.
 */
@Transactional(rollbackFor = Exception.class)
@Service(IRemarkService.SERVICE_NAME)
public class RemarkServiceImpl implements IRemarkService {
    @Autowired
    private RemarkMapper remarkMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public List<Remark> search(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<Remark> remarkList = remarkMapper.findPageBreakByCondition(vo, rb);
        vo.setTotalCount(remarkMapper.getTotalCount(vo));
        return remarkList;
    }

    @Override
    public void delete(String[] ids) throws ServiceException {
        for (String id : ids) {
            if (remarkMapper.deleteByPrimaryKey(Integer.valueOf(id.split("_")[0])) > 0) {
                goodsMapper.subRemarkCount(Integer.valueOf(id.split("_")[1]));
            } else {
                throw new ServiceException("删除失败");
            }
        }
    }

}
