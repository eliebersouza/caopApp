package br.mp.mpro.caopapp.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "br.mp.mpro.caopapp")
public class FeignConfiguration {

}
