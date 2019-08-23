package cn.abovesky.shopping.web.user;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.base.BaseController;
import cn.abovesky.shopping.common.constant.Constants;
import cn.abovesky.shopping.domain.MerchantCollection;
import cn.abovesky.shopping.exception.ServiceException;
import cn.abovesky.shopping.service.IMerchantCollectionService;
import cn.abovesky.shopping.service.IMerchantService;
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
@RequestMapping("/user/merchantCollection")
public class UMerchantCollectionController extends BaseController {
    @Autowired
    private IMerchantCollectionService merchantCollectionService;

    @RequestMapping("/add")
    public @ResponseBody Map<String, Object> add(MerchantCollection merchantCollection) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            merchantCollectionService.add(merchantCollection);
            map.put(Constants.RESULT_CODE, "1");
        } catch (ServiceException e) {
            map.put(Constants.RESULT_CODE, "0");
            map.put(Constants.ERROR_MESSAGE, e.getMessage());
        }
        return map;
    }

    @RequestMapping("/cancel")
    public @ResponseBody Map<String, Object> cancel(MerchantCollection merchantCollection) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            merchantCollectionService.cancel(merchantCollection);
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
            List<MerchantCollection> merchantCollectionList = merchantCollectionService.search(vo);
            map.put("count", merchantCollectionList.size());
            map.put("merchantCollections", merchantCollectionList);
        } else {
            map.put("count", 0);
        }
        return map;
    }

    @RequestMapping("/isExist")
    public @ResponseBody Map<String, Object> isExist(MerchantCollection merchantCollection) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (merchantCollectionService.isExist(merchantCollection)) {
            map.put(Constants.RESULT_CODE, "1");
        } else {
            map.put(Constants.RESULT_CODE, "0");
        }
        return map;
    }
}
