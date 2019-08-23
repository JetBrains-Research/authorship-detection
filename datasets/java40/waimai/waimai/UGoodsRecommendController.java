package cn.abovesky.shopping.web.user;

import cn.abovesky.shopping.base.BaseController;
import cn.abovesky.shopping.domain.Goods;
import cn.abovesky.shopping.service.ISaleRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by snow on 2014/5/9.
 */
@Controller
@RequestMapping("/user/goodsRecommend")
public class UGoodsRecommendController extends BaseController {
    @Autowired
    private ISaleRecordService saleRecordService;

    @RequestMapping("/list")
    public @ResponseBody Map<String, Object> list(Integer size) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Goods> goods = saleRecordService.getGoodsRecommend(size);
        map.put("count", goods.size());
        map.put("goods", goods);
        return map;
    }
}
