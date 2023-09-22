package org.example.api.sharding;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
/*@Entity
@Table(name = "t_order")
@EntityListeners(value = AuditingEntityListener.class)*/
public class OrderInfo {
   /* @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    *//**
     * 商品编码
     *//*
    private String orderNo;
    *//**
     * 商品名称
     *//*
    private Integer userId;
    private Integer amount;
    *//**
     * 创建时间
     *//*
    private LocalDateTime createTime;
    *//*//*
     * 修改时间
     *//*
    private LocalDateTime updateTime;

    public OrderInfo(Long orderId, String orderNo, Integer userId, Integer amount, LocalDateTime createTime, LocalDateTime updateTime) {
        this.orderId = orderId;
        this.orderNo = orderNo;
        this.userId = userId;
        this.amount = amount;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public OrderInfo() {

    }*/
}
