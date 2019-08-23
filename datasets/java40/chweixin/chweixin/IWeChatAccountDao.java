package ipower.wechat.dao;

import java.util.List;

import ipower.wechat.domain.WeChatAccount;
import ipower.wechat.modal.WeChatAccountInfo;
/**
 * 微信公众号数据访问接口。
 * @author yangyong.
 * @since 2014-03-31.
 * */
public interface IWeChatAccountDao extends IBaseDao<WeChatAccount> {
	/**
	 * 加载全部可用的公众账号。
	 * @return 可用的公众账号。
	 * */
	List<WeChatAccount> loadAllAccounts();
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 *  结果数据集。
	 * */
	List<WeChatAccount> findAccounts(WeChatAccountInfo info);
	/**
	 * 查询数据总数。
	 * @param	info
	 * 	查询条件。
	 * @return
	 * 数据总数。
	 * */
	Long total(WeChatAccountInfo info);
	/**
	 * 根据OpenId来加载公众账号。
	 * @param openId
	 * 		OpenId
	 * @return
	 * 	加载公众账号。
	 * */
	WeChatAccount loadAccount(String openId);
	/**
	 * 根据公众号加载注册信息。
	 * @param account
	 *  公众号账号。
	 * @return
	 * 公众号注册信息。
	 * */
	WeChatAccount findAccount(String account);
}