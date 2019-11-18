package com.example.serviceconsumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
public class RibbonClient {
  @Autowired
  private LoadBalancerClient lba;

  @RequestMapping("/test")
  public String getGreetings() throws Exception {
      ServiceInstance serviceInstance = lba.choose("service-provider");

      String baseUrl = serviceInstance.getUri().toString();
      baseUrl = baseUrl + "/hello";

      System.out.println(baseUrl);

      RestTemplate restTemplate = new RestTemplate();
      ResponseEntity<String> response = restTemplate.exchange(baseUrl, HttpMethod.GET, getHeaders(), String.class);
      return response.getBody();
  }

    private static HttpEntity<?> getHeaders() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }
}
