package com.ghimtim.dice;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
    private ImageView mImgRollingDice;
    private TextView mTxtDiceResult;
    private Button mBtnRollDice;
    private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			int iRand = (int) (Math.random()*6+1);
			String s = getString(R.string.dice_result);
			mTxtDiceResult.setText(s+iRand);
			showNotification(s+iRand,""+iRand);
			switch (iRand) {
			case 1:
				mImgRollingDice.setImageResource(R.drawable.dice01);
				break;
			case 2:
				mImgRollingDice.setImageResource(R.drawable.dice02);
				break;
			case 3:
				mImgRollingDice.setImageResource(R.drawable.dice03);
				break;
			case 4:
				mImgRollingDice.setImageResource(R.drawable.dice04);
				break;
			case 5:
				mImgRollingDice.setImageResource(R.drawable.dice05);
				break;
			case 6:
				mImgRollingDice.setImageResource(R.drawable.dice06);
				break;
			}
		}



	
    };
	private void showNotification(String s,String irand) {
		// TODO Auto-generated method stub
		NotificationManager noteMng = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
		Notification noti;
		
		Notification.Builder builder = new Notification.Builder(this).setTicker("骰子"+s)
				.setSmallIcon(getResources().getIdentifier("dice0"+irand,"drawable",getPackageName()));
		noti = builder.setContentTitle("骰子点数结果").setContentText(s).getNotification();
		noteMng.notify(10, noti);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}
	private void init() {
		// TODO Auto-generated method stub
		mImgRollingDice = (ImageView) findViewById(R.id.imgRollingDice);
		mTxtDiceResult = (TextView) findViewById(R.id.txtDiceResult);
		mBtnRollDice = (Button) findViewById(R.id.btnRollDice);
		
		mBtnRollDice.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				Animation btnanim = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate);
				mBtnRollDice.startAnimation(btnanim);
				ObjectAnimator animBtnRotate = ObjectAnimator.ofFloat(mTxtDiceResult, "rotation", 0,-360);
				animBtnRotate.setDuration(1000);
				animBtnRotate.start();
				String s = getString(R.string.dice_result);
				mTxtDiceResult.setText(s);
				final AnimationDrawable animDraw = new AnimationDrawable();
				animDraw.setOneShot(false);
				Resources res = getResources();
				for(int i=0;i<6;i++){
				int iRand = (int) (Math.random()*6+1);
				String num = new String();
				num = "dice0"+iRand;
//				Log.i("LJT", "******"+num);
//				Log.i("LJT", "******"+res.getIdentifier(num,"drawable",getPackageName()));
				animDraw.addFrame(res.getDrawable(res.getIdentifier(num,"drawable",getPackageName())), 100);
				}
				mImgRollingDice.setImageDrawable(animDraw);
				animDraw.start();
				//启动后台thread进行计时；
				new Thread(new Runnable() {
					
					@Override
					public void run() {
					    try {
							Thread.sleep(1000);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						animDraw.stop();
//						Log.i("LJT", "******stop");
						handler.sendMessage(handler.obtainMessage());
					}
				}).start();
				
			}
		});

		}

}
