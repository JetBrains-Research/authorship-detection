package cn.abovesky.shopping.web.merchant;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.base.BaseController;
import cn.abovesky.shopping.domain.Goods;
import cn.abovesky.shopping.domain.Remark;
import cn.abovesky.shopping.service.IGoodsService;
import cn.abovesky.shopping.service.IRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by snow on 2014/5/1.
 */
@Controller
@RequestMapping("/merchant/remark")
public class MRemarkController extends BaseController {
    @Autowired
    private IRemarkService remarkService;
    @Autowired
    private IGoodsService goodsService;

    @RequestMapping("/list")
    public ModelAndView list(BaseConditionVO vo) {
        ModelAndView modelAndView = new ModelAndView("merchant_system/remark/list");
        vo.setMerchantId(getMerchant().getId());
        List<Remark> remarkList = remarkService.search(vo);
        List<Goods> goodsList = goodsService.findByMerchantId(vo.getMerchantId());
        modelAndView.addObject("goodsList", goodsList);
        modelAndView.addObject("remarkList", remarkList);
        modelAndView.addObject("vo", vo);
        return modelAndView;
    }
}
