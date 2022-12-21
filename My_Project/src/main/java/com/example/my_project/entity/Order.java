package com.example.my_project.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 秒杀订单表
 * </p>
 *
 * @author GongYue
 * @since 2021-12-15
 */
@TableName("orderInformation")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

//    @TableId(type = IdType.AUTO)
    private int id;

    private String orderName;

    private String orderUser;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 最后更新时间
     */
    /**
     * 使用自动填充功能
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 0正常，1删除
     */
    @TableLogic(value = "0", delval = "1")
    private String delFlag;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }
    public String getOrderUser() {
        return orderUser;
    }

    public void setOrderUser(String orderUser) {
        this.orderUser = orderUser;
    }
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + id +
            ", orderName=" + orderName +
            ", orderUser=" + orderUser +
            ", remarks=" + remarks +
            ", updateDate=" + updateDate +
            ", createDate=" + createDate +
            ", updateBy=" + updateBy +
            ", createBy=" + createBy +
            ", delFlag=" + delFlag +
        "}";
    }
}
