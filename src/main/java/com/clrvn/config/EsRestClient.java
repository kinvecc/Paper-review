package com.clrvn.config;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EsRestClient {

    @Value("${elasticsearch.ip}")
    String ipAddress;


    public RestHighLevelClient highLevelClient() {

        String[] address = ipAddress.split(":");
        String ip = address[0];
        int port = Integer.parseInt(address[1]);
        //建立连接
        HttpHost httpHost = new HttpHost(ip, port, "http");
        return new RestHighLevelClient(RestClient.builder(httpHost));

    }

}
