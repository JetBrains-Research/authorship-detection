package ipower.wechat.action;

import java.io.IOException;

import ipower.action.BaseDataAction;
import ipower.model.DataGrid;
import ipower.wechat.modal.PictureInfo;

/**
 * 图片库Action。
 * @author yangyong.
 * @since 2014-04-08.
 * */
public class PictureAction extends BaseDataAction<PictureInfo> {
	private PictureInfo info = new PictureInfo();
	
	@Override
	public PictureInfo getModel() {
		return this.info;
	}
	
	@Override
	public void datagrid() throws IOException{
		DataGrid<PictureInfo> grid = this.service.datagrid(this.getModel());
		if(grid != null && grid.getRows() != null && grid.getRows().size() > 0){
			String host = this.host();
			for(int i = 0; i < grid.getRows().size(); i++){
				grid.getRows().get(i).setUrl(host + grid.getRows().get(i).getPath());
			}
		}
		this.writeJson(grid);
	}
	
	@Override
	public void update() throws IOException{
		if(this.getUserIdentity() != null && this.getModel() != null){
			this.getModel().setCreateUserId(this.getUserIdentity().getId());
			this.getModel().setCreateUserName(this.getUserIdentity().getName());
		}
		if(this.getModel() != null){
			this.getModel().setUrl(this.host());
		}
		super.update();
	}

	@Override
	protected String deletePrimaryString() {
		return this.getModel().getId();
	}
}