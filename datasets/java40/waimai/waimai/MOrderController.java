package cn.abovesky.shopping.web.merchant;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.common.enums.OrderStatus;
import cn.abovesky.shopping.domain.OrderDetail;
import cn.abovesky.shopping.exception.ServiceException;
import cn.abovesky.shopping.service.IMerchantService;
import cn.abovesky.shopping.service.IOrderService;
import cn.abovesky.shopping.service.ISaleRecordService;
import cn.abovesky.shopping.web.common.ComOrderController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by snow on 2014/5/1.
 */
@Controller
@RequestMapping("/merchant/order")
public class MOrderController extends ComOrderController {
    @Autowired
    private IOrderService orderService;
    @Autowired
    private ISaleRecordService saleRecordService;
    @Autowired
    private IMerchantService merchantService;

    @RequestMapping("/list")
    public ModelAndView list(BaseConditionVO vo) {
        ModelAndView modelAndView = new ModelAndView("merchant_system/order/list");
        vo.setMerchantId(getMerchant().getId());
        List<OrderDetail> orderList = orderService.search(vo);
        modelAndView.addObject("orderList", orderList);
        modelAndView.addObject("orderStatus", OrderStatus.values());
        modelAndView.addObject("vo", vo);
        modelAndView.addObject("todayNumber", saleRecordService.countNumber(vo, "4"));
        modelAndView.addObject("todayTotalPrice", saleRecordService.countTotalPrice(vo, "4"));
        modelAndView.addObject("weekTotalPrice", saleRecordService.countTotalPrice(vo, "5"));
        modelAndView.addObject("monthTotalPrice", saleRecordService.countTotalPrice(vo, "6"));
        modelAndView.addObject("yearTotalPrice", saleRecordService.countTotalPrice(vo, "7"));
        modelAndView.addObject("collectionCount", merchantService.getCollectionCountById(this.getMerchant().getId()));
        return modelAndView;
    }

    @RequestMapping("/confirmSend")
    public ModelAndView confirmSend(String[] ids) {
        try {
            orderService.confirmSend(ids, this.getMerchant().getStoreName());
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        } catch (UnsupportedEncodingException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccess("确认成功");
    }

    @RequestMapping("/send")
    public ModelAndView send(String[] ids) {
        try {
            orderService.send(ids);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccess("操作成功");
    }
}
