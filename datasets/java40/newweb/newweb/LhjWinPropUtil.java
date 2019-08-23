package com.newweb.util;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.newweb.model.business.OrderLhjWinProp;
import com.newweb.service.base.MaterialBrandService;
import com.newweb.service.business.OrderService;

public class LhjWinPropUtil {

	private static void pushWinProp(OrderLhjWinProp model,JSONObject winProp,MaterialBrandService materialBrandService,String brandID,String typeForBrandID,
			String windowsBuyType) throws NumberFormatException, JSONException{
		model.setMaterialBrand(materialBrandService.
				findMaterialBrandById(Integer.parseInt(brandID)));
		model.setTypeForBrand(materialBrandService.
				findTypeForBrandById(Integer.parseInt(typeForBrandID)));
		model.setWindowsBuyType(windowsBuyType);
		model.setWinHeight(Double.parseDouble(winProp.getString("winHeight")));
		model.setWinWidth(Double.parseDouble(winProp.getString("winWidth")));
		model.setWinCount(Integer.parseInt(winProp.getString("winCount")));
		model.setShType(winProp.getString("shType"));
		model.setNmHeight(Double.parseDouble(winProp.getString("nmHeight")));
		model.setYtDirection(winProp.getString("ytDirection"));
		model.setIsHasZZ(Integer.parseInt(winProp.getString("isHasZZ")));
		model.setIsHasCircle(Integer.parseInt(winProp.getString("isHasCircle")));
		try {
			model.setCircleCount(Integer.parseInt(winProp.getString("circleCount")));
		} catch (Exception e) {}
		model.setWinType(winProp.getString("winType"));
		try {
			model.setFourLinkPartZZCount(Integer.parseInt(winProp.getString("fourLinkPartZZCount")));
		} catch (Exception e) {}
		model.setGlassType(winProp.getString("glassType"));
		model.setRemark(winProp.getString("remark"));
	}
	
	public static OrderLhjWinProp pushJsonDataToModel(JSONObject winProp,String brandID,String typeForBrandID,
			String windowsBuyType,MaterialBrandService materialBrandService) throws NumberFormatException, JSONException{
		OrderLhjWinProp model = new OrderLhjWinProp();
		
		pushWinProp(model, winProp, materialBrandService, brandID, typeForBrandID, windowsBuyType);
		
		return model;
	}
	
	public static List<OrderLhjWinProp> pushJsonDataToModel(JSONArray winProps,String brandID,String typeForBrandID,
			String windowsBuyType,MaterialBrandService materialBrandService) throws NumberFormatException, JSONException{
		List<OrderLhjWinProp> list = new ArrayList<OrderLhjWinProp>();
		for(int i = 0;i<winProps.length();i++){
			JSONObject winProp = winProps.getJSONObject(i);
			OrderLhjWinProp model = new OrderLhjWinProp();
			
			pushWinProp(model, winProp, materialBrandService, brandID, typeForBrandID, windowsBuyType);
			list.add(model);
		}
		return list;
	}
	
	public static List<OrderLhjWinProp> pushJsonDataToModel(JSONArray winProps,String brandID,String typeForBrandID,
			String windowsBuyType,String orderID,MaterialBrandService materialBrandService,OrderService orderService) throws NumberFormatException, JSONException{
		List<OrderLhjWinProp> list = new ArrayList<OrderLhjWinProp>();
		for(int i = 0;i<winProps.length();i++){
			JSONObject winProp = winProps.getJSONObject(i);
			OrderLhjWinProp model = new OrderLhjWinProp();
			model.setOrder(orderService.findOrderById(orderID));
			pushWinProp(model, winProp, materialBrandService, brandID, typeForBrandID, windowsBuyType);
			list.add(model);
		}
		return list;
	}
	
	public static OrderLhjWinProp pushJsonDataToModel(JSONObject winProp,String brandID,String typeForBrandID,
			String windowsBuyType,String orderID,MaterialBrandService materialBrandService,OrderService orderService) throws NumberFormatException, JSONException{
		OrderLhjWinProp model = new OrderLhjWinProp();
		model.setOrder(orderService.findOrderById(orderID));
		pushWinProp(model, winProp, materialBrandService, brandID, typeForBrandID, windowsBuyType);
		
		return model;
	}
	
}
