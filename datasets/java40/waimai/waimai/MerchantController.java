package cn.abovesky.shopping.web.merchant;

import cn.abovesky.shopping.base.BaseController;
import cn.abovesky.shopping.domain.Merchant;
import cn.abovesky.shopping.exception.ServiceException;
import cn.abovesky.shopping.service.IMerchantService;
import cn.abovesky.shopping.service.INewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;

/**
 * Created by snow on 2014/4/20.
 */
@Controller
@RequestMapping("/merchant")
public class MerchantController extends BaseController {
    @Autowired
    private IMerchantService merchantService;
    @Autowired
    private INewsService newsService;

    @RequestMapping("/index")
    public ModelAndView index() {
        ModelAndView modelAndView = new ModelAndView("merchant_system/index");
        modelAndView.addObject("storeImage", merchantService.getImageById(this.getMerchant().getId()));
        modelAndView.addObject("news", newsService.findById(1));
        return modelAndView;
    }

    @RequestMapping("/changePwdView")
    public String changePwdView() {
        return "merchant_system/merchant/changePwd";
    }

    @RequestMapping("/changePwd")
    public ModelAndView changPwd(String oldPassword, String newPassword) {
        try {
            merchantService.changePwd(this.getMerchant().getId(), oldPassword, newPassword);
            return ajaxDoneSuccessAndClose("修改成功", "");
        } catch (ServiceException e) {
            return ajaxDoneError(e.getMessage());
        }
    }

    @RequestMapping("/infoView")
    public ModelAndView infoView() {
        ModelAndView modelAndView = new ModelAndView("merchant_system/merchant/info");
        Merchant merchant = merchantService.findById(this.getMerchant().getId());
        modelAndView.addObject("merchant", merchant);
        return modelAndView;
    }

    @RequestMapping("/update")
    public ModelAndView update(@RequestParam("imageFile") MultipartFile image, Merchant merchant) {
        try {
            merchantService.update(image, merchant);
            return ajaxDoneSuccessAndClose("修改成功", "");
        } catch (ServiceException e) {
            return ajaxDoneError("修改失败");
        }
    }

    @RequestMapping("/checkStoreName")
    public @ResponseBody String checkStoreName(String storeName, String oldStoreName) {
        try {
            storeName = new String(storeName.getBytes("iso-8859-1"), "utf-8");
            oldStoreName = new String(oldStoreName.getBytes("iso-8859-1"), "utf-8");
            if (oldStoreName.equals(storeName)) {
                return "true";
            } else {
                return merchantService.checkStoreName(storeName);
            }
        } catch (UnsupportedEncodingException e) {
            return "false";
        }
    }
}
