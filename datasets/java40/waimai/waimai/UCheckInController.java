package cn.abovesky.shopping.web.user;

import cn.abovesky.shopping.base.BaseController;
import cn.abovesky.shopping.common.constant.Constants;
import cn.abovesky.shopping.domain.CheckIn;
import cn.abovesky.shopping.exception.ServiceException;
import cn.abovesky.shopping.service.ICheckInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by snow on 2014/5/2.
 */
@Controller
@RequestMapping("/user/checkIn")
public class UCheckInController extends BaseController {
    @Autowired
    private ICheckInService checkInService;

    @RequestMapping("")
    public @ResponseBody Map<String, Object> add(CheckIn checkIn) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            checkInService.add(checkIn);
            map.put(Constants.RESULT_CODE, "1");
        } catch (ServiceException e) {
            map.put(Constants.RESULT_CODE, "0");
            map.put(Constants.ERROR_MESSAGE, e.getMessage());
        }
        return map;
    }
}
