package com.example.serviceconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class RibbonClient {
  @Autowired
  private LoadBalancerClient lba;

  @RequestMapping("/test")
  public String test() {
    ServiceInstance serviceInstance = lba.choose("service-provider");

    String baseUrl = serviceInstance.getUri() + "/hello";
    System.out.println(baseUrl);

    RestTemplate restTemplate = new RestTemplate();
    return restTemplate.getForObject(baseUrl, String.class);
  }
}
