package cn.abovesky.shopping.dao;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.base.BaseMapper;
import cn.abovesky.shopping.domain.Goods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface GoodsMapper extends BaseMapper<Goods, Integer> {
    // 查询
    List<Goods> findPageBreakByCondition(BaseConditionVO vo, RowBounds rb);

    void deleteByIds(Integer[] ids);

    void up(Integer[] ids);
    void down(Integer[] ids);
    Goods findNameAndNumberById(Integer id);

    void addSaleCount(@Param("id") Integer id, @Param("saleCount") Integer saleCount);

    void addNumber(@Param("id") Integer id, @Param("number") Integer number);

    void addRemarkCount(Integer id);

    List<Goods> findByMerchantId(Integer merchantId);

    Goods getDetailById(Integer id);

    void addCollectionCount(Integer id);

    void subCollectionCount(Integer id);

    void subRemarkCount(Integer id);

    List<Goods> findAll();

    int getTotalCount(BaseConditionVO vo);
}