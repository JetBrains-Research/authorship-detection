package cn.abovesky.shopping.web.user;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.base.BaseController;
import cn.abovesky.shopping.domain.Remark;
import cn.abovesky.shopping.service.IRemarkService;
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
@RequestMapping("/user/remark")
public class URemarkController extends BaseController {
    @Autowired
    private IRemarkService remarkService;

    @RequestMapping("/list")
    public @ResponseBody Map<String, Object> list(BaseConditionVO vo) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Remark> remarks = remarkService.search(vo);
        map.put("count", remarks.size());
        map.put("remarks", remarks);
        return map;
    }
}
