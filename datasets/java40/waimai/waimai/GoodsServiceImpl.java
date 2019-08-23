package cn.abovesky.shopping.service.impl;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.common.enums.GoodsStatus;
import cn.abovesky.shopping.dao.GoodsMapper;
import cn.abovesky.shopping.domain.Goods;
import cn.abovesky.shopping.exception.ServiceException;
import cn.abovesky.shopping.service.IGoodsService;
import cn.abovesky.shopping.util.CompressImage;
import cn.abovesky.shopping.util.FileUtils;
import cn.abovesky.shopping.util.IdStatusSplitUtils;
import cn.abovesky.shopping.util.PathUtils;
import com.qiniu.api.auth.AuthException;
import org.apache.ibatis.session.RowBounds;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by snow on 2014/4/22.
 */
@Transactional(rollbackFor = Exception.class)
@Service(IGoodsService.SERVICE_NAEM)
public class GoodsServiceImpl implements IGoodsService {
    @Autowired
    private GoodsMapper goodsMapper;

    public List<Goods> search(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<Goods> goodsList = goodsMapper.findPageBreakByCondition(vo, rb);
        vo.setTotalCount(goodsMapper.getTotalCount(vo));
        return goodsList;
    }

    @Override
    public void add(MultipartFile image, Goods goods) throws ServiceException {
        if (!image.isEmpty()) {
            String filename = UUID.randomUUID().toString() + PathUtils.fileSuffix(image.getOriginalFilename());
            try {
                FileUtils.upload(image.getInputStream(), "goodsImage/" + filename);
            } catch (AuthException e) {
                throw new ServiceException("上传图片失败");
            } catch (JSONException e) {
                throw new ServiceException("上传图片失败");
            } catch (IOException e) {
                throw new ServiceException("上传图片失败");
            }
            goods.setImage(filename);
        }
        Date now = new Date();
        goods.setStatus(GoodsStatus.ACTIVE.toString());
        goods.setInsertDate(now);
        goods.setUpdateDate(now);
        goods.setRemarkCount(0);
        goods.setSaleCount(0);
        goods.getName().replace('_', '-');
        goods.setCollectionCount(0);
        goodsMapper.insert(goods);
    }

    @Override
    public Goods findById(Integer goodsId) {
        return goodsMapper.selectByPrimaryKey(goodsId);
    }

    @Override
    public void update(MultipartFile image, Goods goods) throws ServiceException {
        if (!image.isEmpty()) {
            if (!StringUtils.isEmpty(goods.getOriginalImage())) {
                FileUtils.delete("wmlm", "goodsImage/" + goods.getOriginalImage());
            }
            String filename = UUID.randomUUID().toString() + PathUtils.fileSuffix(image.getOriginalFilename());
            try {
                FileUtils.upload(image.getInputStream(), "goodsImage/" + filename);
            } catch (AuthException e) {
                throw new ServiceException("上传图片失败");
            } catch (JSONException e) {
                throw new ServiceException("上传图片失败");
            } catch (IOException e) {
                throw new ServiceException("上传图片失败");
            }
            goods.setImage(filename);
        }
        goods.getName().replace('_', '-');
        goods.setUpdateDate(new Date());
        goodsMapper.updateByPrimaryKeySelective(goods);
    }

    @Override
    public void delete(String[] idsAndStatuses) {
        goodsMapper.deleteByIds(IdStatusSplitUtils.split2Id(idsAndStatuses));
    }

    @Override
    public void up(String[] idsAndStatuses) throws ServiceException {
        if (IdStatusSplitUtils.isFormatSecret(idsAndStatuses, GoodsStatus.INACTIVE.toString())) {
            goodsMapper.up(IdStatusSplitUtils.split2Id(idsAndStatuses));
        } else {
            throw new ServiceException("只能上架下架的菜品");
        }
    }

    @Override
    public void down(String[] idsAndStatuses) throws ServiceException {
        if (IdStatusSplitUtils.isFormatSecret(idsAndStatuses, GoodsStatus.ACTIVE.toString())) {
            goodsMapper.down(IdStatusSplitUtils.split2Id(idsAndStatuses));
        } else {
            throw new ServiceException("只能下架上架的菜品");
        }
    }

    @Override
    public List<Goods> findByMerchantId(Integer merchantId) {
        return goodsMapper.findByMerchantId(merchantId);
    }

    @Override
    public Goods getDetailById(Integer goodsId) {
        return goodsMapper.getDetailById(goodsId);
    }

    @Override
    public List<Goods> findAll() {
        return goodsMapper.findAll();
    }

}
