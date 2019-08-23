package ipower.wechat.service;

import java.util.List;

import ipower.wechat.message.menu.Menu;
import ipower.wechat.modal.WeChatMenuInfo;
/**
 * 微信公众号菜单服务接口。
 * @author yangyong.
 * @since 2014-04-02.
 * */
public interface IWeChatMenuService {
	/**
	 * 加载菜单数据。
	 * @param accountId
	 *  所属微信公众号ID。
	 *  @param ignoreId
	 *   忽略其子节点。
	 *  @return 
	 *  菜单数据集合。
	 * */
	List<WeChatMenuInfo> loadMenus(String accountId,String ignoreId);
	/**
	 * 更新数据。
	 * @param info
	 * 	源数据。
	 * */
	WeChatMenuInfo update(WeChatMenuInfo info);
	/**
	 * 删除数据。
	 * @param ids
	 * 	需删除的主键数组。
	 * */
	void delete(String[] ids);
	/**
	 * 构造公众号菜单对象。
	 * @param accountId
	 * 公众号ID。
	 * @return
	 *  公众号菜单对象。
	 * */
	Menu buildMenu(String accountId);
	/**
	 * 创建公众号上的菜单。
	 * @param accountId
	 * 公众号ID。
	 * @return
	 * 创建成功返回true，否则false。
	 * */
	String createMenus(String accountId);
	/**
	 * 删除公众号上的菜单。
	 * @param accountId
	 * 公众号ID。
	 * @return
	 * 创建成功返回true，否则false。
	 * */
	String deleteMenus(String accountId);
	/**
	 * 查询公众号上的菜单。
	 * @param accountId
	 * 公众号ID。
	 * @return
	 * 查询反馈。
	 * */
	String queryMenus(String accountId);
}