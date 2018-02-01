package com.hubert.ZuulGateway;

import com.hubert.ZuulGateway.Filter.AccessFilter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.http.client.*;


@SpringCloudApplication   //SpringBootApplication & EnableDiscoveryClient & EnableCircuitBreaker
//@SpringBootApplication
//@EnableDiscoveryClient
//@EnableCircuitBreaker
@ComponentScan
@EnableZuulProxy
public class ZuulGatewayApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ZuulGatewayApplication.class).web(true).run(args);
	}

	/**
	 * 注册网关过滤器
	 * @return
	 */
	@Bean
	public AccessFilter accessFilter() {
		return new AccessFilter();
	}
}
