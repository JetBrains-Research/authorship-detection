package ipower.wechat.modal;

import ipower.model.IPaging;
import ipower.wechat.domain.Picture;

/**
 * 图片信息。
 * @author yangyong.
 * @since 2014-04-08.
 * */
public class PictureInfo extends Picture implements IPaging {
	private static final long serialVersionUID = 1L;
	
	private Integer rows,page;
	private String sort,order,url;
	/**
	 * 构造函数。
	 * */
	public PictureInfo(){
		super();
	}
	/**
	 * 获取图片URL。
	 * @return
	 * 图片URL。
	 * */
	public String getUrl() {
		return url;
	}
	/**
	 * 设置图片URL。
	 * @param url
	 * 	图片URL。
	 * */
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public Integer getRows() {
		return this.rows;
	}

	@Override
	public void setRows(Integer rows) {
		this.rows = rows;
	}

	@Override
	public Integer getPage() {
		return this.page;
	}

	@Override
	public void setPage(Integer page) {
		this.page = page;
	}

	@Override
	public String getSort() {
		return this.sort;
	}

	@Override
	public void setSort(String sort) {
		this.sort = sort;
	}

	@Override
	public String getOrder() {
		return this.order;
	}

	@Override
	public void setOrder(String order) {
		this.order = order;
	}
}