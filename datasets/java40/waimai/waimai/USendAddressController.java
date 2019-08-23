package cn.abovesky.shopping.web.user;

import cn.abovesky.shopping.base.BaseController;
import cn.abovesky.shopping.domain.SendAddress;
import cn.abovesky.shopping.service.ISendAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by snow on 2014/5/1.
 */
@Controller
@RequestMapping("/user/sendAddress")
public class USendAddressController extends BaseController {
    @Autowired
    private ISendAddressService sendAddressService;

    @RequestMapping("/list")
    public @ResponseBody Map<String, Object> list(Integer userId) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<SendAddress> sendAddresses = sendAddressService.findById(userId);
        map.put("count", sendAddresses.size());
        map.put("sendAddresses", sendAddresses);
        return map;
    }
}
