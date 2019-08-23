package cn.abovesky.shopping.web.user;

import cn.abovesky.shopping.base.BaseController;
import cn.abovesky.shopping.domain.GoodsType;
import cn.abovesky.shopping.service.IGoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by snow on 2014/4/29.
 */
@Controller
@RequestMapping("/user/goodsType")
public class UGoodsTypeController extends BaseController {
    @Autowired
    private IGoodsTypeService goodsTypeService;

    @RequestMapping("/byMerchant")
    public @ResponseBody Map<String, Object> findByMerchant(Integer merchantId) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<GoodsType> goodsTypeList = goodsTypeService.findByMerchantId(merchantId);
        map.put("count", goodsTypeList.size());
        map.put("goodsTypes", goodsTypeList);
        return map;
    }

}
