package cn.abovesky.shopping.web.management;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.base.BaseController;
import cn.abovesky.shopping.common.enums.AdminStatus;
import cn.abovesky.shopping.common.enums.Gender;
import cn.abovesky.shopping.domain.Admin;
import cn.abovesky.shopping.exception.ServiceException;
import cn.abovesky.shopping.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by snow on 2014/5/14.
 */
@Controller
@RequestMapping("/manage/admin")
public class AdminController extends BaseController{
    @Autowired
    private IAdminService adminService;

    @RequestMapping("/list")
    public ModelAndView list(@ModelAttribute("vo") BaseConditionVO vo) {
        ModelAndView modelAndView = new ModelAndView("management/admin/list");
        List<Admin> adminList = adminService.search(vo);
        modelAndView.addObject("adminList", adminList);
        modelAndView.addObject("gender", Gender.values());
        modelAndView.addObject("adminStatus", AdminStatus.values());
        return modelAndView;
    }

    @RequestMapping("/edit/{admin_id}")
    public ModelAndView editView(@PathVariable("admin_id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("management/admin/edit");
        Admin admin = adminService.findById(id);
        admin.setPassword(null);
        modelAndView.addObject("admin", admin);
        modelAndView.addObject("gender", Gender.values());
        return modelAndView;
    }

    @RequestMapping("/update")
    public ModelAndView update(Admin admin) {
        try {
            adminService.update(admin);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccessAndClose("修改成功", "admin_list");
    }

    @RequestMapping("/active")
    public ModelAndView active(String[] ids) {
        try {
            adminService.active(ids);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccess("操作成功");
    }

    @RequestMapping("/inactive")
    public ModelAndView inactive(String[] ids) {
        try {
            adminService.inactive(ids);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccess("操作成功");
    }

    @RequestMapping("/resetPwd")
    public ModelAndView resetPwd(String[] ids) {
        adminService.resetPwd(ids);
        return ajaxDoneSuccess("操作成功");
    }

    @RequestMapping("/addView")
    public ModelAndView addView() {
        ModelAndView modelAndView = new ModelAndView("management/admin/add");
        modelAndView.addObject("gender", Gender.values());
        return modelAndView;
    }

    @RequestMapping("/add")
    public ModelAndView add(Admin admin) {
        try {
            adminService.add(admin);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccessAndClose("添加成功", "admin_list");
    }

    @RequestMapping("/checkUsername")
    public @ResponseBody String checkUsername(String username) {
        return adminService.checkUsername(username);
    }
}
