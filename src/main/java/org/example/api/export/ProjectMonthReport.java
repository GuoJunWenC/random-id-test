package org.example.api.export;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 高速铁路项目月报
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ProjectMonthReport{
    private static final long serialVersionUID = -6072284305142573853L;
    /**
     * 节点自增id
     */

    private Integer id;
    /**
     * 项目id
     */
    private String projectId;
    /**
     * 项目名称
     */
    private String projectName;
    /**
     * 填报日期
     */
    private String reportDate;
    /**
     * 建设里程
     */
    private String constructionMileage;
    /**
     * 征地面积（亩）
     */
    private String landRequisitionArea;
    /**
     * 拆迁面积（平方米）
     */
    private String demolitionArea;
    /**
     * 征拆包干费用（万元）
     */
    private String demolitionCharge;
    /**
     * 开工累计完成征地（亩）
     */
    private String completeDemolition;
    /**
     * 开工累计完成征地（亩）/征地面积（亩）
     */
    private String expropriationRate;
    /**
     * 开工累计拆迁面积（平方米）
     */
    private String demolitionAreaTotal;
    /**
     * 开工累计拆迁面积（平方米）/拆迁面积（平方米）
     */
    private String demolitionAreaRate;
    /**
     * 开工累计投入征拆费用（万元）
     */
    private String removalFee;
    /**
     * 开工累计投入征拆费用（万元）/征拆包干费用（万元）
     */
    private String removalFeeRate;
    /**
     * 先行用地（亩）
     */
    private String advanceSite;
    /**
     * 交付占比
     */
    private String deliveryRatio;
    /**
     * 先行用地已交付（亩）
     */
    private BigDecimal advanceSiteDelivered;
    /**
     * 先行用地已交付（亩）占比
     */
    private BigDecimal advanceSiteDeliveredRate;
    /**
     * 变径桩完成（根）
     */
    private Integer reducingPileCompleted;
    /**
     * 变径桩完成（根）占比
     */
    private BigDecimal reducingPileCompletedRate;
    /**
     * 开累完成（根）
     */
    private BigDecimal workCompleted;
    /**
     * 开累完成（根）完成占比
     */
    private BigDecimal workCompletedRate;
    /**
     * 路基CFG桩完成（根）
     */
    private Integer subGradeCfgPile;
    /**
     * 路基CFG桩完成（根）占比
     */
    private BigDecimal subGradeCfgPileRate;
    /**
     * 挖方完成（万立方米）
     */
    private BigDecimal excavationCompletion;
    /**
     * 挖方完成（万立方米）占比
     */
    private BigDecimal excavationCompletionRate;
    /**
     * 填完完成（万立方米）
     */
    private BigDecimal fillOutCompletion;
    /**
     * 填完完成（万立方米）占比
     */
    private BigDecimal fillOutCompletionRate;
    /**
     * 桩基完成（根）
     */
    private Integer pileFoundationCompletion;
    /**
     * 桩基完成（根）占比
     */
    private BigDecimal pileFoundationCompletionRate;
    /**
     * 桩基完成（根）
     */
    private Integer capCompletion;
    /**
     * 桩基完成（根）占比
     */
    private BigDecimal capCompletionRate;
    /**
     * 墩身完成（个）
     */
    private Integer pierCompletion;
    /**
     * 墩身完成（个）占比
     */
    private BigDecimal pierCompletionRate;
    /**
     * 箱梁预制完成（榀）
     */
    private Integer boxGirderCompletion;
    /**
     * 箱梁预制完成（榀）
     */
    private BigDecimal boxGirderCompletionRate;
    /**
     * 隧道（座）
     */
    private Integer tunnel;
    /**
     * 隧道完成（座）
     */
    private Integer tunnelComplete;
    /**
     * 累计开挖进尺（米）
     */
    private BigDecimal tunnelCompletionMeter;
    /**
     * 累计开挖进尺（米）占比
     */
    private BigDecimal tunnelCompletionMeterRate;
    /**
     * 项目总投资（亿元）
     */
    private BigDecimal totalAmount;
    /**
     * 本次完成投资（亿元）
     */
    private BigDecimal completeAmount;
    /**
     * 年对累计完成投资（亿元）
     */
    private BigDecimal completeYearAmount;
    /**
     * 开工累计完成投资（亿元）
     */
    private BigDecimal addUpCompleteAmount;
    /**
     * 存在的问题及困难
     */
    private String problem;
    /**
     * 重大事项进展情况
     */
    private String progress;
    /**
     *
     */
    private Integer createId;
    private Integer deptId;
    private String deptName;
    private Integer unitId;
    private String unitName;
    private LocalDateTime createTime;
    @TableField(exist = false)
    private String date;
}
