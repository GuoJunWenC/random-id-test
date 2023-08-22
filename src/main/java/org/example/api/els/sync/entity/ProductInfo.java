package org.example.api.els.sync.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("product_info")
@EqualsAndHashCode(callSuper = true)
public class ProductInfo extends Model<ProductInfo> implements Serializable {
    private static final long serialVersionUID = 6819476121498182001L;
    /**
     * 主键id
     */
    @TableId(type = IdType.INPUT)
    private Long id;
    /**
     * 商品编码
     */
    private String productCode;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品价格
     */
    private BigDecimal productPrice;
    /**
     * 创建时间
     */
    private LocalDateTime gmtCreate;
    /*/
     * 修改时间
     */
    private LocalDateTime gmtModified;
}
