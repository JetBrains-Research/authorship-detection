package wyf.ytl.data;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import wyf.ytl.HDZGActivity;
import wyf.ytl.R;
import wyf.ytl.R.raw;
import wyf.ytl.entity.CityDrawable;
import wyf.ytl.entity.General;
import wyf.ytl.entity.Hero;
import wyf.ytl.entity.HeroBackDataThread;
import wyf.ytl.entity.HeroGoThread;
import wyf.ytl.entity.MyDrawable;
import wyf.ytl.entity.Skill;
import wyf.ytl.view.GameView;

import android.media.MediaPlayer;

public class SerializableGame {
	
	public SerializableGame(){
		
	}
	
	//保存游戏的方法
	public static void saveGameStatus(GameView gameView){
		OutputStream out = null;
		ObjectOutputStream  oout = null;
		try{
			out = gameView.getContext().openFileOutput("game.ytl", 0);
			oout = new ObjectOutputStream(out);
		
			
			oout.writeObject(gameView.hero);//保存英雄对象			
			oout.writeInt(gameView.startRow);//屏幕在大地图中的行数
			oout.writeInt(gameView.startCol);//屏幕在大地图中的列数
			oout.writeObject(gameView.skillToLearn);//将要学习的技能
			oout.writeObject(gameView.allCityDrawable);//存放所有敌方的城池
			oout.writeObject(gameView.freeGeneral);//自由的将领,即不属于我方和敌方的
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				oout.close();
				out.close();				
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	//加载游戏的方法
	@SuppressWarnings("unchecked")
	public static void loadingGameStatus(GameView gameView){	
		InputStream in = null;
		ObjectInputStream oin = null;
		try{
			in = gameView.getContext().openFileInput("game.ytl");
			oin = new ObjectInputStream(in);
		
			gameView.hero.ht.flag=false;
			gameView.hero.ht.isGameOn=false;
			gameView.hero.ht.interrupt();
			gameView.hero.hbdt.flag=false;
			gameView.hero.hbdt.interrupt();
			
			try{
				Thread.sleep(200);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			if(gameView.activity.loadingView.process<90)
			{
				gameView.activity.loadingView.process = 90;
			}
			
			gameView.hero = (Hero) oin.readObject();//读取英雄对象		
			
			gameView.startRow = oin.readInt();//屏幕在大地图中的行数
			gameView.startCol = oin.readInt();//屏幕在大地图中的列数
			gameView.skillToLearn = (ArrayList<Skill>) oin.readObject();//将要学习的技能
			gameView.allCityDrawable = (ArrayList<CityDrawable>) oin.readObject();//存放所有敌方的城池
			gameView.freeGeneral = (ArrayList<General>) oin.readObject();//自由的将领,即不属于我方和敌方的
			
			MyDrawable[][] mba=gameView.layerList.layers.get(1).getMapMatrix();
			for(CityDrawable c : gameView.allCityDrawable){
				int colT=c.col;
			    int rowT=c.row;
			    
			    c.bmpSelf=((CityDrawable)mba[rowT][colT]).bmpSelf;
			    c.meetable=((CityDrawable)mba[rowT][colT]).meetable;
			    c.width=((CityDrawable)mba[rowT][colT]).width;
			    c.height=((CityDrawable)mba[rowT][colT]).height;
			    c.col=((CityDrawable)mba[rowT][colT]).col;
			    c.row=((CityDrawable)mba[rowT][colT]).row;
			    c.refCol=((CityDrawable)mba[rowT][colT]).refCol;
			    c.refRow=((CityDrawable)mba[rowT][colT]).refRow;
			    c.noThrough=((CityDrawable)mba[rowT][colT]).noThrough;
			    c.meetableMatrix=((CityDrawable)mba[rowT][colT]).meetableMatrix;
			    c.bmpDialogBack=((CityDrawable)mba[rowT][colT]).bmpDialogBack;
			    c.bmpDialogButton=((CityDrawable)mba[rowT][colT]).bmpDialogButton;				    
			    
			    mba[rowT][colT]=c;			    
			}
			
			for(CityDrawable c : gameView.hero.cityList){
				int colT=c.col;
			    int rowT=c.row;		

			    c.bmpSelf=((CityDrawable)mba[rowT][colT]).bmpSelf;
			    c.meetable=((CityDrawable)mba[rowT][colT]).meetable;
			    c.width=((CityDrawable)mba[rowT][colT]).width;
			    c.height=((CityDrawable)mba[rowT][colT]).height;
			    c.col=((CityDrawable)mba[rowT][colT]).col;
			    c.row=((CityDrawable)mba[rowT][colT]).row;
			    c.refCol=((CityDrawable)mba[rowT][colT]).refCol;
			    c.refRow=((CityDrawable)mba[rowT][colT]).refRow;
			    c.noThrough=((CityDrawable)mba[rowT][colT]).noThrough;
			    c.meetableMatrix=((CityDrawable)mba[rowT][colT]).meetableMatrix;
			    c.bmpDialogBack=((CityDrawable)mba[rowT][colT]).bmpDialogBack;
			    c.bmpDialogButton=((CityDrawable)mba[rowT][colT]).bmpDialogButton;	
			    
			    mba[rowT][colT]=c;	
			}
			
			((MeetableLayer)gameView.layerList.layers.get(1)).initMapMatrixForMeetable();
			
			//恢复英雄相关信息
			gameView.hero.father = gameView;
			gameView.hero.initAnimationSegment(GameView.heroAnimationSegments);//为英雄初始化动画段列表
			gameView.hero.startAnimation();//启动英雄动画
			gameView.hero.hgt = new HeroGoThread(gameView,gameView.hero);
			gameView.drawThread.setIsViewOn(true);
			 
			gameView.hero.hbdt=new HeroBackDataThread(gameView.hero);
			gameView.hero.hbdt.start();
			
			//gameView.mMediaPlayer.stop();
			if(!gameView.mMediaPlayer.isPlaying())
			{
				gameView.mMediaPlayer = MediaPlayer.create(gameView.activity, R.raw.backsound);
				gameView.mMediaPlayer.setLooping(true);
				if(gameView.activity.isBackSound){
					gameView.mMediaPlayer.start();
		    	}				
			}
			
			gameView.activity.loadingView.process = 101;
			gameView.activity.myHandler.sendEmptyMessage(100);
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				oin.close();
				in.close();
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public static boolean check(HDZGActivity h){//检查文件是否存在
		try{
			h.openFileInput("game.ytl");
		}
		catch(Exception e){
			return false;
		}
		return true;
	}
}
