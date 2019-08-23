package com.jufan.platform.view;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.TextView;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.jufan.platform.ui.LocationPickerActivity;
import com.jufan.platform.ui.R;
import com.lianzt.commondata.AbstractCommonData;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;

public class LocationNearbyOverlay extends ItemizedOverlay {

	private List<AbstractCommonData> positionList = new LinkedList<AbstractCommonData>();

	private MapView mapView;

	private PopupOverlay popupOverlay;

	private CustomOverlayInterface locationPicker;

	public LocationNearbyOverlay(Drawable defaultMarker, MapView mapView,
			PopupOverlay popupOverlay, CustomOverlayInterface locationPicker) {
		super(defaultMarker, mapView);
		this.mapView = mapView;
		this.popupOverlay = popupOverlay;
		this.locationPicker = locationPicker;
	}

	@Override
	public boolean onTap(int index) {
		AbstractCommonData acd = positionList.get(index);
		locationPicker.showBubble(acd, 500);
		return true;
	}

	public void addPositionItem(AbstractCommonData acd) {
		positionList.add(acd);
		GeoPoint p = new GeoPoint(acd.getIntValue("lat"),
				acd.getIntValue("lon"));
		OverlayItem item = new OverlayItem(p,
				acd.getStringValue("location_jc"), "");
		Bitmap bmp = ImageLoader.getInstance().loadImageSync(
				"drawable://" + R.drawable.location_arrows,
				new ImageSize(25, 25));
		item.setMarker(new BitmapDrawable(bmp));
		addItem(item);
	}
}
