package com.httpclient.util;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

@Component
public class HttpUtil {

    private PoolingHttpClientConnectionManager cm;

    public HttpUtil(){
        this.cm = new PoolingHttpClientConnectionManager();
        //设置连接数
        this.cm.setMaxTotal(100);
        //设置每个主机的最大连接数
        this.cm.setDefaultMaxPerRoute(10);
    }

    /**
     * 根据URL获取页面数据
     * @param url
     * @return
     */
    public String doGet(String url){
        //1.打开浏览器，创建httpclient 对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.cm).build();
        //2、输入网址
        HttpGet httpGet = new HttpGet(url);
        //设置请求信息
        httpGet.setConfig(this.getConfig());

        CloseableHttpResponse response = null;
        try {
            //3、按回车，发起请求
            response = httpClient.execute(httpGet);
            //4、解析相应，获取数据
            //判断状态码是否是200
            if(response.getStatusLine().getStatusCode() == 200){
                if(response.getEntity() != null){
                    String content = EntityUtils.toString(response.getEntity(),"utf8");
                    return content;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "返回值为空字符串";
    }

    private RequestConfig getConfig() {
        RequestConfig config = RequestConfig.custom().
                setConnectTimeout(1000).
                setConnectionRequestTimeout(500).
                setSocketTimeout(10000).
                build();
        return config;
    }

    /**
     * 下载图片
     * @param url
     * @return
     */
    public String doGetImage(String url){
        //1.打开浏览器，创建httpclient 对象
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(this.cm).build();
        //2、输入网址
        HttpGet httpGet = new HttpGet(url);
        //设置请求信息
        httpGet.setConfig(this.getConfig());

        CloseableHttpResponse response = null;
        try {
            //3、按回车，发起请求
            response = httpClient.execute(httpGet);
            //4、解析相应，获取数据
            //判断状态码是否是200
            if(response.getStatusLine().getStatusCode() == 200){
                if(response.getEntity() != null){
                    //获取图片后缀
                    String extName = url.substring(url.lastIndexOf("."));
                    //创建图片名，重新命名图片
                    String picName = UUID.randomUUID().toString()+extName;
                    //下载图片
                    //先声明OutputStream
                    OutputStream outputstream = new FileOutputStream(new File("C:\\Users\\27891\\Desktop"+picName));
                    //返回图片名
                    return picName;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //如果下载失败，则返回下载失败
        return "下载失败";
    }
}
