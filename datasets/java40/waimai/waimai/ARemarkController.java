package cn.abovesky.shopping.web.management;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.base.BaseController;
import cn.abovesky.shopping.domain.Remark;
import cn.abovesky.shopping.exception.ServiceException;
import cn.abovesky.shopping.service.IRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by snow on 2014/5/12.
 */
@Controller
@RequestMapping("/manage/remark")
public class ARemarkController extends BaseController {
    @Autowired
    private IRemarkService remarkService;

    @RequestMapping("/list")
    public ModelAndView list(BaseConditionVO vo) {
        ModelAndView modelAndView = new ModelAndView("management/remark/list");
        List<Remark> remarkList = remarkService.search(vo);
        modelAndView.addObject("remarkList", remarkList);
        modelAndView.addObject("vo", vo);
        return modelAndView;
    }

    @RequestMapping("/delete")
    public ModelAndView delete(String[] ids) {
        try {
            remarkService.delete(ids);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccess("删除成功");
    }
}
