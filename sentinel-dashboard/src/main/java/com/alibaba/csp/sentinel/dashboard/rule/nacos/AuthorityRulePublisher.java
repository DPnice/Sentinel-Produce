package com.alibaba.csp.sentinel.dashboard.rule.nacos;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
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
 * @datetime 2019/4/19 20:29
 */
@Component(value = "authorityRulePublisher")
public class AuthorityRulePublisher implements DynamicRulePublisher<List<AuthorityRuleEntity>> {
    @Autowired
    private ConfigService configService;

    @Autowired
    private Converter2<List<AuthorityRuleEntity>, String> converter;

    @Override
    public void publish(String app, List<AuthorityRuleEntity> rules) throws Exception {
        AssertUtil.notEmpty(app, "app name cannot be empty");
        if (rules == null) {
            return;
        }
        boolean b = this.configService.publishConfig(app + NacosConfigUtil.AUTH_DATA_ID_POSTFIX,
                NacosConfigUtil.GROUP_ID, this.converter.convert(rules));
        System.out.println(b);
    }
}
