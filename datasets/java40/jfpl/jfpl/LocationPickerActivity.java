package com.jufan.platform.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.cyss.android.lib.AutoActivity;
import com.cyss.android.lib.annotation.InjectionView;
import com.jufan.platform.util.ConstVariables;
import com.jufan.platform.view.CustomMyLocationOverlay;
import com.jufan.platform.view.CustomOverlayInterface;
import com.jufan.platform.view.LocationNearbyOverlay;
import com.lianzt.commondata.AbstractCommonData;
import com.lianzt.commondata.DataConvertFactory;

public class LocationPickerActivity extends AutoActivity implements
		BDLocationListener, CustomOverlayInterface {

	private BMapManager mapManager = null;
	private MKSearch mkSearch = null;
	@InjectionView(id = R.id.bmapsView)
	private MapView mapView;
	@InjectionView(id = R.id.location_send_btn, click = "locationSendBtnClick")
	private android.widget.Button sendBtn;
	@InjectionView(id = R.id.location_loading)
	private LinearLayout loadingLayout;
	@InjectionView(id = R.id.location_list)
	private ListView locationList;

	private CustomMyLocationOverlay myLocOverlay;

	private LocationNearbyOverlay nearbyOverlay;

	private LocationClient locationClient;

	private LocationData ld;

	private GeoPoint mylocationPoint;

	private PopupOverlay popupOverlay;

	private int nearByIndex = 0;

	private boolean isFirstLocation = true;

	private String[] sendAddress = new String[4];

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mapManager = new BMapManager(getApplication());
		mapManager.init(ConstVariables.BAIDU_API_KEY, null);
		mkSearch = new MKSearch();
		mkSearch.init(mapManager, new LocationSearchListener());
		setContentView(R.layout.location);
		setListType(false);

		mapView.setBuiltInZoomControls(true);
		MapController mapController = mapView.getController();
		GeoPoint point = new GeoPoint((int) (34.790 * 1E6),
				(int) (113.675 * 1E6));
		// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mapController.setCenter(point);// 设置地图中心点
		mapController.setZoom(16.5f);// 设置地图zoom级别
		mapController.enableClick(true);

		popupOverlay = new PopupOverlay(mapView, new PopupClickListener() {

			@Override
			public void onClickedPopup(int arg0) {
				popupOverlay.hidePop();
			}
		});
		// mapView.getOverlays().add(popupOverlay);

		locationClient = new LocationClient(getApplicationContext());
		locationClient.registerLocationListener(this);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);
		option.setAddrType("all");
		option.setCoorType("bd09ll");
		option.setScanSpan(5000);
		option.disableCache(false);
		// option.setPoiNumber(5);
		// option.setPoiDistance(1000);
		// option.setPoiExtraInfo(true);
		locationClient.setLocOption(option);
		locationClient.start();

		myLocOverlay = new CustomMyLocationOverlay(mapView, popupOverlay, this);
		ld = new LocationData();
		myLocOverlay.setData(ld);
		mapView.getOverlays().add(myLocOverlay);

		nearbyOverlay = new LocationNearbyOverlay(null, mapView, popupOverlay,
				this);
		mapView.getOverlays().add(nearbyOverlay);
	}

	@Override
	protected void handleListItem(View v, int position) {
		TextView tv = (TextView) v.findViewById(R.id.location_jc);
		tv.getPaint().setFakeBoldText(true);
	}

	@Override
	protected void onListItemClick(String prxName, ListView l, View v,
			int position, long id) {
		AbstractCommonData acd = (AbstractCommonData) v.getTag();
		MapController mapController = mapView.getController();
		int lat = acd.getIntValue("lat");
		int lon = acd.getIntValue("lon");
		GeoPoint point = new GeoPoint(lat, lon);
		// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mapController.animateTo(point);// 设置地图中心点
		mapController.setZoom(17.2f);
		mapController.animateTo(point);// 设置地图中心点
		mapController.setZoom(17.2f);
		int upGap = position == 0 ? 0 : 500;
		showBubble(acd, upGap);
	}

	@Override
	public void onReceiveLocation(BDLocation location) {
		ld = new LocationData();
		ld.latitude = location.getLatitude();
		ld.longitude = location.getLongitude();
		ld.direction = location.getDerect();
		ld.accuracy = location.getRadius();
		myLocOverlay.setData(ld);
		mapView.refresh();
		int lat = (int) (ld.latitude * 1E6);
		int lon = (int) (ld.longitude * 1E6);
		AbstractCommonData myLocData = myLocOverlay.getData();
		if (myLocData != null) {
			myLocData.putIntValue("lat", lat);
			myLocData.putIntValue("lon", lon);
			myLocOverlay.setData(myLocData);
		}
		if (isFirstLocation) {
			MapController mapController = mapView.getController();
			mylocationPoint = new GeoPoint(lat, lon);
			mapController.animateTo(mylocationPoint);
			mapController.setZoom(16.0f);
			mkSearch.reverseGeocode(mylocationPoint);
			isFirstLocation = !isFirstLocation;
		}

		// locationClient.unRegisterLocationListener(this);
	}

	@Override
	public void onReceivePoi(BDLocation location) {

	}

	private void searchNearBy() {
		if (mylocationPoint != null
				&& nearByIndex < ConstVariables.LOCATION_NEAR_BY.length) {
			mkSearch.poiSearchNearBy(
					ConstVariables.LOCATION_NEAR_BY[nearByIndex],
					mylocationPoint, 2000);
			nearByIndex++;
		}
	}

	public void locationSendBtnClick(View v) {
		Intent i = new Intent();
		i.putExtra("address", sendAddress);
		setResult(ConstVariables.SEND_LOCATION_FLAG, i);
		finish();
	}

	public void setSendAddress(String[] address) {
		this.sendAddress = address;
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
		this.sendAddress = new String[] { jc, wz, lat + "", lon + "" };
	}

	class LocationSearchListener implements MKSearchListener {

		@Override
		public void onGetAddrResult(MKAddrInfo result, int iError) {
			// 返回地址信息搜索结果
			if (iError != 0) {
				Toast.makeText(LocationPickerActivity.this, "查询所在位置失败",
						Toast.LENGTH_LONG).show();
				return;
			}
			AbstractCommonData acd = DataConvertFactory.getInstanceEmpty();
			List<AbstractCommonData> list = new ArrayList<AbstractCommonData>();
			AbstractCommonData item = DataConvertFactory.getInstanceEmpty();
			item.putStringValue("location_jc", "【我的位置】");
			item.putStringValue("location_wz", result.strAddr);
			item.putIntValue("lat", mylocationPoint.getLatitudeE6());
			item.putIntValue("lon", mylocationPoint.getLongitudeE6());
			list.add(item);
			myLocOverlay.setData(item);
			showBubble(item, 0);
			acd.putArrayValue("location_list", list);
			fillData(acd, getWindow().getDecorView(), "");
			sendAddress = new String[] { "我的位置", result.strAddr,
					mylocationPoint.getLatitudeE6() + "",
					mylocationPoint.getLongitudeE6() + "" };
			sendBtn.setEnabled(true);
			loadingLayout.setVisibility(View.GONE);
			locationList.setVisibility(View.VISIBLE);
			searchNearBy();
		}

		@Override
		public void onGetDrivingRouteResult(MKDrivingRouteResult result,
				int iError) {
			// 返回驾乘路线搜索结果
		}

		@Override
		public void onGetPoiResult(MKPoiResult res, int type, int iError) {
			if (iError == MKEvent.ERROR_RESULT_NOT_FOUND) {
				Toast.makeText(LocationPickerActivity.this, "查询附近地点失败",
						Toast.LENGTH_LONG).show();
				return;
			}

			mapView.refresh();
			AbstractCommonData acd = DataConvertFactory.getInstanceEmpty();
			List<AbstractCommonData> list = new ArrayList<AbstractCommonData>();
			acd.putArrayValue("location_list", list);
			int num = 0;
			for (MKPoiInfo info : res.getAllPoi()) {
				if (info.pt != null && num < 5) {
					AbstractCommonData item = DataConvertFactory
							.getInstanceEmpty();
					item.putStringValue("location_jc", info.name);
					item.putStringValue("location_wz", info.address);
					item.putIntValue("lat", info.pt.getLatitudeE6());
					item.putIntValue("lon", info.pt.getLongitudeE6());
					list.add(item);
					nearbyOverlay.addPositionItem(item);
					num++;
				}
			}
			fillData(acd, getWindow().getDecorView(), "");
			searchNearBy();
		}

		@Override
		public void onGetTransitRouteResult(MKTransitRouteResult result,
				int iError) {
			// 返回公交搜索结果
		}

		@Override
		public void onGetWalkingRouteResult(MKWalkingRouteResult result,
				int iError) {
			// 返回步行路线搜索结果
		}

		@Override
		public void onGetBusDetailResult(MKBusLineResult result, int iError) {
			// 返回公交车详情信息搜索结果
		}

		@Override
		public void onGetSuggestionResult(MKSuggestionResult result, int iError) {
			// 返回联想词信息搜索结果
		}

		@Override
		public void onGetShareUrlResult(MKShareUrlResult result, int type,
				int error) {
			// 在此处理短串请求返回结果.
		}

		@Override
		public void onGetPoiDetailSearchResult(int arg0, int arg1) {
			// TODO Auto-generated method stub

		}

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

}
