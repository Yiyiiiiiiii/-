package com.neutech.vo;

import com.neutech.enumeration.CategoryStatusEnum;

public class CategoryVO {
    private Integer id;
    private Integer parentId;
    private String name;
    private CategoryStatusEnum status;
    private Integer sortOrder;

    public CategoryVO(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryStatusEnum getStatus() {
        return status;
    }

    public void setStatus(CategoryStatusEnum status) {
        this.status = status;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }
}
