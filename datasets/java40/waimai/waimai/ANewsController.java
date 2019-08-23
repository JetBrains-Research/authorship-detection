package cn.abovesky.shopping.web.management;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.base.BaseController;
import cn.abovesky.shopping.common.enums.NewsSearchType;
import cn.abovesky.shopping.common.enums.NewsType;
import cn.abovesky.shopping.domain.News;
import cn.abovesky.shopping.exception.ServiceException;
import cn.abovesky.shopping.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by snow on 2014/5/16.
 */
@Controller
@RequestMapping("/manage/news")
public class ANewsController extends BaseController {
    @Autowired
    private INewsService newsService;

    @RequestMapping("/list")
    public ModelAndView list(@ModelAttribute("vo") BaseConditionVO vo) {
        ModelAndView modelAndView = new ModelAndView("management/news/list");
        List<News> newsList = newsService.search(vo);
        modelAndView.addObject("newsList", newsList);
        modelAndView.addObject("newsType", NewsType.values());
        modelAndView.addObject("newsSearchType", NewsSearchType.values());
        return modelAndView;
    }

    @RequestMapping("/addView")
    public ModelAndView addView() {
        ModelAndView modelAndView = new ModelAndView("management/news/add");
        modelAndView.addObject("newsType", NewsType.values());
        return modelAndView;
    }

    @RequestMapping("/add")
    public ModelAndView add(News news) {
        try {
            newsService.add(news);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccessAndClose("添加成功", "news_list");
    }

    @RequestMapping("/edit/{item_id}")
    public ModelAndView editView(@PathVariable("item_id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("management/news/edit");
        modelAndView.addObject("news", newsService.findById(id));
        modelAndView.addObject("newsType", NewsType.values());
        return modelAndView;
    }

    @RequestMapping("/update")
    public ModelAndView update(News news) {
        try {
            newsService.update(news);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        if (news.getId() == 1) {
            return ajaxDoneSuccessAndClose("修改成功", "");
        } else {
            return ajaxDoneSuccessAndClose("修改成功", "news_list");
        }
    }

    @RequestMapping("/delete")
    public ModelAndView delete(Integer[] ids) {
        try {
            newsService.delete(ids);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccess("删除成功");
    }

    @RequestMapping("/announcement")
    public ModelAndView announcement() {
        ModelAndView modelAndView = new ModelAndView("management/news/announcement");
        modelAndView.addObject("news", newsService.findById(1));
        return modelAndView;
    }
}
