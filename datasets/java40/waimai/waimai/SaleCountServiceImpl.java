package cn.abovesky.shopping.service.impl;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.dao.SaleCountMapper;
import cn.abovesky.shopping.domain.SaleCount;
import cn.abovesky.shopping.service.ISaleCountService;
import cn.abovesky.shopping.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by snow on 2014/5/7.
 */
@Transactional(rollbackFor = Exception.class)
@Service(ISaleCountService.SERVICE_NAME)
public class SaleCountServiceImpl implements ISaleCountService {
    @Autowired
    private SaleCountMapper saleCountMapper;

    public Map<String, Object> saleCount(BaseConditionVO vo) {
        Map<String, Object> map = this.getSaleCountMap(vo.getStatus());
        vo.setSaleCountDate(DateUtil.getSaleCountDate(vo.getStatus()));
        List<SaleCount> saleCountList = saleCountMapper.saleCount(vo);
        for (SaleCount saleCount : saleCountList) {
            map.put(saleCount.getDate(), !"2".equals(vo.getKeywords()) ? saleCount.getNumber() : saleCount.getMoney());
        }
        return map;
    }

    @Override
    public List<SaleCount> findTopTen(BaseConditionVO vo) {
        vo.setSaleCountDate(DateUtil.getSaleCountDate(vo.getStatus()));
        return saleCountMapper.findTopTen(vo);
    }

    private Map<String, Object> getSaleCountMap(String status) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
        if (status == null || "1".equals(status)) {
            for (int i = 6; i >= 0; i--) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, -i);
                map.put(dateFormat.format(calendar.getTime()), 0);
            }
        } else if ("2".equals(status)) {
            for (int i = 29; i >= 0; i--) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, -i);
                map.put(dateFormat.format(calendar.getTime()), 0);
            }
        } else if ("3".equals(status)) {
            dateFormat = new SimpleDateFormat("yyyy-MM");
            for (int i = 11; i >= 0; i--) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MONTH, -i);
                map.put(dateFormat.format(calendar.getTime()), 0);
            }
        }
        return map;
    }

}
