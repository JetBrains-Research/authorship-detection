package com.weparty.views;

import java.util.Random;

import com.googlecode.androidannotations.annotations.AfterTextChange;
import com.googlecode.androidannotations.annotations.AfterViews;
import com.googlecode.androidannotations.annotations.Click;
import com.googlecode.androidannotations.annotations.EActivity;
import com.googlecode.androidannotations.annotations.Extra;
import com.googlecode.androidannotations.annotations.ViewById;
import com.umeng.analytics.MobclickAgent;
import com.weparty.R;
import com.weparty.domain.SourceData;
import com.weparty.utils.Constant;
import com.weparty.widgets.KeywordsFlow;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

@EActivity(R.layout.act_edit_content)
public class EditActivity extends Activity implements OnClickListener {

	@Extra
	int flag;
	
	@Extra
	String value;
	
	@ViewById(R.id.title_center)
	TextView title;

	@ViewById(R.id.details_dt)
	EditText details_dt;

	@ViewById(R.id.keywordsflow)
	KeywordsFlow keywordsFlow;

	@ViewById(R.id.title_sub_bar)
	RelativeLayout title_sub_bar;
	
	@AfterTextChange(R.id.details_dt)
	void detailsChange(Editable s){
		Spannable spanText = (Spannable) s;
		Selection.setSelection(spanText, s.length());
	}
	
	@AfterViews
	void afterViews() {
		if (flag != -1) {
			title_sub_bar.setBackgroundResource(SourceData.getTheme());
			details_dt.setText(value);
			initState(flag);
		}
	}

	@Click(R.id.title_pos)
	void pos() {
		Intent intent = new Intent(EditActivity.this, LaunchActivity.class);
		intent.putExtra("value", details_dt.getText().toString());
		setResult(flag, intent);
		finish();
	}

	@Click(R.id.title_back)
	void back() {
		EditActivity.this.finish();
	}

	private void initKeywordsFlow(String[] keywords) {
		keywordsFlow.setDuration(800l);
		keywordsFlow.setOnItemClickListener(this);
		feedKeywordsFlow(keywordsFlow, keywords);
		keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);
	}

	private static void feedKeywordsFlow(KeywordsFlow keywordsFlow, String[] arr) {
		Random random = new Random();
		for (int i = 0; i < KeywordsFlow.MAX; i++) {
			int ran = random.nextInt(arr.length);
			String tmp = arr[ran];
			keywordsFlow.feedKeyword(tmp);
		}
	}

	private void initState(int flag) {
		switch (flag) {
		case Constant.ITEM_VALUE_TITLE:
			title.setText("编辑标题");
			initKeywordsFlow(buildTitleKeyword());
			break;
		case Constant.ITEM_VALUE_LOCATION:
			title.setText("编辑地点");
			initKeywordsFlow(buildLocationKeyword());
			break;
		case Constant.ITEM_VALUE_DETAILS:
			title.setText("编辑详情");
			initKeywordsFlow(buildDetailsKeyword());
			break;
		default:
			title.setText("编辑文本");
			break;
		}
	}

	private String[] buildTitleKeyword() {
		return new String[] { "老友", "同事", "哥们", "闺蜜", "社团", "寝室", "同学", "组织",
				"叙旧", "聚会", "Party", "组会", "聊天", "侃大山", "放松", "看球", "组团", "通宵",
				"刷夜" };
	}

	private String[] buildLocationKeyword() {
		return new String[] { "就近噢", "老地方", "你懂的", "看大家方便", "学校附近", "家附近",
				"地铁附近", "西单附近", "五道口", "朝阳大悦城", "三元桥", " 国贸附近", "大望路", "蓝色港湾",
				"东单附近", "王府井", "西直门", "东直门", "簋街附近", "三里屯 ", "工体附近", "世贸天阶",
				"后海啊", "南锣鼓巷", "鼓楼大街" };

	}

	private String[] buildDetailsKeyword() {
		return new String[] { "大家可以提其他活动方案", "其实希望能有一系列活动", "想死你们了", "大家一定要来哦",
				"我请客你们可要给面子啊", "确定下人数我好订位置", "如果刮风下雨我们就改室内", "这家最近有团购", "不见不散",
				"风雨无阻", "因为喝酒，不许开车 ", "谈正事" };

	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
		hideKeyboard();
	}

	private void hideKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(details_dt.getWindowToken(), 0);
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id != R.id.title_back && id != R.id.title_pos) {
			if (v instanceof TextView) {

				String key = ((TextView) v).getText().toString();
				details_dt.setText(details_dt.getText().toString() + key);
				keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);
			}
		}

	}
}
