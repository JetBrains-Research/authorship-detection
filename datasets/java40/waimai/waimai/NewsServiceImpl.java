package cn.abovesky.shopping.service.impl;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.dao.NewsMapper;
import cn.abovesky.shopping.domain.News;
import cn.abovesky.shopping.exception.ServiceException;
import cn.abovesky.shopping.service.INewsService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by snow on 2014/5/16.
 */
@Transactional(rollbackFor = Exception.class)
@Service(INewsService.SERVICE_NAME)
public class NewsServiceImpl implements INewsService {
    @Autowired
    private NewsMapper newsMapper;

    @Override
    public List<News> search(BaseConditionVO vo) {
        RowBounds rb = new RowBounds(vo.getStartIndex(), vo.getPageSize());
        List<News> newsList = newsMapper.findPageBreakByCondition(vo, rb);
        vo.setTotalCount(newsMapper.getTotalCount(vo));
        return newsList;
    }

    @Override
    public void add(News news) throws ServiceException {
        Date now = new Date();
        news.setInsertDate(now);
        news.setUpdateDate(now);
        if (newsMapper.insertSelective(news) < 1) {
            throw new ServiceException("添加失败");
        }
    }

    @Override
    public News findById(Integer id) {
        return newsMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(News news) throws ServiceException {
        news.setUpdateDate(new Date());
        if (newsMapper.updateByPrimaryKeySelective(news) < 1) {
            throw new ServiceException("修改失败");
        }
    }

    @Override
    public void delete(Integer[] ids) throws ServiceException {
        if (newsMapper.delete(ids) < 0) {
            throw new ServiceException("删除失败");
        }
    }

    @Override
    public int getTotalCount() {
        return newsMapper.getTotalCount(null);
    }
}
