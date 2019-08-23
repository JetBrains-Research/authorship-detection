package cn.abovesky.shopping.service;

import cn.abovesky.shopping.domain.Merchant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by snow on 2014/4/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring.xml", "classpath:spring-mybatis.xml"})
public class TestService {
    @Autowired
    private IMerchantService merchantService;

    @Test
    public void testInsertMerchant() throws Exception {
        Merchant merchant = new Merchant();
        merchant.setUsername("merchant1");
        merchant.setStoreName("阿里郎台式餐厅");
        merchant.setPassword("123456");
        merchant.setAddress("西华大学侧门");
        merchant.setPhone("18202877930");
        merchant.setDescription("台式快餐");
        merchantService.insertMerchant(merchant);
    }

    @Test
    public void testFloat2Integer() throws Exception {
        System.out.println(new BigDecimal("45.5").setScale(0, BigDecimal.ROUND_HALF_UP).toString());
    }

    @Test
    public void testDateFormat() throws Exception {
        //System.out.println(new Date(1398773727000l).toString());
        //System.out.println(new SimpleDateFormat("yyyy-MM-dd mm:HH:ss").format(1398773727000l));
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 0);
//        calendar.set(Calendar.MINUTE, 0);
//        calendar.set(Calendar.SECOND, 0);
//        System.out.println(calendar.getTime());
//        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
//        System.out.println(format.format(new Date()));
    }
}
