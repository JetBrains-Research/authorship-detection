package cn.abovesky.shopping.service.impl;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.dao.GoodsCollectionMapper;
import cn.abovesky.shopping.dao.GoodsMapper;
import cn.abovesky.shopping.domain.GoodsCollection;
import cn.abovesky.shopping.exception.ServiceException;
import cn.abovesky.shopping.service.IGoodsCollectionService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by snow on 2014/5/3.
 */
@Transactional(rollbackFor = Exception.class)
@Service(IGoodsCollectionService.SERVICE_NAME)
public class GoodsCollectionServiceImpl implements IGoodsCollectionService {
    @Autowired
    private GoodsCollectionMapper goodsCollectionMapper;
    @Autowired
    private GoodsMapper goodsMapper;

    @Override
    public void add(GoodsCollection goodsCollection) throws ServiceException {
        if (goodsCollectionMapper.countByUserIdAndGoodsId(goodsCollection) > 0) {
            throw new ServiceException("收藏一次就好");
        } else {
            goodsCollectionMapper.insert(goodsCollection);
            goodsMapper.addCollectionCount(goodsCollection.getGoodsId());
        }
    }

    @Override
    public void cancel(GoodsCollection goodsCollection) throws ServiceException {
        if (goodsCollectionMapper.cancel(goodsCollection) > 0) {
            goodsMapper.subCollectionCount(goodsCollection.getGoodsId());
        } else {
            throw new ServiceException("没有收藏该菜品");
        }
    }

    @Override
    public List<GoodsCollection> search(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<GoodsCollection> goodsCollectionList = goodsCollectionMapper.findPageBreakByCondition(vo, rb);
        vo.setTotalCount(goodsCollectionMapper.getTotalCount(vo));
        return goodsCollectionList;
    }

    @Override
    public boolean isExist(GoodsCollection goodsCollection) {
        return goodsCollectionMapper.countByUserIdAndGoodsId(goodsCollection) > 0 ? true : false;
    }
}
