package cn.abovesky.shopping.web.user;

import cn.abovesky.shopping.base.BaseController;
import cn.abovesky.shopping.common.constant.Constants;
import cn.abovesky.shopping.common.enums.LoginStatus;
import cn.abovesky.shopping.common.enums.UserStatus;
import cn.abovesky.shopping.common.enums.UserType;
import cn.abovesky.shopping.domain.LoginLog;
import cn.abovesky.shopping.domain.User;
import cn.abovesky.shopping.exception.ServiceException;
import cn.abovesky.shopping.service.ILoginLogService;
import cn.abovesky.shopping.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by snow on 2014/4/26.
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;
    @Autowired
    private ILoginLogService loginLogService;

    @RequestMapping("/register")
    public @ResponseBody Map<String, Object> add(User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            userService.add(user);
            map.put(Constants.RESULT_CODE, "1");
        } catch (ServiceException e) {
            map.put(Constants.RESULT_CODE, "0");
            map.put(Constants.ERROR_MESSAGE, "手机号已存在");
        }
        return map;
    }

    @RequestMapping("/login")
    public @ResponseBody Map<String, Object> login(User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        User loginUser = userService.findByUsername(user.getUsername());
        LoginLog loginLog = new LoginLog();
        loginLog.setUserType(UserType.USER.toString());
        loginLog.setDate(new Date());
        loginLog.setIp(request.getRemoteAddr());
        loginLog.setStatus(LoginStatus.FAIL.toString());
        if (loginUser == null) {
            map.put(Constants.RESULT_CODE, "0");
            map.put(Constants.ERROR_MESSAGE, "用户名不存在");
        } else if (!loginUser.getPassword().equals(user.getPassword())) {
            map.put(Constants.RESULT_CODE, "2");
            map.put(Constants.ERROR_MESSAGE, "密码错误");
            loginLog.setUserId(loginUser.getId());
        } else if (UserStatus.INACTIVE.toString().equals(loginUser.getStatus())) {
            map.put(Constants.RESULT_CODE, "3");
            map.put(Constants.ERROR_MESSAGE, "用户已被禁用");
            loginLog.setUserId(loginUser.getId());
        } else {
            map.put(Constants.RESULT_CODE, "1");
            loginUser.setPassword(null);
            loginUser.setStatus(null);
            map.put(Constants.USER, loginUser);
            loginLog.setUserId(loginUser.getId());
            loginLog.setStatus(LoginStatus.SUCCESS.toString());
        }
        loginLogService.add(loginLog);
        return map;
    }

    @RequestMapping("/update")
    public @ResponseBody Map<String, Object> update(User user) {
        Map<String, Object> map = new HashMap<String, Object>();
        userService.update(user);
        map.put(Constants.RESULT_CODE, "1");
        return map;
    }

    @RequestMapping("/editPwd")
    public @ResponseBody Map<String, Object> editPwd(User user, String newPwd) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            userService.editPwd(user, newPwd);
            map.put(Constants.RESULT_CODE, "1");
        } catch (ServiceException e) {
            map.put(Constants.RESULT_CODE, "0");
            map.put(Constants.ERROR_MESSAGE, e.getMessage());
        }
        return map;
    }

    @RequestMapping("/credit")
    public @ResponseBody Map<String, Object> getCredit(Integer userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Integer credit = userService.getCreditByUserId(userId);
        map.put("credit", credit);
        return map;
    }

    @RequestMapping("/info")
    public @ResponseBody Map<String, Object> info(Integer userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = userService.findById(userId);
        if (user != null) {
            user.setStatus(null);
            user.setPassword(null);
        }
        map.put("user", user);
        return map;
    }
}
