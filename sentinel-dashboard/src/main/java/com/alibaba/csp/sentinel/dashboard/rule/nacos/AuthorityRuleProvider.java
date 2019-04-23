package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.util.NacosConfigUtil;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.csp.sentinel.datasource.Converter2;
import com.alibaba.csp.sentinel.util.AssertUtil;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author yangchangjian
 * @version 1.0
 * @Description:
 * @datetime 2019/4/19 20:27
 */
@Component(value = "authorityRuleProvider")
public class AuthorityRuleProvider implements DynamicRuleProvider<List<AuthorityRuleEntity>> {
    @Autowired
    private ConfigService configService;

    @Autowired
    private Converter2<String, List<AuthorityRuleEntity>> converter;

    @Override
    public List<AuthorityRuleEntity> getRules(String appName) throws Exception {
        AssertUtil.notEmpty(appName, "app name cannot be empty");
        String config = this.configService.getConfig(appName + NacosConfigUtil.AUTH_DATA_ID_POSTFIX, NacosConfigUtil.GROUP_ID, 3000);
        List<AuthorityRuleEntity> convert = this.converter.convert(config);
        return convert;
    }

}
