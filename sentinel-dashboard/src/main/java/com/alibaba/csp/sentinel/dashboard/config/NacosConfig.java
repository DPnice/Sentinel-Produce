/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.csp.sentinel.dashboard.config;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.*;
import com.alibaba.csp.sentinel.dashboard.util.RuleUtils;
import com.alibaba.csp.sentinel.datasource.Converter2;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @author Eric Zhao
 * @since 1.4.0
 */
@Configuration
public class NacosConfig {

    @Value("${nacos.config.server-addr}")
    private String serverAddr;
    @Value("${nacos.config.sentinel.namespace}")
    private String namespace;

    @Bean
    public Converter2<List<FlowRuleEntity>, String> flowRuleEntityEncoder() {
        return (s, p) -> JSON.toJSONString(s);
    }

    @Bean
    public Converter2<String, List<FlowRuleEntity>> flowRuleEntityDecoder() {
        return (s, p) -> JSON.parseArray(s, FlowRuleEntity.class);
    }

    @Bean
    public Converter2<List<DegradeRuleEntity>, String> degradeRuleEntityEncoder() {
        return (s, p) -> JSON.toJSONString(s);
    }

    @Bean
    public Converter2<String, List<DegradeRuleEntity>> degradeRuleEntityDecoder() {
        return (s, p) -> JSON.parseArray(s, DegradeRuleEntity.class);
    }


    /**
     * 授权规则
     */
    @Bean
    public Converter2<List<AuthorityRuleEntity>, String> authorityRuleEntityEncoder() {
        return (s, p) -> JSON.toJSONString(s);
    }

    /**
     * 授权规则
     */
    @Bean
    public Converter2<String, List<AuthorityRuleEntity>> authorityRuleEntityDecoder() {
        return (s, p) -> JSON.parseArray(s, AuthorityRuleEntity.class);
    }

    /**
     * 系统规则
     */
    @Bean
    public Converter2<List<SystemRuleEntity>, String> systemRuleEntityEncoder() {
        return (s, p) -> JSON.toJSONString(s);
    }

    /**
     * 系统规则
     */
    @Bean
    public Converter2<String, List<SystemRuleEntity>> systemRuleEntityDecoder() {
        return (s, p) -> JSON.parseArray(s, SystemRuleEntity.class);
    }

    /**
     * 热点规则
     */
    @Bean
    public Converter2<List<ParamFlowRuleEntity>, String> paramFlowRuleEntityEncoder() {
        return (rules, p) -> JSON.toJSONString(
                rules.stream().map(ParamFlowRuleEntity::getRule).collect(Collectors.toList())
        );
    }

    /**
     * 热点规则
     */
    @Bean
    public Converter2<String, List<ParamFlowRuleEntity>> paramFlowRuleEntityDecoder() {
        return (rules, p) -> Objects.requireNonNull(RuleUtils.parseParamFlowRule(rules)).stream()
                .map(rule -> ParamFlowRuleEntity
                        .fromAuthorityRule(p[0], p[1], Integer.valueOf(p[2]), rule))
                .collect(Collectors.toList());
    }

    @Bean
    public ConfigService nacosConfigService() throws Exception {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.NAMESPACE, namespace);
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        return NacosFactory.createConfigService(properties);
    }
}
