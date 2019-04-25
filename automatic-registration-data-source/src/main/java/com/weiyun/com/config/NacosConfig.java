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
package com.weiyun.com.config;

import com.alibaba.nacos.api.PropertyKeyConst;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author DPnice
 */
@Configuration
public class NacosConfig {

    @Value("${nacos.config.server-addr:127.0.0.1:8848}")
    private String serverAddr;
    @Value("${nacos.config.sentinel.namespace:ec3e95f8-2e6b-4857-95a4-7e68083289b4}")
    private String namespace;

    public static Properties propertiesBean;

    @Bean("nacosProperties")
    public Properties nacosProperties() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.NAMESPACE, namespace);
        properties.put(PropertyKeyConst.SERVER_ADDR, serverAddr);
        propertiesBean = properties;
        return properties;
    }
}
