package cn.abovesky.shopping.web;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.base.BaseController;
import cn.abovesky.shopping.domain.News;
import cn.abovesky.shopping.service.IAppService;
import cn.abovesky.shopping.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by snow on 2014/4/22.
 */
@Controller
public class IndexController extends BaseController {
    @Autowired
    private INewsService newsService;
    @Autowired
    private IAppService appService;

    @RequestMapping("")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("totalCount", newsService.getTotalCount());
        modelAndView.addObject("apkUrl", appService.getNewestUrl());
        return modelAndView;
    }

    @RequestMapping("/manageLogin")
    public ModelAndView manageLogin() {
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("flag", "admin");
        modelAndView.addObject("totalCount", newsService.getTotalCount());
        modelAndView.addObject("apkUrl", appService.getNewestUrl());
        return modelAndView;
    }

    @RequestMapping("/news/{pageNum}")
    public @ResponseBody Map<String, Object> news(@PathVariable("pageNum") Integer pageNum) {
        Map<String, Object> map = new HashMap<String, Object>();
        BaseConditionVO vo = new BaseConditionVO();
        vo.setPageNum(pageNum + 1);
        vo.setPageSize(5);
        List<News> newsList = newsService.search(vo);
        map.put("newsList", newsList);
        return map;
    }

    @RequestMapping("/register")
    public ModelAndView registerView() {
        return new ModelAndView("register");
    }

    @RequestMapping("/addDownloadCount")
    public String addDownloadCount() {
        appService.addDownloadCount();
        return null;
    }
}
