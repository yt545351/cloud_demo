package com.example.system.utils;

import com.alibaba.fastjson.JSON;
import com.example.system.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * HTTP工具
 *
 * @author laughlook
 * @date 2022/07/21
 */
@Slf4j
public class HttpUtil {
    public static void main(String[] args) {
        postHttp();
    }

    public static void postHttp() {
        RestTemplate restTemplate = new RestTemplate();
        //解决(响应数据可能)中文乱码 的问题
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        // 移除原来的转换器
        converterList.remove(1);
        //设置字符编码为utf-8
        HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        //添加新的转换器(注:convert顺序错误会导致失败)
        converterList.add(1, converter);
        restTemplate.setMessageConverters(converterList);

        // 请求头信息
        // HttpHeaders实现了MultiValueMap接口
        HttpHeaders httpHeaders = new HttpHeaders();
        // 设置contentType
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        // 给请求header中添加一些数据
        httpHeaders.add("xxxxxx", "xxxxxx");
        httpHeaders.add("xxxxxx", "xxxxxx");

        //将请求头、请求体数据，放入HttpEntity中
        //请求体的类型任选即可;只要保证 请求体 的类型与HttpEntity类的泛型保持一致即可
        //这里手写了一个json串作为请求体 数据 (实际开发时,可使用fastjson、gson等工具将数据转化为json串)
        String httpBody = "{\"xxxxxx\":\"" + 123 + "\",\"xxxxxx\":\"" + 123 + "\"}";
        HttpEntity<String> httpEntity = new HttpEntity<String>(httpBody, httpHeaders);

        //URI
        String paramsUrl = "url";
        URI uri = URI.create(paramsUrl);

        //执行请求并返回结果
        //此处的泛型对应响应体数据类型;即:这里指定响应体的数据装配为Object
        ResponseEntity<Object> response;
        User user = null;
        try {
            response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Object.class);
            if (response.hasBody()) {
                String data = JSON.toJSONString(((Map<String, Object>) response.getBody()).get("data"));
                user = JSON.parseObject(data, User.class);
            }
        } catch (Exception e) {

        }

    }
}
