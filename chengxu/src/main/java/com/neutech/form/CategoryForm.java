package com.neutech.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CategoryForm {
    private Integer id;
    @NotNull(message = "父类别id不能为空")
    private Integer parentId;
    @NotBlank(message = "名称不能为空")
    private String name;
    @NotNull(message = "状态不能为空")
    private Integer status;
    private Integer sortOrder;

    public CategoryForm(){

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString() {
        return "CategoryForm{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", sortOrder=" + sortOrder +
                '}';
    }
}
