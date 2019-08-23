package com.weparty.fragment;



import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.weparty.R;
import com.weparty.utils.Constant;
import com.weparty.views.GuideActivity_;
import com.weparty.views.LaunchActivity_;
import com.weparty.widgets.homebtn.HomeClickListener;
import com.weparty.widgets.homebtn.LaunchActButton;


/**
 * 
 * <ul>
 * <li><b>name : </b>		FragScence		</li>
 * <li><b>description :</b>	首页场景				</li>
 * <li><b>author : </b>		yelingh			    </li>
 * <li><b>date : </b>		2013-8-30 下午3:27:59		</li>
 * </ul>
 */

public class FragScence extends Fragment {
	
	
	public FragScence() {
	
	}
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_main,container,false);
		initSetListener(view);
		return view;
	}
	
	
	void action(int flags){
		Intent intent =  new Intent(getActivity(),LaunchActivity_.class);
		intent.putExtra("flag",flags);
		startActivity(intent);
		getActivity().overridePendingTransition(R.anim.roll_up, R.anim.roll);
	}
	
	@Override
	public void onResume() {
		super.onResume();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}
	
	private void initSetListener(View view){
		
		LaunchActButton taotieLAB =  (LaunchActButton) view.findViewById(R.id.taotie_lab);
		taotieLAB.setOnHomeClick(new HomeClickListener() {
			
			@Override
			public void onclick() {
				action(Constant.TYPE_TAOTIE);
			}
		});
		
		LaunchActButton mingjingLAB =  (LaunchActButton) view.findViewById(R.id.mijing_lab);
		mingjingLAB.setOnHomeClick(new HomeClickListener() {
			
			@Override
			public void onclick() {
				action(Constant.TYPE_MINGJING);
			}
		});
		
		LaunchActButton wujieLab =  (LaunchActButton) view.findViewById(R.id.wujie_lab);
		wujieLab.setOnHomeClick(new HomeClickListener() {
			
			@Override
			public void onclick() {
				action(Constant.TYPE_WUJIE);
			}
		});
		
		LaunchActButton guangyingLab =  (LaunchActButton) view.findViewById(R.id.guangying_lab);
		guangyingLab.setOnHomeClick(new HomeClickListener() {
			
			@Override
			public void onclick() {
				action(Constant.TYPE_GUANGYING);
			}
		});
		
		LaunchActButton yexingLab =  (LaunchActButton) view.findViewById(R.id.yexing_lab);
		yexingLab.setOnHomeClick(new HomeClickListener() {
			
			@Override
			public void onclick() {
				action(Constant.TYPE_YEXING);
			}
		});
		
		LaunchActButton helpLab =  (LaunchActButton) view.findViewById(R.id.help_lab);
		helpLab.setOnHomeClick(new HomeClickListener() {
			
			@Override
			public void onclick() {
				new GuideActivity_.IntentBuilder_(getActivity()).start();
				//action(Constant.TYPE_HELP);
			}
		});
	}
	
	
	
	
}
