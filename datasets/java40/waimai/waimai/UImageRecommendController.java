package cn.abovesky.shopping.web.user;

import cn.abovesky.shopping.base.BaseController;
import cn.abovesky.shopping.domain.ImageRecommend;
import cn.abovesky.shopping.service.IImageRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by snow on 2014/5/9.
 */
@Controller
@RequestMapping("/user/imageRecommend")
public class UImageRecommendController extends BaseController {
    @Autowired
    private IImageRecommendService imageRecommendService;

    @RequestMapping("/list")
    public @ResponseBody Map<String, Object> list() {
        Map<String, Object> map = new HashMap<String, Object>();
        List<ImageRecommend> imageRecommends = imageRecommendService.findAll();
        map.put("count", imageRecommends.size());
        map.put("imageRecommends", imageRecommends);
        return map;
    }
}
