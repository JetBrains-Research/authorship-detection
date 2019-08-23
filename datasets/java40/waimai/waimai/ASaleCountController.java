package cn.abovesky.shopping.web.management;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.base.BaseController;
import cn.abovesky.shopping.domain.Goods;
import cn.abovesky.shopping.domain.Merchant;
import cn.abovesky.shopping.domain.SaleCount;
import cn.abovesky.shopping.service.IGoodsService;
import cn.abovesky.shopping.service.IMerchantService;
import cn.abovesky.shopping.service.ISaleCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by snow on 2014/5/12.
 */
@Controller
@RequestMapping("/manage/saleCount")
public class ASaleCountController extends BaseController {
    @Autowired
    private ISaleCountService saleCountService;
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IMerchantService merchantService;

    @RequestMapping("/list")
    public ModelAndView list(@ModelAttribute("vo") BaseConditionVO vo) {
        ModelAndView modelAndView = new ModelAndView("management/saleCount/list");
        Map<String, Object> saleCountMap = saleCountService.saleCount(vo);
        List<Goods> goodsList;
        if (vo.getMerchantId() != null && vo.getMerchantId() != 0) {
            goodsList = goodsService.findByMerchantId(vo.getMerchantId());
        } else {
            goodsList = goodsService.findAll();
        }
        List<Merchant> merchantList = merchantService.findAll();
        if (!"2".equals(vo.getKeywords())) {
            List<SaleCount> topTenList = saleCountService.findTopTen(vo);
            modelAndView.addObject("topTenList", topTenList);
        }
        modelAndView.addObject("saleCountMap", saleCountMap);
        modelAndView.addObject("goodsList", goodsList);
        modelAndView.addObject("merchantList", merchantList);
        return modelAndView;
    }

    @RequestMapping("/type")
    public ModelAndView getType(Integer merchantId) {
        ModelAndView modelAndView = new ModelAndView("management/saleCount/type");
        List<Goods> goodsList;
        if (merchantId != null && merchantId != 0) {
            goodsList = goodsService.findByMerchantId(merchantId);
        } else {
            goodsList = goodsService.findAll();
        }
        modelAndView.addObject("goodsList", goodsList);
        return modelAndView;
    }
}
