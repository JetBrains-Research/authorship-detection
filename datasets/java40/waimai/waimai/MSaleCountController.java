package cn.abovesky.shopping.web.merchant;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.base.BaseController;
import cn.abovesky.shopping.domain.Goods;
import cn.abovesky.shopping.domain.SaleCount;
import cn.abovesky.shopping.service.IGoodsService;
import cn.abovesky.shopping.service.ISaleCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * Created by snow on 2014/5/6.
 */
@Controller
@RequestMapping("/merchant/saleCount")
public class MSaleCountController extends BaseController {
    @Autowired
    private ISaleCountService saleCountService;
    @Autowired
    private IGoodsService goodsService;

    @RequestMapping("/list")
    public ModelAndView list(@ModelAttribute("vo") BaseConditionVO vo) {
        ModelAndView modelAndView = new ModelAndView("merchant_system/saleCount/list");
        vo.setMerchantId(getMerchant().getId());
        Map<String, Object> saleCountMap = saleCountService.saleCount(vo);
        List<Goods> goodsList = goodsService.findByMerchantId(getMerchant().getId());
        if (!"2".equals(vo.getKeywords())) {
            List<SaleCount> topTenList = saleCountService.findTopTen(vo);
            modelAndView.addObject("topTenList", topTenList);
        }
        modelAndView.addObject("saleCountMap", saleCountMap);
        modelAndView.addObject("goodsList", goodsList);
        return modelAndView;
    }

}
