package cn.abovesky.shopping.web.common;

import cn.abovesky.shopping.base.BaseController;
import cn.abovesky.shopping.domain.Goods;
import cn.abovesky.shopping.exception.ServiceException;
import cn.abovesky.shopping.service.IGoodsService;
import cn.abovesky.shopping.service.IGoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by snow on 2014/5/12.
 */
public class ComGoodsController extends BaseController {
    @Autowired
    private IGoodsService goodsService;
    @Autowired
    private IGoodsTypeService goodsTypeService;

    @RequestMapping("/edit/{goods_id}")
    public ModelAndView editView(@PathVariable("goods_id") Integer goodsId) {
        ModelAndView modelAndView = new ModelAndView("common/goods/edit");
        Goods goods = goodsService.findById(goodsId);
        modelAndView.addObject("goods", goods);
        modelAndView.addObject("goodsType", goodsTypeService.findAll());
        return modelAndView;
    }

    @RequestMapping("/edit")
    public ModelAndView edit(@RequestParam(value = "imageFile") MultipartFile image, Goods goods) {
        try {
            goodsService.update(image, goods);
        } catch (ServiceException e) {
            return ajaxDoneError("修改失败");
        }
        return ajaxDoneSuccessAndClose("修改成功", "goods_list");
    }

    @RequestMapping(value = "/delete")
    public ModelAndView delete(String[] ids) {
        goodsService.delete(ids);
        return ajaxDoneSuccess("删除成功");
    }

    @RequestMapping("/up")
    public ModelAndView up(String[] ids) {
        try {
            goodsService.up(ids);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccess("上架成功");
    }

    @RequestMapping("/down")
    public ModelAndView down(String[] ids) {
        try {
            goodsService.down(ids);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccess("下架成功");
    }
}
