package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.util.NacosConfigUtil;
import com.alibaba.csp.sentinel.dashboard.util.RuleUtils;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.Converter2;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author yangchangjian
 * @version 1.0
 * @Description:
 * @datetime 2019/4/20 13:23
 */
@Component(value = "paramFlowRuleProvider")
public class ParamFlowRuleProvider {
    @Autowired
    private ConfigService configService;

    @Autowired
    private Converter2<String, List<ParamFlowRuleEntity>> converter;

    public List<ParamFlowRuleEntity> getRules(String appName, String ip, String port) throws Exception {
        AssertUtil.notEmpty(appName, "app name cannot be empty");
        String config = this.configService.getConfig(appName + NacosConfigUtil.PARAM_FLOW_DATA_ID_POSTFIX, NacosConfigUtil.GROUP_ID, 3000);
        if (StringUtil.isBlank(config)){
            return Collections.emptyList();
        }
        String[] p = new String[]{appName, ip, port};
        return this.converter.convert(config, p);
    }
}
