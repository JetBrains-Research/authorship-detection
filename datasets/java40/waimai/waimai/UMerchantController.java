package cn.abovesky.shopping.web.user;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.base.BaseController;
import cn.abovesky.shopping.domain.Merchant;
import cn.abovesky.shopping.service.IMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by snow on 2014/4/28.
 */
@Controller
@RequestMapping("/user/merchant")
public class UMerchantController extends BaseController {
    @Autowired
    private IMerchantService merchantService;

    @RequestMapping("/list")
    public @ResponseBody Map<String, Object> list(BaseConditionVO vo) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Merchant> merchantList = merchantService.findPageBreakByCondition(vo);
        map.put("count", merchantList.size());
        map.put("merchants", merchantList);
        return map;
    }

    @RequestMapping("/detail")
    public @ResponseBody Map<String, Object> detail(Integer merchantId) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (merchantId == null || merchantId == 0) {
            map.put("merchant", null);
            return map;
        } else {
            map.put("merchant", merchantService.findById(merchantId));
            return map;
        }
    }
}
