package com.example.demo2.primary.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cth
 * @date 2019/06/03
 */
@Data
@TableName(value = "basic_dept")
@KeySequence(value = "BASIC_DEPT_SEQ")
public class DptBean implements Serializable {

    @TableId(value = "dept_id", type = IdType.INPUT)
    private Long deptId;

    private Integer basicShare;

    private String deptCode;

    private Integer deptIsleaf;

    private String deptManager;

    private String deptName;

    private String deptNodecode;

    private String deptParent;

    private String deptRemark;

    private String memberCode;

    private String orgCode;

    private Date dataSystemdate;

    @TableField(fill = FieldFill.UPDATE)
    private Date dataUpdatedate;

}
