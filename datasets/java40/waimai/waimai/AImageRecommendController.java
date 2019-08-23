package cn.abovesky.shopping.web.management;

import cn.abovesky.shopping.base.BaseConditionVO;
import cn.abovesky.shopping.base.BaseController;
import cn.abovesky.shopping.common.enums.ImageRecommendType;
import cn.abovesky.shopping.domain.ImageRecommend;
import cn.abovesky.shopping.exception.ServiceException;
import cn.abovesky.shopping.service.IImageRecommendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by snow on 2014/5/16.
 */
@Controller
@RequestMapping("/manage/imageRecommend")
public class AImageRecommendController extends BaseController {
    @Autowired
    private IImageRecommendService imageRecommendService;

    @RequestMapping("/list")
    public ModelAndView list(@ModelAttribute("vo") BaseConditionVO vo) {
        ModelAndView modelAndView = new ModelAndView("management/imageRecommend/list");
        List<ImageRecommend> imageRecommendList = imageRecommendService.search(vo);
        modelAndView.addObject("imageRecommendList", imageRecommendList);
        modelAndView.addObject("imageRecommendType", ImageRecommendType.values());
        return modelAndView;
    }

    @RequestMapping("/addView")
    public ModelAndView addView() {
        ModelAndView modelAndView = new ModelAndView("management/imageRecommend/add");
        modelAndView.addObject("imageRecommendType", ImageRecommendType.values());
        return modelAndView;
    }

    @RequestMapping("/add")
    public ModelAndView add(@RequestParam("imageFile") MultipartFile image, ImageRecommend imageRecommend) {
        try {
            imageRecommendService.add(image, imageRecommend);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccessAndClose("添加成功", "imageRecommend_list");
    }

    @RequestMapping("/edit/{item_id}")
    public ModelAndView editView(@PathVariable("item_id") Integer id) {
        ModelAndView modelAndView = new ModelAndView("management/imageRecommend/edit");
        ImageRecommend imageRecommend = imageRecommendService.findById(id);
        modelAndView.addObject("imageRecommend", imageRecommend);
        modelAndView.addObject("imageRecommendType", ImageRecommendType.values());
        return modelAndView;
    }

    @RequestMapping("/update")
    public ModelAndView update(@RequestParam("imageFile") MultipartFile image, ImageRecommend imageRecommend) {
        try {
            imageRecommendService.update(image, imageRecommend);
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
        return ajaxDoneSuccessAndClose("修改成功", "imageRecommend_list");
    }

    @RequestMapping("/delete")
    public ModelAndView delete(String[] ids) {
        imageRecommendService.delete(ids);
        return ajaxDoneSuccess("删除成功");
    }
}
