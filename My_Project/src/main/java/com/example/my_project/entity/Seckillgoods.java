package com.example.my_project.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * seckillGoods
 * @author 
 */
@Data
public class Seckillgoods extends Stock {

    /**
     * 秒杀价
     */
    private BigDecimal seckillPrice;

    /**
     * 秒杀开始时间
     */
    private Date startDate;

    /**
     * 秒杀结束时间
     */
    private Date endDate;

    @TableField("sec_status")
    private String secStatus;

    private static final long serialVersionUID = 1L;

}