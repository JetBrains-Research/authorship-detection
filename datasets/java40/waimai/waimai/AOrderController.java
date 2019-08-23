package cn.abovesky.shopping.web.management;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.common.enums.OrderStatus;
import cn.abovesky.shopping.domain.OrderDetail;
import cn.abovesky.shopping.service.IOrderService;
import cn.abovesky.shopping.service.ISaleRecordService;
import cn.abovesky.shopping.web.common.ComOrderController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by snow on 2014/5/12.
 */
@Controller
@RequestMapping("/manage/order")
public class AOrderController extends ComOrderController {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private ISaleRecordService saleRecordService;

    @RequestMapping("/list")
    public ModelAndView list(BaseConditionVO vo) {
        ModelAndView modelAndView = new ModelAndView("management/order/list");
        List<OrderDetail> orderList = orderService.search(vo);
        modelAndView.addObject("orderList", orderList);
        modelAndView.addObject("orderStatus", OrderStatus.values());
        modelAndView.addObject("vo", vo);
        modelAndView.addObject("todayNumber", saleRecordService.countNumber(vo, "4"));
        modelAndView.addObject("todayTotalPrice", saleRecordService.countTotalPrice(vo, "4"));
        modelAndView.addObject("weekTotalPrice", saleRecordService.countTotalPrice(vo, "5"));
        modelAndView.addObject("monthTotalPrice", saleRecordService.countTotalPrice(vo, "6"));
        modelAndView.addObject("yearTotalPrice", saleRecordService.countTotalPrice(vo, "7"));
        return modelAndView;
    }
}

