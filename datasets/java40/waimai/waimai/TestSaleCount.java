package cn.abovesky.shopping.service;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.domain.Merchant;
import cn.abovesky.shopping.domain.SaleCount;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

/**
 * Created by snow on 2014/5/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mybatis.xml"})
public class TestSaleCount {
    @Autowired
    private ISaleCountService saleCountService;

    @Test
    public void testDate() throws Exception {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd");
//        for (int i = 6; i >= 0; i--) {
//            Calendar calendar = Calendar.getInstance();
//            calendar.add(Calendar.DAY_OF_YEAR, -i);
//            System.out.println(dateFormat.format(calendar.getTime()));
//        }
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
        calendar.add(Calendar.MONTH, -11);
        System.out.println(dateFormat.format(calendar.getTime()));
    }

    @Test
    public void testSaleCount() throws Exception {
        BaseConditionVO vo = new BaseConditionVO();
        vo.setMerchantId(4);
        vo.setStatus("2");
        Map<String, Object> map = saleCountService.saleCount(vo);
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }
}
