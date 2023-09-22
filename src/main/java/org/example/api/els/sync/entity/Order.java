package org.example.api.els.sync.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("t_order")
@EqualsAndHashCode(callSuper = true)
public class Order extends Model<Order> implements Serializable {
    private static final long serialVersionUID = 6819476121498182001L;
    /**
     * 主键id
     */
    @TableId(type = IdType.INPUT)
    private Long orderId;
    /**
     * 商品编码
     */
    private String orderNo;
    /**
     * 商品名称
     */
        private Integer userId;
        private Integer amount;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /*/
     * 修改时间
     */
    private LocalDateTime updateTime;
    public Order(){}
    public Order(Long orderId, String orderNo, Integer userId, Integer amount, LocalDateTime createTime, LocalDateTime updateTime) {
        this.orderId =  orderId;
        this.orderNo = orderNo;
        this.userId = userId;
        this.amount = amount;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
