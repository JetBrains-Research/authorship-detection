package cn.abovesky.shopping.web.merchant;

import cn.abovesky.shopping.base.BaseController;
import cn.abovesky.shopping.domain.GoodsType;
import cn.abovesky.shopping.exception.ServiceException;
import cn.abovesky.shopping.service.IGoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by snow on 2014/4/24.
 */
@Controller
@RequestMapping("/merchant/goodsType")
public class GoodsTypeController extends BaseController {
    @Autowired
    private IGoodsTypeService goodsTypeService;

    @RequestMapping("/add")
    public ModelAndView add(GoodsType goodsType) {
        try {
            goodsTypeService.add(goodsType);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccessAndClose("", "");
    }

    @RequestMapping("/showList")
    public ModelAndView showListByJson(String ids) {
        ModelAndView modelAndView = new ModelAndView("merchant_system/goodsType/select");
        List<GoodsType> goodsTypeList = goodsTypeService.findAll();
        modelAndView.addObject("goodsTypeList", goodsTypeList);
        modelAndView.addObject("ids", ids);
        return modelAndView;
    }
}
