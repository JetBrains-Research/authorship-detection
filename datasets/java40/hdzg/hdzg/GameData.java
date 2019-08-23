package wyf.ytl.data;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;

import wyf.ytl.R;
import wyf.ytl.R.drawable;
import wyf.ytl.entity.MyDrawable;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/*
 * 该类提供游戏中的数据
 */
public class GameData{
	//各种MyDrawable对象在这里初始化
	public static Resources resources;//resources的引用
	
	
	static Bitmap  grassBitmap;
	static Bitmap xiaoHua1Bitmap;
	static Bitmap muZhuangBitmap;
	static Bitmap xiaoHua2Bitmap;
	static Bitmap roadBitmap;
	static Bitmap jingBitmap;	
	static Bitmap[] bitmaps;	
	public static MyDrawable [][] mapData;

	
	public static void initMapImage(){
		grassBitmap = BitmapFactory.decodeResource(resources, R.drawable.caodi);
		xiaoHua1Bitmap = BitmapFactory.decodeResource(resources, R.drawable.hua1);
		muZhuangBitmap = BitmapFactory.decodeResource(resources, R.drawable.muzhuang);
		xiaoHua2Bitmap = BitmapFactory.decodeResource(resources, R.drawable.hua2);
		roadBitmap = BitmapFactory.decodeResource(resources, R.drawable.gonglu);
		jingBitmap = BitmapFactory.decodeResource(resources, R.drawable.jing);		
		
		bitmaps=new Bitmap[]{
			grassBitmap,
			xiaoHua1Bitmap,
			muZhuangBitmap,
			xiaoHua2Bitmap,
			roadBitmap,
			jingBitmap
        };
	}

	public static void initMapData(){	
	//二维数组为一个MyDrawable对象的引用
		mapData = new MyDrawable [40][60];
		try {
			InputStream in = resources.getAssets().open("maps.so");
			DataInputStream din = new DataInputStream(in);
			int totalBlocks = din.readInt();
			
			for(int i=0; i<totalBlocks; i++){
				int outBitmapInxex = din.readByte();
				int kyf=din.readByte();//可遇否，0—不可遇
				int w = din.readByte();//图元的宽度
				int h = din.readByte();//图元的高度
				int col = din.readByte();//总列数
				int row = din.readByte();//总行数
				int pCol = din.readByte();//占位列
				int pRow = din.readByte();//占位行
				
				int bktgCount=din.readByte();////不可通过点的数量
				int[][] notIn=new int[bktgCount][2];
				
				for(int j=0; j<bktgCount; j++){//读入不可通过点 
					notIn[j][0] = din.readByte();
					notIn[j][1] = din.readByte();
				}
				
				mapData[row][col]=new MyDrawable(
						bitmaps[outBitmapInxex], 
						((kyf==0)?false:true), //可遇否标志位
						w, 
						h, 
						col, 
						row, 
						pCol, 
						pRow, 
						notIn
				);
			}
			din.close();//关闭流
			in.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}