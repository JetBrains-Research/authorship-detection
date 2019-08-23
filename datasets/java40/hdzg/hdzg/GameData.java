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
 * �����ṩ��Ϸ�е�����
 */
public class GameData{
	//����MyDrawable�����������ʼ��
	public static Resources resources;//resources������
	
	
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
	//��ά����Ϊһ��MyDrawable���������
		mapData = new MyDrawable [40][60];
		try {
			InputStream in = resources.getAssets().open("maps.so");
			DataInputStream din = new DataInputStream(in);
			int totalBlocks = din.readInt();
			
			for(int i=0; i<totalBlocks; i++){
				int outBitmapInxex = din.readByte();
				int kyf=din.readByte();//������0��������
				int w = din.readByte();//ͼԪ�Ŀ��
				int h = din.readByte();//ͼԪ�ĸ߶�
				int col = din.readByte();//������
				int row = din.readByte();//������
				int pCol = din.readByte();//ռλ��
				int pRow = din.readByte();//ռλ��
				
				int bktgCount=din.readByte();////����ͨ���������
				int[][] notIn=new int[bktgCount][2];
				
				for(int j=0; j<bktgCount; j++){//���벻��ͨ���� 
					notIn[j][0] = din.readByte();
					notIn[j][1] = din.readByte();
				}
				
				mapData[row][col]=new MyDrawable(
						bitmaps[outBitmapInxex], 
						((kyf==0)?false:true), //�������־λ
						w, 
						h, 
						col, 
						row, 
						pCol, 
						pRow, 
						notIn
				);
			}
			din.close();//�ر���
			in.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}