package cn.abovesky.shopping.web.management;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.base.BaseController;
import cn.abovesky.shopping.domain.GoodsType;
import cn.abovesky.shopping.exception.ServiceException;
import cn.abovesky.shopping.service.IGoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by snow on 2014/5/17.
 */
@Controller
@RequestMapping("/manage/goodsType")
public class AGoodsTypeController extends BaseController {
    @Autowired
    private IGoodsTypeService goodsTypeService;

    @RequestMapping("/list")
    public ModelAndView list(@ModelAttribute("vo")BaseConditionVO vo) {
        ModelAndView modelAndView = new ModelAndView("management/goodsType/list");
        List<GoodsType> goodsTypeList = goodsTypeService.search(vo);
        modelAndView.addObject("goodsTypeList", goodsTypeList);
        return modelAndView;
    }

    @RequestMapping("/addView")
    public String addView() {
        return "management/goodsType/add";
    }

    @RequestMapping("/add")
    public ModelAndView add(GoodsType goodsType) {
        try {
            goodsTypeService.add(goodsType);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccessAndClose("添加成功", "goodsType_list");
    }

    @RequestMapping("/edit/{item_id}")
    public ModelAndView editView(@PathVariable("item_id") Integer id){
        ModelAndView modelAndView = new ModelAndView("management/goodsType/edit");
        modelAndView.addObject("goodsType", goodsTypeService.findById(id));
        return modelAndView;
    }

    @RequestMapping("/update")
    public ModelAndView update(GoodsType goodsType) {
        try {
            goodsTypeService.update(goodsType);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccessAndClose("修改成功", "goodsType_list");
    }

    @RequestMapping("/delete")
    public ModelAndView delete(Integer[] ids) {
        try {
            goodsTypeService.delete(ids);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccess("删除成功");
    }
}
