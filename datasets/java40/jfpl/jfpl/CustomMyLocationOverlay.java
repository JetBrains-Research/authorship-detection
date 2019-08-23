package com.jufan.platform.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.TextView;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.PopupOverlay;
import com.jufan.platform.ui.LocationPickerActivity;
import com.lianzt.commondata.AbstractCommonData;

public class CustomMyLocationOverlay extends MyLocationOverlay {

	private MapView mapView;
	private PopupOverlay popupOverlay;
	private AbstractCommonData data;
	private CustomOverlayInterface locationPicker;

	public CustomMyLocationOverlay(MapView mapView, PopupOverlay popupOverlay,
			CustomOverlayInterface locationPicker) {
		super(mapView);
		this.mapView = mapView;
		this.popupOverlay = popupOverlay;
		this.locationPicker = locationPicker;
	}

	@Override
	protected boolean dispatchTap() {
		if (data == null) {
			return true;
		}
		locationPicker.showBubble(data, 0);
		return super.dispatchTap();
	}

	public AbstractCommonData getData() {
		return data;
	}

	public void setData(AbstractCommonData data) {
		this.data = data;
	}

}
