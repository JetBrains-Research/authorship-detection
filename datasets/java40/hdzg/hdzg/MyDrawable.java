package wyf.ytl.entity;							//声明包语句
import static wyf.ytl.tool.ConstantUtil.TILE_SIZE;

import java.io.Externalizable;						//引入相关类
import java.io.IOException;							//引入相关类
import java.io.ObjectInput;							//引入相关类
import java.io.ObjectOutput;						//引入相关类
import android.graphics.Bitmap;						//引入相关类
import android.graphics.Canvas;						//引入相关类
/*
 * 该类中封装了一个地图图元的信息，每个MyDrawable类是在地图上占有一个格子，
 * 该类中包含图片引用，图片宽高，图片位置（row，col），定位点坐标，不可通过矩阵
 * 以及是否可遇的标志位
 */
public class MyDrawable implements Externalizable{
	private static final long serialVersionUID = 919144009679011682L;	
	public Bitmap bmpSelf;//自己图片的引用
	public int width;//图元的宽度
	public int height;//图元的高度
	public int col;//在大地图中所在的列
	public int row;//在大地图中所在的行
	public int refCol;//定位参考点在本MyDrawable中所占的列，以左下角为原点
	public int refRow;//定位参考点在本MyDrawable中所占的行，以左下角为原点
	public int [][] noThrough;//不可通过矩阵
	public boolean meetable;//是否可以遇到
	
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(width);//图元的宽度
		out.writeInt(height);//图元的高度
		out.writeInt(col);//在大地图中所在的列
		out.writeInt(row);//在大地图中所在的行
		out.writeInt(refCol);//定位参考点在本MyDrawable中所占的列，以左下角为原点
		out.writeInt(refRow);//定位参考点在本MyDrawable中所占的行，以左下角为原点
		out.writeObject(noThrough);//不可通过矩阵
		out.writeBoolean(meetable);//是否可以遇到
	}
	public void readExternal(ObjectInput in) throws IOException,
			ClassNotFoundException {
		width = in.readInt();
		height = in.readInt();
		col = in.readInt();
		row = in.readInt();
		refCol = in.readInt();
		refRow = in.readInt();
		noThrough = (int[][]) in.readObject();
		meetable = in.readBoolean();
	}
	public MyDrawable(){}//无参构造器
	//构造器
	public MyDrawable(Bitmap bmpSelf,boolean meetable,int width,int height,int col,int row,int refCol,int refRow,int [][] noThrough){
		this.bmpSelf = bmpSelf;
		this.width = width;
		this.height = height;
		this.col = col;
		this.row = row;
		this.refCol = refCol;
		this.refRow = refRow;
		this.noThrough = noThrough;
		this.meetable = meetable;
	}
	//方法：绘制自己
	public void drawSelf(Canvas canvas,int screenRow, int screenCol,int offsetX,int offsetY){
		int x = (screenCol-refCol)*TILE_SIZE;//求出自己所拥有的块数中左上角块的x坐标
		int y = screenRow*TILE_SIZE+(refRow+1)*TILE_SIZE-height;//求出自己所拥有的块数中左上角块的y坐标
		canvas.drawBitmap(bmpSelf, x-offsetX, y-offsetY, null);//根据自己的左上角的xy坐标画出自己
	}
}