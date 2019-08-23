package cn.abovesky.shopping.base;

import java.util.Date;

/**
 * Created by snow on 2014/4/22.
 */
public class BaseConditionVO {
    public final static int PAGE_SHOW_COUNT = 20;
    private int pageNum = 1;
    private int pageSize = 0;
    private int totalCount = 0;
    private String orderField;
    private String orderDirection;
    private String keywords;
    private String status;
    private String type;
    private Date startDate;
    private Date endDate;

    public String getType() {
        return "".equals(type) ? null : type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getStatus() {
        return "".equals(status)? null : status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public int getPageNum() {
        return pageNum;
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    public int getPageSize() {
        return pageSize > 0 ? pageSize : PAGE_SHOW_COUNT;
    }
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    public String getOrderField() {
        return orderField;
    }
    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }
    public String getOrderDirection() {
        return "desc".equals(orderDirection) ? "desc" : "asc";
    }
    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getKeywords() {
        return "".equals(keywords)? null : keywords;
    }
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public int getStartIndex() {
        int pageNum = this.getPageNum() > 0 ? this.getPageNum() - 1 : 0;
        return pageNum * this.getPageSize();
    }

    //商家ID
    private Integer merchantId;

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    //用户ID
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    //统计日期
    private String saleCountDate;

    public String getSaleCountDate() {
        return saleCountDate;
    }

    public void setSaleCountDate(String saleCountDate) {
        this.saleCountDate = saleCountDate;
    }

    private String keywords2;

    public String getKeywords2() {
        return keywords2;
    }

    public void setKeywords2(String keywords2) {
        this.keywords2 = keywords2;
    }

    //状态数组
    private String[] statuses;

    public String[] getStatuses() {
        return statuses;
    }

    public void setStatuses(String[] statuses) {
        this.statuses = statuses;
    }
}
