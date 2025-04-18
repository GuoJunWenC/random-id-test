package org.example.api.export;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 三电及管线迁改情况
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectPipelineRelocation {
    private static final long serialVersionUID = -6072284305142573853L;
    /**
     * 节点自增id
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     * 主项目id
     */
    private Integer reportId;
    /**
     * 设计迁改（处）
     */
    private Integer designModification;
    /**
     * 开累完成迁改（处）
     */
    private Integer designModificationComplete;
    /**
     * 开累完成迁改（处）/设计迁改（处）
     */
    private BigDecimal designModificationRate;
    /**
     *
     */
    private Integer createId;
    private Integer deptId;
    private String deptName;
    private Integer unitId;
    private String unitName;
    private LocalDateTime createTime;
    /**
     * 合计
     */
    @TableField(exist = false)
    private ProjectPipelineRelocation relocation;
}
