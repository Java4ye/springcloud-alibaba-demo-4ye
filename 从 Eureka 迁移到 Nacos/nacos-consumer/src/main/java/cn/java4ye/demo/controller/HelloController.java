package cn.java4ye.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author Java4ye
 * @微信公众号： Java4ye
 * @GitHub https://github.com/Java4ye
 * @CSDN https://blog.csdn.net/weixin_40251892
 * @掘金 https://juejin.cn/user/2304992131153981
 */
@RestController
public class HelloController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/services")
    public List<String> getServices(){
        return discoveryClient.getServices();
    }

    @RequestMapping("/hello/{name}")
    public String sayHi(@PathVariable String name) {
        String providerName="provider-demo";

        List<ServiceInstance> instances = discoveryClient.getInstances(providerName);
        instances.stream().findAny().orElseThrow(() ->
                new IllegalStateException("")
        );

        return restTemplate.getForObject("http://" + providerName
                +"/hello/"+name, String.class);
    }
}
