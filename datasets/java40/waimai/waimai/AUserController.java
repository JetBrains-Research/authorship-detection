package cn.abovesky.shopping.web.management;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.base.BaseController;
import cn.abovesky.shopping.common.enums.Areas;
import cn.abovesky.shopping.common.enums.Gender;
import cn.abovesky.shopping.common.enums.Schools;
import cn.abovesky.shopping.common.enums.UserStatus;
import cn.abovesky.shopping.domain.User;
import cn.abovesky.shopping.exception.ServiceException;
import cn.abovesky.shopping.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by snow on 2014/5/14.
 */
@Controller
@RequestMapping("/manage/user")
public class AUserController extends BaseController {
    @Autowired
    private IUserService userService;

    @RequestMapping("/list")
    public ModelAndView list(@ModelAttribute("vo") BaseConditionVO vo) {
        ModelAndView modelAndView = new ModelAndView("management/user/list");
        List<User> userList = userService.search(vo);
        modelAndView.addObject("userList", userList);
        modelAndView.addObject("userStatus", UserStatus.values());
        modelAndView.addObject("gender", Gender.values());
        modelAndView.addObject("school", Schools.values());
        modelAndView.addObject("area", Areas.values());
        return modelAndView;
    }

    @RequestMapping("/edit/{user_id}")
    public ModelAndView editView(@PathVariable("user_id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("management/user/edit");
        User user = userService.findById(id);
        user.setPassword(null);
        modelAndView.addObject("user", user);
        modelAndView.addObject("gender", Gender.values());
        modelAndView.addObject("school", Schools.values());
        modelAndView.addObject("area", Areas.values());
        return modelAndView;
    }

    @RequestMapping("/update")
    public ModelAndView update(User user) {
        userService.update(user);
        return ajaxDoneSuccessAndClose("修改成功", "user_list");
    }

    @RequestMapping("/active")
    public ModelAndView active(String[] ids) {
        try {
            userService.active(ids);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccess("操作成功");
    }

    @RequestMapping("/inactive")
    public ModelAndView inactive(String[] ids) {
        try {
            userService.inactive(ids);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccess("操作成功");
    }

    @RequestMapping("/resetPwd")
    public ModelAndView resetPwd(String[] ids) {
        userService.resetPwd(ids);
        return ajaxDoneSuccess("操作成功");
    }
}
