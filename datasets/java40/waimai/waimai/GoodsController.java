package cn.abovesky.shopping.web.merchant;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.common.enums.GoodsStatus;
import cn.abovesky.shopping.domain.Goods;
import cn.abovesky.shopping.exception.ServiceException;
import cn.abovesky.shopping.service.IGoodsService;
import cn.abovesky.shopping.service.IGoodsTypeService;
import cn.abovesky.shopping.web.common.ComGoodsController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by snow on 2014/4/22.
 */
@Controller
@RequestMapping("/merchant/goods")
public class GoodsController extends ComGoodsController {
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IGoodsTypeService goodsTypeService;

    @RequestMapping("/list")
    public ModelAndView list(BaseConditionVO vo) {
        ModelAndView modelAndView = new ModelAndView("merchant_system/goods/list");
        vo.setMerchantId(getMerchant().getId());
        List<Goods> goodsList = goodsService.search(vo);
        modelAndView.addObject("goodsList", goodsList);
        modelAndView.addObject("goodsStatus", GoodsStatus.values());
        modelAndView.addObject("goodsType", goodsTypeService.findAll());
        modelAndView.addObject("vo", vo);
        return modelAndView;
    }

    @RequestMapping("/add")
    public ModelAndView add(@RequestParam(value = "imageFile") MultipartFile image, Goods goods) {
        try {
            goods.setMerchantId(getMerchant().getId());
            goodsService.add(image, goods);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccessAndClose("发布菜品成功", "goods_list");
    }

    @RequestMapping("/addView")
    public ModelAndView addView() {
        ModelAndView modelAndView = new ModelAndView("merchant_system/goods/add");
        modelAndView.addObject("goodsType", goodsTypeService.findAll());
        return modelAndView;
    }

}
