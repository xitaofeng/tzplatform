package com.tzplatform.entity.common;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 分页工具类 只需要继承即可
 *
 * @author leijie
 */
public class PageDto implements Serializable {
    @JSONField(serialize = false)
    private static final long serialVersionUID = 7995642028506709755L;

    @JSONField(serialize = false)
    private Integer first;// 起始记录数

    @JSONField(serialize = false)
    private Integer last; // 截至记录数

    @JSONField(serialize = false)
    private Integer pageNum;// 页码

    @JSONField(serialize = false)
    private Integer pageSize;// 每页显示数据量

    @JSONField(serialize = false)
    private Integer totalRecord;// 总记录数

    @JSONField(serialize = false)
    private Integer totalPage;// 总共页码数

    @JSONField(serialize = false)
    private String sortName;//排序名称

    @JSONField(serialize = false)
    private String sortOrder;//排序字段

    @JSONField(serialize = false)
    private String requestSource;//请求来源

    // 获取分页总页数
    public Integer getTotalPage() {
        if (totalRecord != null) {
            if (this.totalRecord % this.pageSize == 0) {
                this.totalPage = this.totalRecord / this.pageSize;
            } else {
                this.totalPage = this.totalRecord / this.pageSize + 1;
            }
        }
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getFirst() {
        return first;
    }

    public void setFirst(Integer first) {
        this.first = first;
    }

    public Integer getLast() {
        return last;
    }

    public void setLast(Integer last) {
        this.last = last;
    }

    // 获取当前页码数
    public static Integer getPageNum(Integer first, Integer last) {
        Integer pageNum = 1;
        if (first != null && last != null) {
            if (first <= 0) {
                pageNum = 1;
            } else {
                pageNum = (first % (last - first) == 0) ? (first / (last - first)) + 1 : (first / (last - first) + 1);
            }
        }
        return pageNum;
    }

    // 获取当前页码数
    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        if (null == pageSize || "".equals(pageSize)) {
            pageSize = 10;
        }
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Integer totalRecord) {
        this.totalRecord = totalRecord;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getRequestSource() {
        return requestSource;
    }

    public void setRequestSource(String requestSource) {
        this.requestSource = requestSource;
    }
}
