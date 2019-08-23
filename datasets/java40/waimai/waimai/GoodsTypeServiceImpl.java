package cn.abovesky.shopping.service.impl;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.common.enums.GoodsTypeStatus;
import cn.abovesky.shopping.dao.GoodsTypeMapper;
import cn.abovesky.shopping.domain.GoodsType;
import cn.abovesky.shopping.exception.ServiceException;
import cn.abovesky.shopping.service.IGoodsTypeService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.management.ServiceNotFoundException;
import java.util.List;

/**
 * Created by snow on 2014/4/24.
 */
@Transactional(rollbackFor = Exception.class)
@Service(IGoodsTypeService.SERVICE_NAME)
public class GoodsTypeServiceImpl implements IGoodsTypeService {
    @Autowired
    private GoodsTypeMapper goodsTypeMapper;

    @Override
    public List<GoodsType> findAll() {
        return goodsTypeMapper.findAll();
    }

    @Override
    public void add(GoodsType goodsType) throws ServiceException {
        if (goodsTypeMapper.countByName(goodsType.getName()) < 1) {
            goodsType.setStatus(GoodsTypeStatus.ACTIVE.toString());
            goodsTypeMapper.insert(goodsType);
        } else {
            throw new ServiceException("该类别已存在");
        }
    }

    @Override
    public List<GoodsType> findByMerchantId(Integer merchantId) {
        return goodsTypeMapper.findByMerchantId(merchantId);
    }

    @Override
    public List<GoodsType> search(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<GoodsType> goodsTypeList = goodsTypeMapper.findPageBreakByCondition(vo, rb);
        vo.setTotalCount(goodsTypeMapper.getTotalCount(vo));
        return goodsTypeList;
    }

    @Override
    public GoodsType findById(Integer id) {
        return goodsTypeMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(GoodsType goodsType) throws ServiceException {
        if (goodsTypeMapper.countByName(goodsType.getName()) < 1) {
            if (goodsTypeMapper.updateByPrimaryKeySelective(goodsType) < 1) {
                throw new ServiceException("修改失败");
            }
        } else {
            throw new ServiceException("该类别已存在");
        }
    }

    @Override
    public void delete(Integer[] ids) throws ServiceException {
        if (goodsTypeMapper.delete(ids) < 1) {
            throw new ServiceException("删除失败");
        }
    }

}
