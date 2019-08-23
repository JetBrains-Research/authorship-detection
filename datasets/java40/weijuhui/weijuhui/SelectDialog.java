package com.weparty.widgets;

import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.weparty.R;

/**
 * @author ping
 * @Describle 选择人数,或者平均消费
 */
public class SelectDialog extends Dialog implements OnItemClickListener {
	private static final String TAG = SelectDialog.class.getSimpleName();
	private MyDialogListener dialogListener;
	private Context mContext;
	private List<String> dataList;
	private ListView listView;
	private String data;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public SelectDialog(Context context, MyDialogListener dialogListener) {
		super(context);
		this.mContext = context;
		this.dialogListener = dialogListener;
	}

	public SelectDialog(Context context, List<String> dataList) {
		super(context);
		this.mContext = context;
		this.dataList = dataList;
	}

	public SelectDialog(Context context, int theme) {
		super(context, theme);
		this.mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.dialog_select_people);
		listView = (ListView) this.findViewById(R.id.lv_select_people);
		// listView.setAdapter(new ArrayAdapter<String>(mContext,
		// R.layout.item_text, R.id.tv_item_name, getData()));
		listView.setAdapter(new ArrayAdapter<String>(mContext,
				android.R.layout.simple_list_item_1, dataList));
		listView.setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (dataList.get(position).trim() != null) {
			setData(dataList.get(position).trim());
			dismiss();
		}

	}

	public interface MyDialogListener {
		public void onOkClick(String name);

		public void onCancelClick();
	}
}
