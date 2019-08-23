package com.jufan.platform.view;

import com.jufan.platform.ui.R;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class VoicePlayItem extends LinearLayout implements
		View.OnClickListener, MediaPlayer.OnCompletionListener,
		MediaPlayer.OnPreparedListener {

	private Context ctx;
	private String audioPath;
	private MediaPlayer mediaPlayer;
	private boolean isPlay = false;
	private Handler handler = new Handler();
	private ImageView voiceImage;
	private TextView secondsTv;
	private int flag = 0;

	private Runnable playVoice = new Runnable() {

		@Override
		public void run() {
			if (isPlay) {
				if (flag == 0) {
					voiceImage
							.setImageResource(R.drawable.bottle_receiver_voice_node_playing001);
				} else if (flag == 1) {
					voiceImage
							.setImageResource(R.drawable.bottle_receiver_voice_node_playing002);
				} else if (flag == 2) {
					voiceImage
							.setImageResource(R.drawable.bottle_receiver_voice_node_playing003);
				}
				flag++;
				if (flag > 2) {
					flag = 0;
				}
				handler.postDelayed(playVoice, 500);
			}
		}
	};

	public VoicePlayItem(Context context, AttributeSet as) {
		super(context, as);
		init(context);
	}

	public VoicePlayItem(Context context) {
		super(context);
		init(context);
	}
	
	public void setBindEventView(View v) {
		v.setOnClickListener(this);
	}

	private void init(Context context) {
		this.ctx = context;
		this.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		View v = LayoutInflater.from(ctx).inflate(R.layout.voice_play_item,
				null);
		this.addView(v);
//		this.setOnClickListener(this);
		this.setGravity(Gravity.CENTER_VERTICAL);
		voiceImage = (ImageView) v.findViewById(R.id.play_voice_image);
		secondsTv = (TextView) v.findViewById(R.id.play_voice_seconds);
		this.mediaPlayer = new MediaPlayer();
		this.mediaPlayer.setOnCompletionListener(this);
		this.mediaPlayer.setOnPreparedListener(this);
	}

	public void setPlaySource(String filePath) {
		this.audioPath = filePath;
		try {
			this.mediaPlayer.setDataSource(this.audioPath);
			this.mediaPlayer.prepare();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		if (isPlay) {
			endPlay();
		} else {
			this.mediaPlayer = new MediaPlayer();
			this.mediaPlayer.setOnCompletionListener(this);
			this.mediaPlayer.setOnPreparedListener(this);
			try {
				this.mediaPlayer.setDataSource(this.audioPath);
				this.mediaPlayer.prepare();
			} catch (Exception e) {
				e.printStackTrace();
			}
			this.mediaPlayer.start();
			this.isPlay = !isPlay;
			handler.postDelayed(playVoice, 1000);
		}
	}

	private void endPlay() {
		try {
			mediaPlayer.reset();
			mediaPlayer.stop();
			mediaPlayer.release();
			this.mediaPlayer = null;
		} catch (Exception ex) {

		}
		voiceImage
				.setImageResource(R.drawable.bottle_receiver_voice_node_playing003);
		this.isPlay = false;
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		endPlay();

	}

	@Override
	public void onPrepared(MediaPlayer mp) {
		String sec = mp.getDuration() / 1000.0f + "";
		sec.substring(0, 3);
		secondsTv.setText(sec + "'");
	}
}
