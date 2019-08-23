package cn.abovesky.shopping.web.user;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.base.BaseController;
import cn.abovesky.shopping.common.constant.Constants;
import cn.abovesky.shopping.domain.GoodsCollection;
import cn.abovesky.shopping.exception.ServiceException;
import cn.abovesky.shopping.service.IGoodsCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by snow on 2014/5/3.
 */
@Controller
@RequestMapping("/user/goodsCollection")
public class UGoodsCollectionController extends BaseController {
    @Autowired
    private IGoodsCollectionService goodsCollectionService;

    @RequestMapping("/add")
    public @ResponseBody Map<String, Object> add(GoodsCollection goodsCollection) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            goodsCollectionService.add(goodsCollection);
            map.put(Constants.RESULT_CODE, "1");
        } catch (ServiceException e) {
            map.put(Constants.RESULT_CODE, "0");
            map.put(Constants.ERROR_MESSAGE, e.getMessage());
        }
        return map;
    }

    @RequestMapping("/cancel")
    public @ResponseBody Map<String, Object> cancel(GoodsCollection goodsCollection) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            goodsCollectionService.cancel(goodsCollection);
            map.put(Constants.RESULT_CODE, "1");
        } catch (ServiceException e) {
            map.put(Constants.RESULT_CODE, "0");
            map.put(Constants.ERROR_MESSAGE, e.getMessage());
        }
        return map;
    }

    @RequestMapping("/list")
    public @ResponseBody Map<String, Object> list(BaseConditionVO vo) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(vo.getUserId())) {
            List<GoodsCollection> goodsCollectionList = goodsCollectionService.search(vo);
            map.put("count", goodsCollectionList.size());
            map.put("goodsCollections", goodsCollectionList);
        } else {
            map.put("count", 0);
        }
        return map;
    }

    @RequestMapping("/isExist")
    public @ResponseBody Map<String, Object> isExist(GoodsCollection goodsCollection) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (goodsCollectionService.isExist(goodsCollection)) {
            map.put(Constants.RESULT_CODE, "1");
        } else {
            map.put(Constants.RESULT_CODE, "0");
        }
        return map;
    }
}
