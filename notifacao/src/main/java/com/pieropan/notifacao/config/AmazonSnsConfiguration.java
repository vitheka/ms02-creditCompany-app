package com.pieropan.notifacao.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonSnsConfiguration {

    @Value("{aws.acessKey}")
    private String acessKey;
    @Value("{aws.secretKey}")
    private String secretKey;
}
