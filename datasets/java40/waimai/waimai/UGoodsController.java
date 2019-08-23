package cn.abovesky.shopping.web.user;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.base.BaseController;
import cn.abovesky.shopping.domain.Goods;
import cn.abovesky.shopping.domain.GoodsType;
import cn.abovesky.shopping.service.IGoodsService;
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
@RequestMapping("/user/goods")
public class UGoodsController extends BaseController {
    @Autowired
    private IGoodsService goodsService;

    @RequestMapping("/list/byMerchant")
    public @ResponseBody Map<String, Object> findByMerchant(BaseConditionVO vo) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Goods> goodsList = goodsService.search(vo);
        map.put("count", goodsList.size());
        map.put("goods", goodsList);
        return map;
    }

    @RequestMapping("/detail")
    public @ResponseBody Map<String, Object> findById(Integer goodsId) {
        Map<String, Object> map = new HashMap<String, Object>();
        Goods goods = goodsService.getDetailById(goodsId);
        map.put("goods", goods);
        return map;
    }
}
