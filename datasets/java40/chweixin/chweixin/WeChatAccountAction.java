package ipower.wechat.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ipower.action.BaseDataAction;
import ipower.model.TreeNode;
import ipower.wechat.modal.WeChatAccountInfo;
import ipower.wechat.service.IWeChatAccountService;

/**
 * 微信公众账号管理Action。
 * @author yangyong.
 * @since 2014-04-01.
 * */
public class WeChatAccountAction extends BaseDataAction<WeChatAccountInfo> {
	private WeChatAccountInfo info = new WeChatAccountInfo();
	
	@Override
	public WeChatAccountInfo getModel() {
		return this.info;
	}
	/**
	 * 加载全部微信公众账号。
	 * @return 公众账号数据。
	 * @throws IOException 
	 * */
	public void all() throws IOException{
		List<TreeNode> nodes = new ArrayList<>();
		if(this.service instanceof IWeChatAccountService){
			List<WeChatAccountInfo> list = ((IWeChatAccountService)this.service).loadAllAccounts();
			if(list != null && list.size() > 0){
				for(WeChatAccountInfo info: list){
					if(info == null) continue;
					TreeNode tn = new TreeNode();
					tn.setId(info.getId());
					tn.setText(info.getName());
					nodes.add(tn);
				}
			}
		}
		this.writeJson(nodes);
	}

	@Override
	protected String deletePrimaryString() {
		return this.getModel().getId();
	}
}