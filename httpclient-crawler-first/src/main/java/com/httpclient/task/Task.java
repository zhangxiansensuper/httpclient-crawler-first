package com.httpclient.task;

import com.httpclient.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Task {
    @Autowired
    private HttpUtil httpUtil;

    @Scheduled(fixedDelay = 100*1000)
    public void itemTask(){
        String url ="https://search.jd.com/Search?keyword=%E6%89%8B%E6%9C%BA&enc=utf-8&qrst=1&rt=1&stop=1&vt=2&wq=%E6%89%8B%E6%9C%BA&cid2=653&cid3=655&page=";
        for (int i = 1; i < 10; i=i+2) {
            String html = httpUtil.doGet(url);
            this.parseHtml(html);
        }
    }

    //解析数据，获取商品数据并存储
    private void parseHtml(String html) {

    }

}
