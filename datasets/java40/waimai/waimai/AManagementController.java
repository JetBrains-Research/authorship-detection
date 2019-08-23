package cn.abovesky.shopping.web.management;

import cn.abovesky.shopping.base.BaseController;
import cn.abovesky.shopping.exception.ServiceException;
import cn.abovesky.shopping.service.IAdminService;
import cn.abovesky.shopping.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by snow on 2014/5/11.
 */
@Controller
@RequestMapping("/manage")
public class AManagementController extends BaseController {
    @Autowired
    private INewsService newsService;
    @Autowired
    private IAdminService adminService;

    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("management/index");
        modelAndView.addObject("news", newsService.findById(1));
        return modelAndView;
    }

    @RequestMapping("/changePwdView")
    public String changePwdView() {
        return "management/admin/changePwd";
    }

    @RequestMapping("/changePwd")
    public ModelAndView changePwd(String oldPassword, String newPassword) {
        try {
            adminService.changePwd(this.getAdmin().getId(), oldPassword, newPassword);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccessAndClose("修改成功", "");
    }
}
