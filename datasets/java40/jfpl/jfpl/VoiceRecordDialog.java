package com.jufan.platform.view;

import java.io.File;
import java.io.IOException;

import com.cyss.android.lib.util.FileUtil;
import com.cyss.android.lib.util.SystemUtil;
import com.jufan.platform.ui.R;

import android.app.Dialog;
import android.content.Context;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class VoiceRecordDialog extends Dialog {

	private Context ctx;

	private TextView tipTv;
	private ImageView volumeIv1;
	private ImageView volumeIv2;
	private ImageView volumeIv3;
	private ImageView[] volumeArr;

	private int state = 0;

	private MediaRecorder mediaRecorder;
	private static final int BASE = 600;
	private String lastVoicePath;

	private Handler handler = new Handler();
	private Runnable showVoiceVolume = new Runnable() {

		@Override
		public void run() {
			if (mediaRecorder != null && volumeArr != null) {
				int ratio = mediaRecorder.getMaxAmplitude() / BASE;
				int db = 0;// 分贝
				if (ratio > 1) {
					db = (int) (20 * Math.log10(ratio));
				}
				setVolumeState(db / 4);
				handler.postDelayed(showVoiceVolume, 300);
			}
		}
	};

	public VoiceRecordDialog(Context context) {
		super(context, R.style.frame_alert_dialog);
		this.ctx = context;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.record_voice);
		Window dialogWindow = getWindow();
		dialogWindow.setGravity(Gravity.CENTER);
		tipTv = (TextView) findViewById(R.id.cancel_record_tip1);
		volumeIv1 = (ImageView) findViewById(R.id.voice_volume1);
		volumeIv2 = (ImageView) findViewById(R.id.voice_volume2);
		volumeIv3 = (ImageView) findViewById(R.id.voice_volume3);
		volumeArr = new ImageView[] { volumeIv1, volumeIv2, volumeIv3 };
	}

	@Override
	public void show() {
		super.show();
		startRecord();
	}

	@Override
	public void dismiss() {
		super.dismiss();
		if (this.state == 0) {
			endRecord();
		} else if (this.state == 1) {
			cancelRecord();
			this.lastVoicePath = "";
		}
	}

	public void setCancelState(int flag) {
		if (state == flag) {
			return;
		}
		try {
			switch (flag) {
			case 0:
				tipTv.setText(ctx.getString(R.string.cancel_record_tip1));
				// this.mediaRecorder.stop();
				break;
			case 1:
				tipTv.setText(ctx.getString(R.string.cancel_record_tip2));
				// this.mediaRecorder.start();
				break;
			default:
				break;
			}
		} catch (Exception ex) {
			Log.d(SystemUtil.LOG_MSG, "call after dialog.show() ");
		}
		this.state = flag;
	}

	public int getCancelState() {
		return this.state;
	}

	private void startRecord() {
		if (this.mediaRecorder == null) {
			this.mediaRecorder = new MediaRecorder();
			this.mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			this.mediaRecorder
					.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
			this.lastVoicePath = FileUtil.getNewFilePath("voice",
					SystemUtil.getUniqueId() + ".amr");
			this.mediaRecorder.setOutputFile(this.lastVoicePath);
			this.mediaRecorder
					.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
			this.mediaRecorder.setMaxDuration(60 * 1000);
			this.mediaRecorder
					.setOnInfoListener(new MediaRecorder.OnInfoListener() {

						@Override
						public void onInfo(MediaRecorder mr, int what, int extra) {
							if (what == MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {

							}
						}
					});
		}
		try {
			this.mediaRecorder.prepare();
		} catch (IOException e) {
			Log.e(SystemUtil.LOG_MSG, "prepare() failed");
		}
		this.mediaRecorder.start();
		this.handler.postDelayed(showVoiceVolume, 300);
	}

	private void endRecord() {
		try {
			if (this.mediaRecorder != null) {
				this.mediaRecorder.stop();
				this.mediaRecorder.release();
				this.mediaRecorder = null;
			}
		} catch (Exception ex) {
			SystemUtil.printException(ex, "w");
		}
	}

	private void setVolumeState(int _db) {
		int db = _db > 3 ? 3 : _db;
		for (int i = 0; i < db; i++) {
			volumeArr[i].setVisibility(View.VISIBLE);
		}
		for (int i = db; i < 3; i++) {
			volumeArr[i].setVisibility(View.GONE);
		}
	}

	public String getLastVoicePath() {
		return this.lastVoicePath;
	}

	public void cancelRecord() {
		endRecord();
		try {
			File f = new File(this.lastVoicePath);
			f.delete();
		} catch (Exception ex) {
			SystemUtil.printException(ex, "w");
		}
	}
}
