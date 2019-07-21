package com.httpclient.crawler.test;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class CrawlerFirst {
    public static void main(String[] args) throws IOException {
        //1.打开浏览器，创建httpclient 对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //2、输入网址
        HttpGet httpGet = new HttpGet("http://news.baidu.com");
        //3、按回车，发起请求
        CloseableHttpResponse response = httpClient.execute(httpGet);
        //4、解析相应，获取数据
        //判断状态码是否是200
        if(response.getStatusLine().getStatusCode() == 200){
            HttpEntity httpEntity = response.getEntity();
            String content = EntityUtils.toString(httpEntity,"utf8");
            System.out.println(content);
        }
    }
}
