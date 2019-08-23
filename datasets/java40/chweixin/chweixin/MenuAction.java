package ipower.wechat.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ipower.action.BaseAction;
import ipower.configuration.ModuleDefine;
import ipower.configuration.ModuleSystem;
import ipower.model.TreeNode;
import ipower.wechat.service.IMenuService;
/**
 * 菜单服务Action。
 * @author 杨勇。
 * @since 2014-01-18。
 * */
public class MenuAction extends BaseAction {
	private IMenuService menuService;
	private String systemId;
	private static Map<String,List<TreeNode>> mapNodesCache = Collections.synchronizedMap(new HashMap<String,List<TreeNode>>());
	/**
	 * 设置菜单服务。
	 * @param menuService
	 * 菜单服务。
	 * */
	public void setMenuService(IMenuService menuService) {
		this.menuService = menuService;
	}
	/**
	 * 设置系统ID。
	 * @param systemId
	 * 系统ID。
	 * */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	/**
	 * 输出菜单树。
	 * @throws IOException 
	 * */
	public void tree() throws IOException{
		List<TreeNode> treeNodeList = null;
		if(this.menuService != null && (this.systemId != null && !this.systemId.trim().isEmpty())){
			treeNodeList = mapNodesCache.get(this.systemId);
			if(treeNodeList == null || treeNodeList.size() == 0){
				treeNodeList = new ArrayList<>();
				ModuleSystem ms = this.menuService.loadModuleSystem(this.systemId);
				if(ms != null && ms.getModules() != null && ms.getModules().size() > 0){
					for(int i = 0; i < ms.getModules().size(); i++){
						TreeNode node = this.createTreeNode(ms.getModules().item(i));
						if(node != null) treeNodeList.add(node);
					}
				}
				//缓存数据。
				if(treeNodeList != null && treeNodeList.size() > 0){
					mapNodesCache.put(this.systemId, treeNodeList);
				}
			}
		}
		this.writeJson(treeNodeList);
	}
	/**
	 * 创建树结构节点。
	 * @param module
	 * 	菜单模块。
	 * */
	private synchronized TreeNode createTreeNode(ModuleDefine m){
		if(m == null) return null;
		
		TreeNode node = new TreeNode();
		node.setId(m.getModuleID());
		node.setText(m.getModuleName());
		Map<String,Object> attributes = new HashMap<String,Object>();
		attributes.put("url", m.getModuleUri());
		node.setAttributes(attributes);
		
		if(m.getModules() != null && m.getModules().size() > 0){
			node.setChildren(new ArrayList<TreeNode>());
			for(int i = 0; i < m.getModules().size(); i++){
				TreeNode n = this.createTreeNode(m.getModules().item(i));
				if(n != null) node.getChildren().add(n);
			}
		}
		
		return node;
	}
}