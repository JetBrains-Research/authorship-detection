package ipower.wechat.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.opensymphony.xwork2.ModelDriven;

import ipower.action.BaseAction;
import ipower.model.Json;
import ipower.model.TreeNode;
import ipower.wechat.modal.WeChatMenuInfo;
import ipower.wechat.service.IWeChatMenuService;

/**
 * 微信公众号菜单Action。
 * @author yangyong.
 * @since 2014-04-02.
 * */
public class WeChatMenuAction extends BaseAction implements ModelDriven<WeChatMenuInfo> {
	private IWeChatMenuService service;
	private WeChatMenuInfo info = new WeChatMenuInfo();
	/**
	 * 设置微信公众号菜单服务接口。
	 * @param weChatMenuService
	 * 微信公众号菜单服务接口。
	 * */
	public void setService(IWeChatMenuService service) {
		this.service = service;
	}
	
	@Override
	public WeChatMenuInfo getModel() {
		return this.info;
	}
	/**
	 * 加载列表数据。
	 * @throws IOException 
	 * */
	public void datagrid() throws IOException{
		List<WeChatMenuInfo> list = this.service.loadMenus(this.getModel().getAccountId(), null);
		if(list == null) list = new ArrayList<>();
		this.writeJson(list);
	}
	/**
	 * 创建树数据节点。
	 * @param info
	 * @return
	 * 	节点对象。
	 * */
	private TreeNode createTreeNode(WeChatMenuInfo info){
		if(info ==  null) return null;
		TreeNode node = new TreeNode();
		node.setId(info.getId());
		node.setText(info.getName());
		if(info.getChildren() != null && info.getChildren().size() > 0){
			List<TreeNode> children = new ArrayList<>();
			for(WeChatMenuInfo data: info.getChildren()){
				TreeNode n = this.createTreeNode(data);
				if(n != null) children.add(n);
			}
			if(children.size() > 0) node.setChildren(children);
		}
		return node;
	}
	/**
	 * 加载菜单树数据。
	 * */
	public void tree() throws IOException{
		List<TreeNode> results = new ArrayList<>();
		List<WeChatMenuInfo> list = this.service.loadMenus(this.getModel().getAccountId(), this.getModel().getId());
		if(list != null && list.size() > 0){
			for(WeChatMenuInfo info : list){
				TreeNode tNode = this.createTreeNode(info);
				if(tNode != null) results.add(tNode);
			}
		}
		this.writeJson(results);
	}
	/**
	 * 更新数据。
	 * @throws IOException 
	 * */
	public void update() throws IOException{
		Json result = new Json();
		try {
			result.setData(this.service.update(this.getModel()));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}finally{
			this.writeJson(result);
		}
	}
	/**
	 * 删除数据。
	 * @throws IOException 
	 * */
	public void delete() throws IOException{
		Json result = new Json();
		try {
			String s = this.getModel().getId();
			if(s != null && !s.isEmpty()){
				this.service.delete(s.split("\\|"));
				result.setSuccess(true);
				result.setMsg("删除成功！");
			}
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
		}finally{
			this.writeJson(result);
		}
	}
	/**
	 * 创建公众号上的菜单。
	 * */
	public void create() throws IOException{
		this.writeJson(this.service.createMenus(this.getModel().getAccountId()));
	}
	/**
	 * 查询公众号上的菜单。
	 * */
	public void query() throws IOException{
		this.writeJson(this.service.queryMenus(this.getModel().getAccountId()));
	}
	/**
	 * 删除公众号上的菜单。
	 * */
	public void remove() throws IOException{
		this.writeJson(this.service.deleteMenus(this.getModel().getAccountId()));
	}
}