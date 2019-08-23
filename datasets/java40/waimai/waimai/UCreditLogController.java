package cn.abovesky.shopping.web.user;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.base.BaseController;
import cn.abovesky.shopping.domain.CreditLog;
import cn.abovesky.shopping.service.ICreditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by snow on 2014/5/2.
 */
@Controller
@RequestMapping("/user/creditLog")
public class UCreditLogController extends BaseController {
    @Autowired
    private ICreditLogService creditLogService;

    @RequestMapping("/list")
    public @ResponseBody Map<String, Object> list(BaseConditionVO vo) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<CreditLog> creditLogs = creditLogService.search(vo);
        map.put("count", creditLogs.size());
        map.put("creditLogs", creditLogs);
        return map;
    }
}
