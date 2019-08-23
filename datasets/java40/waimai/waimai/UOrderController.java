package cn.abovesky.shopping.web.user;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.base.BaseController;
import cn.abovesky.shopping.common.constant.Constants;
import cn.abovesky.shopping.domain.Order;
import cn.abovesky.shopping.domain.OrderDetail;
import cn.abovesky.shopping.domain.Remark;
import cn.abovesky.shopping.exception.ServiceException;
import cn.abovesky.shopping.service.IOrderService;
import cn.abovesky.shopping.service.ISendAddressService;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by snow on 2014/4/29.
 */
@Controller
@RequestMapping("/user/order")
public class UOrderController extends BaseController {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private ISendAddressService sendAddressService;

    @RequestMapping("/add")
    public @ResponseBody Map<String, Object> add(String detail, Order order) {
        Map<String, Object> map = new HashMap<String, Object>();
        ObjectMapper mapper = new ObjectMapper();
        List<OrderDetail> orderDetails = null;
        sendAddressService.add(order.getUserId(), order.getAddress());
        try {
            orderDetails = mapper.readValue(detail, new TypeReference<List<OrderDetail>>() {});
        } catch (IOException e) {
            map.put(Constants.RESULT_CODE, "0");
            map.put(Constants.ERROR_MESSAGE, "格式有误");
            return map;
        }
        try {
            orderService.add(orderDetails, order);
            map.put(Constants.RESULT_CODE, "1");
        } catch (ServiceException e) {
            map.put(Constants.RESULT_CODE, "2");
            map.put(Constants.ERROR_MESSAGE, e.getMessage());
        }
        return map;
    }

    @RequestMapping("/list")
    public @ResponseBody Map<String, Object> list(BaseConditionVO vo) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<OrderDetail> orderDetails = orderService.search(vo);
        map.put("count", orderDetails.size());
        map.put("orderDetails", orderDetails);
        return map;
    }

    @RequestMapping("/confirmAndRemark")
    public @ResponseBody Map<String, Object> confirm(Remark remark, Integer orderId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            orderService.confirmAndRemark(remark, orderId);
            map.put(Constants.RESULT_CODE, "1");
        } catch (ServiceException e) {
            map.put(Constants.RESULT_CODE, "0");
            map.put(Constants.ERROR_MESSAGE, e.getMessage());
        }
        return map;
    }

}
