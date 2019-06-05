package com.example.demo2.primary.controller;

import com.example.demo2.primary.entity.DptBean;
import com.example.demo2.primary.mapper.DptMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/test")
public class TestController {
    private Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private DptMapper dptMapper;

    @GetMapping("dpt")
    public String getDpt() {
        DptBean dpt = dptMapper.selectById(1L);
        return dpt.getDeptName();
    }

    @GetMapping("dpt1")
    public String getDpt1() {
        DptBean dptBean = new DptBean();
        // 获取顶级部门
        DptBean dptDefault = dptMapper.selectById(1L);
        if (dptDefault == null) {
            throw new RuntimeException("找不到顶级部门...");
        }
        // 赋值
        dptBean.setMemberCode("0000");
        dptBean.setDeptName("测试多数据源");
        dptBean.setDeptCode("000088");
        dptBean.setDeptParent(dptDefault.getDeptCode());
        dptBean.setDeptNodecode(dptDefault.getDeptNodecode() + "." + dptBean.getDeptCode());
        dptMapper.insert(dptBean);
        log.info("新增id:{}", dptBean.getDeptId());
        dptMapper.deleteById(dptBean.getDeptId());
        log.info("删除成功");
        return "success";
    }
}
