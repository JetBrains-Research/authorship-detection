package cn.abovesky.shopping.service.impl;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.dao.ImageRecommendMapper;
import cn.abovesky.shopping.domain.ImageRecommend;
import cn.abovesky.shopping.exception.ServiceException;
import cn.abovesky.shopping.service.IImageRecommendService;
import cn.abovesky.shopping.util.CompressImage;
import cn.abovesky.shopping.util.FileUtils;
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
 * Created by snow on 2014/5/16.
 */
@Transactional(rollbackFor = Exception.class)
@Service(IImageRecommendService.SERVICE_NAME)
public class ImageRecommendServiceImpl implements IImageRecommendService {
    @Autowired
    private ImageRecommendMapper imageRecommendMapper;

    @Override
    public List<ImageRecommend> search(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<ImageRecommend> imageRecommendList = imageRecommendMapper.findPageBreakByCondition(vo, rb);
        vo.setTotalCount(imageRecommendMapper.getTotalCount(vo));
        return imageRecommendList;
    }

    @Override
    public void add(MultipartFile image, ImageRecommend imageRecommend) throws ServiceException {
        if (!image.isEmpty()) {
            String filename = UUID.randomUUID().toString() + PathUtils.fileSuffix(image.getOriginalFilename());
            try {
                FileUtils.upload(image.getInputStream(), "imageRecommend/" + filename);
            } catch (AuthException e) {
                throw new ServiceException("上传图片失败");
            } catch (JSONException e) {
                throw new ServiceException("上传图片失败");
            } catch (IOException e) {
                throw new ServiceException("上传图片失败");
            }
            imageRecommend.setImage(filename);
        }
        Date now = new Date();
        imageRecommend.setUpdateDate(now);
        imageRecommend.setInsertDate(now);
        imageRecommendMapper.insertSelective(imageRecommend);
    }

    @Override
    public ImageRecommend findById(Integer id) {
        return imageRecommendMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(MultipartFile image, ImageRecommend imageRecommend) throws ServiceException {
        if (!image.isEmpty()) {
            if (!StringUtils.isEmpty(imageRecommend.getOriginalImage())) {
                FileUtils.delete("wmlm", "imageRecommend/" + imageRecommend.getOriginalImage());
            }
            String filename = UUID.randomUUID().toString() + PathUtils.fileSuffix(image.getOriginalFilename());
            try {
                FileUtils.upload(image.getInputStream(), "imageRecommend/" + filename);
            } catch (AuthException e) {
                throw new ServiceException("上传图片失败");
            } catch (JSONException e) {
                throw new ServiceException("上传图片失败");
            } catch (IOException e) {
                throw new ServiceException("上传图片失败");
            }
            imageRecommend.setImage(filename);
        }
        imageRecommend.setUpdateDate(new Date());
        imageRecommendMapper.updateByPrimaryKeySelective(imageRecommend);
    }

    @Override
    public void delete(String[] ids) {
        for (String id : ids) {
            FileUtils.delete("wmlm", "imageRecommend/" + id.split("_")[1]);
            imageRecommendMapper.deleteByPrimaryKey(Integer.valueOf(id.split("_")[0]));
        }
    }

    @Override
    public List<ImageRecommend> findAll() {
        return imageRecommendMapper.findAll();
    }
}
