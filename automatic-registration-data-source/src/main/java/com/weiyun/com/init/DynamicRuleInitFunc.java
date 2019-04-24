package com.weiyun.com.init;

import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.csp.sentinel.util.AppNameUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.nacos.api.PropertyKeyConst;

import com.weiyun.com.config.NacosConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Properties;

/**
 * @author DPn!ce
 * @date 2019/04/18.
 */
@Component
public class DynamicRuleInitFunc implements InitFunc {

    private static final String APP_NAME = AppNameUtil.getAppName();
    private final String flowDataId = APP_NAME + NacosConfigUtil.FLOW_DATA_ID_POSTFIX;
    private final String degradeDataId = APP_NAME + NacosConfigUtil.DEGRADE_DATA_ID_POSTFIX;
    private final String paramDataId = APP_NAME + NacosConfigUtil.PARAM_FLOW_DATA_ID_POSTFIX;
    private final String sysDataId = APP_NAME + NacosConfigUtil.SYSTEM_DATA_ID_POSTFIX;
    private final String authorityDataId = APP_NAME + NacosConfigUtil.AUTH_DATA_ID_POSTFIX;

    private final Properties properties;

    private String serverAddr = "192.168.196.1:8848";
    private String namespace = "ec3e95f8-2e6b-4857-95a4-7e68083289b4";

    @Autowired
    public DynamicRuleInitFunc(@Qualifier("nacosProperties") Properties properties) {
        this.properties = properties;
    }

    @Override
    public void init() {
        System.out.println("自动注册数据源");
//        Properties properties = new Properties();
//        properties.put(PropertyKeyConst.NAMESPACE, namespace);
//        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        //从 nacos 监听配置 限流
        NacosDataSource<List<FlowRule>> flowRuleSource = new NacosDataSource<>(properties,
                NacosConfigUtil.GROUP_ID, flowDataId,
                source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
                }));
        FlowRuleManager.register2Property(flowRuleSource.getProperty());

        //降级
        NacosDataSource<List<DegradeRule>> degradeRuleSource = new NacosDataSource<>(properties,
                NacosConfigUtil.GROUP_ID, degradeDataId,
                source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {
                }));
        DegradeRuleManager.register2Property(degradeRuleSource.getProperty());

        //热点
        NacosDataSource<List<ParamFlowRule>> paramRuleSource = new NacosDataSource<>(properties,
                NacosConfigUtil.GROUP_ID, paramDataId,
                source -> JSON.parseObject(source, new TypeReference<List<ParamFlowRule>>() {
                }));
        ParamFlowRuleManager.register2Property(paramRuleSource.getProperty());

        //系统
        NacosDataSource<List<SystemRule>> sysRuleSource = new NacosDataSource<>(properties,
                NacosConfigUtil.GROUP_ID, sysDataId,
                source -> JSON.parseObject(source, new TypeReference<List<SystemRule>>() {
                }));
        SystemRuleManager.register2Property(sysRuleSource.getProperty());

        //授权
        NacosDataSource<List<AuthorityRule>> authorityRuleSource = new NacosDataSource<>(properties,
                NacosConfigUtil.GROUP_ID, authorityDataId,
                source -> JSON.parseObject(source, new TypeReference<List<AuthorityRule>>() {
                }));
        AuthorityRuleManager.register2Property(authorityRuleSource.getProperty());
    }
}
