package cn.abovesky.shopping.web.management;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.common.enums.GoodsStatus;
import cn.abovesky.shopping.domain.Goods;
import cn.abovesky.shopping.service.IGoodsService;
import cn.abovesky.shopping.service.IGoodsTypeService;
import cn.abovesky.shopping.web.common.ComGoodsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by snow on 2014/5/12.
 */
@Controller
@RequestMapping("/manage/goods")
public class AGoodsController extends ComGoodsController {
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IGoodsTypeService goodsTypeService;

    @RequestMapping("/list")
    public ModelAndView list(BaseConditionVO vo) {
        ModelAndView modelAndView = new ModelAndView("management/goods/list");
        List<Goods> goodsList = goodsService.search(vo);
        modelAndView.addObject("goodsList", goodsList);
        modelAndView.addObject("goodsStatus", GoodsStatus.values());
        modelAndView.addObject("goodsType", goodsTypeService.findAll());
        modelAndView.addObject("vo", vo);
        return modelAndView;
    }

}
