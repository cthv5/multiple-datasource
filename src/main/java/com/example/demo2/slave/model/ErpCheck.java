package com.example.demo2.slave.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper=true)
@Entity
public class ErpCheck extends BaseModel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    // 省市代码
    private String prov;
    // 账号
    private String accno;
    // 货币码
    private String cur;
    // 交易日期
    private String trDate;
    // 交易时间
    private String timeStab;
    // 日志号
    private String trjrn;
    // 交易类型
    private String trType;
    // 交易银行账号
    private String trBankNo;
    // 银行开户名称
    private String accName;
    // 发生额标志
    private String amtIndex;
    // 对方账号省市代码
    private String oppProv;
    // 对方账号
    private String oppAccNo;
    // 对方账号货币码
    private String oppCur;
    // 对方账号名称
    private String oppName;
    // 对方账号开户行名称
    private String oppBkName;
    // 现转标志
    private String cshIndex;
    // 错账日期
    private String errDate;
    // 错账传票号
    private String errVchNo;
    // 交易金额
    private String amt;
    // 账户余额
    private String bal;
    // 上笔金额
    private String preAmt;
    // 手续费总额
    private String totChg;
    // 凭证种类
    private String voucherType;
    // 凭证省市代号
    private String voucherProv;
    // 凭证批次号
    private String voucherBat;
    // 凭证号
    private String voucherNo;
    // 客户参考码
    private String custRef;
    // 交易码
    private String transCode;
    // 柜员号
    private String teller;
    // 传票号
    private String vchNo;
    // 摘要
    private String abs;
    // 附言
    private String postScript;
    // 交易来源
    private String trFrom;

    // 查询公司  0 钢盟 1 岳洋通 2 智恒达
    private String compNo;

    @Column(name = "status", columnDefinition = "int default 0")
    private int status = 0;

}
