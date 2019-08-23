package wyf.ytl.entity;							//���������
import static wyf.ytl.tool.ConstantUtil.TILE_SIZE;

import java.io.Externalizable;						//���������
import java.io.IOException;							//���������
import java.io.ObjectInput;							//���������
import java.io.ObjectOutput;						//���������
import android.graphics.Bitmap;						//���������
import android.graphics.Canvas;						//���������
/*
 * �����з�װ��һ����ͼͼԪ����Ϣ��ÿ��MyDrawable�����ڵ�ͼ��ռ��һ�����ӣ�
 * �����а���ͼƬ���ã�ͼƬ��ߣ�ͼƬλ�ã�row��col������λ�����꣬����ͨ������
 * �Լ��Ƿ�����ı�־λ
 */
public class MyDrawable implements Externalizable{
	private static final long serialVersionUID = 919144009679011682L;	
	public Bitmap bmpSelf;//�Լ�ͼƬ������
	public int width;//ͼԪ�Ŀ��
	public int height;//ͼԪ�ĸ߶�
	public int col;//�ڴ��ͼ�����ڵ���
	public int row;//�ڴ��ͼ�����ڵ���
	public int refCol;//��λ�ο����ڱ�MyDrawable����ռ���У������½�Ϊԭ��
	public int refRow;//��λ�ο����ڱ�MyDrawable����ռ���У������½�Ϊԭ��
	public int [][] noThrough;//����ͨ������
	public boolean meetable;//�Ƿ��������
	
	public void writeExternal(ObjectOutput out) throws IOException {
		out.writeInt(width);//ͼԪ�Ŀ��
		out.writeInt(height);//ͼԪ�ĸ߶�
		out.writeInt(col);//�ڴ��ͼ�����ڵ���
		out.writeInt(row);//�ڴ��ͼ�����ڵ���
		out.writeInt(refCol);//��λ�ο����ڱ�MyDrawable����ռ���У������½�Ϊԭ��
		out.writeInt(refRow);//��λ�ο����ڱ�MyDrawable����ռ���У������½�Ϊԭ��
		out.writeObject(noThrough);//����ͨ������
		out.writeBoolean(meetable);//�Ƿ��������
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
	public MyDrawable(){}//�޲ι�����
	//������
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
	//�����������Լ�
	public void drawSelf(Canvas canvas,int screenRow, int screenCol,int offsetX,int offsetY){
		int x = (screenCol-refCol)*TILE_SIZE;//����Լ���ӵ�еĿ��������Ͻǿ��x����
		int y = screenRow*TILE_SIZE+(refRow+1)*TILE_SIZE-height;//����Լ���ӵ�еĿ��������Ͻǿ��y����
		canvas.drawBitmap(bmpSelf, x-offsetX, y-offsetY, null);//�����Լ������Ͻǵ�xy���껭���Լ�
	}
}