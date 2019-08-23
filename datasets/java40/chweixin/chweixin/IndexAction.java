package ipower.wechat.action;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import ipower.action.BaseAction;
import ipower.configuration.ModuleSystem;
import ipower.wechat.service.IMenuService;
/**
 * 默认页面Action.
 * @author 杨勇.
 * @since 2013-11-27.
 * */
public class IndexAction extends BaseAction {
	private static Map<String,String> cache = Collections.synchronizedMap(new HashMap<String,String>());
	private IMenuService menuService;
	private String systemId,systemName;
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
	 *  系统ID。
	 * */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	/**
	 * 获取系统名称。
	 * @return 系统名称。
	 * */
	public String getSystemName() {
		return systemName;
	}
	/**
	 * 设置系统名称(未启用)。
	 * @param systemName
	 * 	系统名称。
	 * */
	public void setSystemName(String systemName){
		if(this.systemId != null && !this.systemId.trim().isEmpty()  &&
				systemName != null && !systemName.trim().isEmpty() && !systemName.equalsIgnoreCase(this.systemName)){
			 cache.put(this.systemId, this.systemName = systemName);
		}
	}
	/**
	 * 默认输出。
	 * */
	@Override
	public String execute() throws Exception{
		if(this.menuService != null && this.systemId != null && !this.systemId.trim().isEmpty()){
			this.systemName = cache.get(this.systemId);
			if(this.systemName == null || !this.systemName.trim().isEmpty()){
				ModuleSystem ms = this.menuService.loadModuleSystem(this.systemId);
				if(ms != null){
					this.systemName = ms.getName();
					if(this.systemName != null && !this.systemName.trim().isEmpty()){
						cache.put(this.systemId, this.systemName);
					}
				}
			}
		}
		return SUCCESS;
	}
	/**
	 * 顶部Banner头。
	 * */
	public String top(){
		return "top";
	}
	/**
	 * 左边菜单。
	 * */
	public String leftmenu(){
		return "leftmenu";
	}
	/**
	 * 中间工作区域。
	 * */
	public String workspace(){
		return "workspace";
	}
	/**
	 * 底部footer。
	 * */
	public String footer(){
		return "footer";
	}
}