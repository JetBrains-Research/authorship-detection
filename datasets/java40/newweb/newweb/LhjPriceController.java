package com.newweb.controller.manager.model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.newweb.model.base.LhjPrice;
import com.newweb.model.base.MaterialBrand;
import com.newweb.service.base.LhjPriceService;
import com.newweb.service.base.MaterialBrandService;

@Controller
public class LhjPriceController {

	@Autowired
	private LhjPriceService lhjPriceService;
	@Autowired
	private MaterialBrandService materialBrandService;
	
	@RequestMapping(value="lhjPriceManager.ajax")
	@ResponseBody
	public String lhjPriceManager(HttpServletRequest request, HttpServletResponse response) throws IOException{
		response.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		synchronized (id) {
			String priceStr = request.getParameter("price");
			String materialBrandStr = request.getParameter("materialBrand");
			PrintWriter out = response.getWriter();
			if(priceStr.equals("") || materialBrandStr.trim().equals("")){
				out.write("failed服务器返回:零售价,品牌不能有空值");
				return null;
			}
			double price = 0;
			try {
				price = Double.parseDouble(priceStr);
			} catch (Exception e) {
				out.write("failed服务器返回:零售价无效");
				return null;
			}
			int materialBrandID = 0;
			try {
				materialBrandID = Integer.parseInt(materialBrandStr);
			} catch (Exception e) {
				out.write("failed服务器返回:铝材品牌id无法识别，操作失败");
				return null;
			}
			MaterialBrand mb = materialBrandService.findMaterialBrandById(materialBrandID);
			if(mb == null){
				out.write("failed更新失败,铝材品牌对象未找到");
				return null;
			}
			if(id != null && !id.trim().equals("")){
				int lid = 0;
				try {
					lid = Integer.parseInt(id);
				} catch (Exception e) {
					out.write("failed服务器返回:铝材id无法识别，操作失败");
					return null;
				}
				LhjPrice lhjp = lhjPriceService.findLhjPriceByID(lid);
				if(lhjp == null){
					out.write("failed更新失败,查找铝材价格对象失败");
					return null;
				}
				lhjp.setPrice(price);
				lhjp.setMaterialBrand(mb);
				if(!lhjPriceService.modify(lhjp)){
					out.write("failed更新失败,数据库保存失败");
				}
			}else{
				if(lhjPriceService.findLhjPriceByMaterialBrandID(materialBrandID) != null){
					out.write("failed服务器返回:该品牌铝材价格信息已存在,保存失败");
					return null;
				}
				LhjPrice lhjp = new LhjPrice();
				lhjp.setMaterialBrand(mb);
				lhjp.setPrice(price);
				if(!lhjPriceService.save(lhjp)){
					out.write("failed服务器返回:保存失败,请尝试重试");
					return null;
				}
			}
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="getLhjPricesJsonData.ajax")
	@ResponseBody
	public String getLhjPricesJsonData(HttpServletRequest request, HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		int start = Integer.parseInt(request.getParameter("start"));
		int limit = Integer.parseInt(request.getParameter("limit"));
		String json = "";
		List list = lhjPriceService.queryAllLhjPrices(start,limit);
		int size = (Integer) list.get(0);
		LhjPrice[] lhjPrices = (LhjPrice[]) list.get(1);
		json = "{\"total\":\"" +size+"\",\"data\":[";	//json串头
		for (LhjPrice lhjPrice : lhjPrices) {
			String str = "{" +
					"\"id\":\"" + lhjPrice.getID() +"\"" +
					",\"price\":\"" + lhjPrice.getPrice() + "\"" +
					",\"materialBrandID\":\"" + lhjPrice.getMaterialBrand().getID() +"" + "\"" +
					",\"materialBrandName\":\"" + lhjPrice.getMaterialBrand().getName() + "\"" +
					",\"brandValid\":\"" + (lhjPrice.getMaterialBrand().isValid()?"可用":"不可用") + "\"" +
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
