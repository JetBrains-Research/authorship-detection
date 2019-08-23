package wyf.ytl.data;

import java.io.Serializable;

import wyf.ytl.entity.Hero;
import wyf.ytl.entity.MyDrawable;
import wyf.ytl.entity.MyMeetableDrawable;

import android.content.res.Resources;

//public class MeetableLayer extends Layer implements Serializable{
public class MeetableLayer extends Layer{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1265133035026861860L;
	private MyMeetableDrawable[][] mapMatrixMeetable;//实际地图
	private MyMeetableDrawable[][] mapMatrixForMeetable;//可遇矩阵
	
	public MeetableLayer(){}
	
	public MeetableLayer(Resources resources) {
		super(resources);
		
		this.mapMatrixMeetable = GameData2.mapData;
		initMapMatrixForMeetable();
	}

	public MyDrawable[][] getMapMatrix()
	{
		return mapMatrixMeetable;
	}
	
	public void initMapMatrixForMeetable(){//计算可遇矩阵mapMatrixForMeetable
		mapMatrixForMeetable = new MyMeetableDrawable[40][60]; 
		for(int i=0; i<mapMatrixMeetable.length; i++){
			for(int j=0; j<mapMatrixMeetable[i].length; j++){
				if(mapMatrixMeetable[i][j] != null){
					int x = mapMatrixMeetable[i][j].col - mapMatrixMeetable[i][j].refCol;
					int y = mapMatrixMeetable[i][j].row + mapMatrixMeetable[i][j].refRow;
					int[][] meetableMatrix = mapMatrixMeetable[i][j].meetableMatrix;
					for(int k=0; k<meetableMatrix.length; k++){
						mapMatrixForMeetable[y-meetableMatrix[k][1]][x+meetableMatrix[k][0]] = mapMatrixMeetable[i][j];
					}					
				}
			}
		}
	}
	
	public MyMeetableDrawable check(Hero hero){//检测是否遇上
		int col = hero.col;//获取英雄的列数
		int row = hero.row;//获取英雄的行数
		switch(hero.direction%4){//还是先按方向查看
		case 0://向下
			if(mapMatrixForMeetable[row][col-1] != null){//左边检测到了可遇物
				return mapMatrixForMeetable[row][col-1];
			}
			else if(mapMatrixForMeetable[row][col+1] != null){//右边检测到了可遇物
				return mapMatrixForMeetable[row][col+1];
			}
			break;
		case 1://向左
			if(mapMatrixForMeetable[row-1][col] != null){//上边检测到了可遇物
				return mapMatrixForMeetable[row-1][col];
			}
			else if(mapMatrixForMeetable[row+1][col] != null){//下边检测到了可遇物
				return mapMatrixForMeetable[row+1][col];
			}
			break;
		case 2://向右
			if(mapMatrixForMeetable[row-1][col] != null){//上边检测到了可遇物
				return mapMatrixForMeetable[row-1][col];
			}
			else if(mapMatrixForMeetable[row+1][col] != null){//下边检测到了可遇物
				return mapMatrixForMeetable[row+1][col];
			}
			break;
		case 3://向上
			if(mapMatrixForMeetable[row][col-1] != null){//左边检测到了可遇物
				return mapMatrixForMeetable[row][col-1];
			}
			else if(mapMatrixForMeetable[row][col+1] != null){//右边检测到了可遇物
				return mapMatrixForMeetable[row][col+1];
			}
			break;
		}
		return null;
	}
}