package cn.abovesky.shopping.service.impl;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.dao.MerchantCollectionMapper;
import cn.abovesky.shopping.dao.MerchantMapper;
import cn.abovesky.shopping.domain.MerchantCollection;
import cn.abovesky.shopping.exception.ServiceException;
import cn.abovesky.shopping.service.IMerchantCollectionService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by snow on 2014/5/3.
 */
@Transactional(rollbackFor = Exception.class)
@Service(IMerchantCollectionService.SERVICE_NAME)
public class MerchantCollectionServiceImpl implements IMerchantCollectionService {
    @Autowired
    private MerchantCollectionMapper merchantCollectionMapper;
    @Autowired
    private MerchantMapper merchantMapper;

    @Override
    public void add(MerchantCollection merchantCollection) throws ServiceException {
        if (merchantCollectionMapper.countByUserIdAndMerchantId(merchantCollection) > 0) {
            throw new ServiceException("收藏一次就好");
        } else {
            merchantCollectionMapper.insert(merchantCollection);
            merchantMapper.addCollectionCount(merchantCollection.getMerchantId());
        }
    }

    @Override
    public void cancel(MerchantCollection merchantCollection) throws ServiceException {
        if (merchantCollectionMapper.cancel(merchantCollection) > 0) {
            merchantMapper.subCollectionCount(merchantCollection.getMerchantId());
        } else {
            throw new ServiceException("没有收藏该店铺");
        }
    }

    @Override
    public List<MerchantCollection> search(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<MerchantCollection> merchantCollectionList = merchantCollectionMapper.findPageBreakByCondition(vo, rb);
        vo.setTotalCount(merchantCollectionMapper.getTotalCount(vo));
        return merchantCollectionList;
    }

    @Override
    public boolean isExist(MerchantCollection merchantCollection) {
        return merchantCollectionMapper.countByUserIdAndMerchantId(merchantCollection) > 0 ? true : false;
    }
}
