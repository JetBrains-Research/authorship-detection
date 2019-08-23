package com.jufan.platform.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.cyss.android.lib.AutoActivity;
import com.cyss.android.lib.annotation.InjectionView;
import com.jufan.platform.util.ConstVariables;
import com.jufan.platform.view.CustomMyLocationOverlay;
import com.jufan.platform.view.CustomOverlayInterface;
import com.jufan.platform.view.LocationNearbyOverlay;
import com.lianzt.commondata.AbstractCommonData;
import com.lianzt.commondata.DataConvertFactory;

public class ShowLocationActivity extends AutoActivity implements
		BDLocationListener, CustomOverlayInterface {

	private BMapManager mapManager = null;

	@InjectionView(id = R.id.bmapsView)
	private MapView mapView;

	private CustomMyLocationOverlay myLocOverlay;

	private LocationNearbyOverlay nearbyOverlay;

	private PopupOverlay popupOverlay;

	private LocationClient locationClient;

	private LocationData ld;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mapManager = new BMapManager(getApplication());
		mapManager.init(ConstVariables.BAIDU_API_KEY, null);
		setContentView(R.layout.show_location);
		mapView.setBuiltInZoomControls(true);
		MapController mapController = mapView.getController();
		Intent intent = getIntent();
		String[] arr = intent.getStringArrayExtra("tag");
		if (arr.length != 4) {
			finish();
		}
		int lat = Integer.parseInt(arr[2]);
		int lon = Integer.parseInt(arr[3]);
		GeoPoint point = new GeoPoint(lat, lon);
		
		// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mapController.setCenter(point);// 设置地图中心点
		mapController.setZoom(16.5f);// 设置地图zoom级别

		popupOverlay = new PopupOverlay(mapView, null);

		myLocOverlay = new CustomMyLocationOverlay(mapView, popupOverlay, this);
		ld = new LocationData();
		myLocOverlay.setData(ld);
		mapView.getOverlays().add(myLocOverlay);

		nearbyOverlay = new LocationNearbyOverlay(null, mapView, popupOverlay,
				this);
		AbstractCommonData item = DataConvertFactory.getInstanceEmpty();
		item.putStringValue("location_jc", arr[0]);
		item.putStringValue("location_wz", arr[1]);
		item.putIntValue("lat", lat);
		item.putIntValue("lon", lon);
		nearbyOverlay.addPositionItem(item);
		mapView.getOverlays().add(nearbyOverlay);
		showBubble(item, 500);

		locationClient = new LocationClient(getApplicationContext());
		locationClient.registerLocationListener(this);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");
		option.setCoorType("bd09ll");
		option.setScanSpan(5000);
		option.disableCache(true);
		option.setPoiNumber(5);
		option.setPoiDistance(1000);
		option.setPoiExtraInfo(true);
		locationClient.setLocOption(option);
		locationClient.start();
	}

	@Override
	public void onReceiveLocation(BDLocation location) {
		ld = new LocationData();
		ld.latitude = location.getLatitude();
		ld.longitude = location.getLongitude();
		ld.direction = location.getDerect();
		myLocOverlay.setData(ld);
		mapView.refresh();
	}

	@Override
	public void onReceivePoi(BDLocation arg0) {
	}

	@Override
	protected void onDestroy() {
		mapView.destroy();
		if (mapView != null) {
			mapView.destroy();
			mapView = null;
		}
		locationClient.stop();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		mapView.onPause();
		if (mapView != null) {
			mapManager.stop();
		}
		locationClient.stop();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mapView.onResume();
		if (mapView != null) {
			mapManager.start();
		}
		locationClient.start();
		super.onResume();
	}

	@Override
	public void showBubble(AbstractCommonData data, int upGap) {
		LayoutInflater inflater = (LayoutInflater) mapView.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.location_bubble, null);
		TextView title = (TextView) view.findViewById(R.id.marker_title);
		TextView content = (TextView) view.findViewById(R.id.marker_text);
		title.getPaint().setFakeBoldText(true);
		String jc = data.getStringValue("location_jc");
		String wz = data.getStringValue("location_wz");
		int lat = data.getIntValue("lat");
		int lon = data.getIntValue("lon");
		title.setText(jc);
		content.setText(wz);
		view.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
				MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
		view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
		view.buildDrawingCache();
		popupOverlay.hidePop();
		popupOverlay.showPopup(view.getDrawingCache(), new GeoPoint(
				lat + upGap, lon), 8);
	}

}
