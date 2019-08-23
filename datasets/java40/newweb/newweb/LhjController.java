package com.newweb.controller.manager.model;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.newweb.model.base.MaterialBrand;
import com.newweb.service.base.MaterialBrandService;

@Controller
public class LhjController {

	@Autowired
	private MaterialBrandService materialBrandService;
	
	/**
	 * ajax请求
	 * 返回铝合金品牌的json串
	 * @param locale
	 * @param model
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="getLhjMaterialBrands.ajax")
	@ResponseBody
	public String getLhjMaterialBrands(Locale locale, Model model, ModelMap modelMap,
			HttpServletRequest request, HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		String json = "{\"data\":[";	//json串头
		MaterialBrand[] mbs = materialBrandService.queryMaterialBrandsByType("lhj");
		for (MaterialBrand mb : mbs) {
			String str = "{" +
					"\"name\":\"" + mb.getName() +"\"" +
					",\"id\":\"" + mb.getID() + "\"" +
					",\"py\":\"" + mb.getPy() + "\"" +
					"},";
			json += str;
		}
		json = json.substring(0, json.length()-1);	//去掉多余的逗号
		json += "]}";	//json串尾
		
		try {
			response.getWriter().write(json);//将JSON数据写入response中
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
