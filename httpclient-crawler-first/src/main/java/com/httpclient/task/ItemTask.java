package com.httpclient.task;

import com.httpclient.pojo.Item;
import com.httpclient.service.ItemService;
import com.httpclient.util.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ItemTask {
    @Autowired
    private HttpUtil httpUtil;
    @Autowired
    private ItemService itemService;

    @Scheduled(fixedDelay = 100*1000)
    public void itemTask() throws Exception{
        String url ="https://list.jd.com/list.html?cat=9987,653,655&page=" ;
        String url2 = "&sort=sort_rank_asc&trans=1&JL=6_0_0&ms=10#J_main";
        for (int i = 1; i < 10; i=i+2) {
            String html = httpUtil.doGet(url+i+url2);
            this.parseHtml(html);
        }
    }
    //解析数据，获取商品数据并存储
    private void parseHtml(String html) {
        //解析html获取document
        Document doc = Jsoup.parse(html);
        //获取spu信息
        Elements spuEles = doc.select("div#J_goodsList > ul >li");

        for (Element spuEle :spuEles ) {
            //获取spu
            long spu =Long.parseLong(spuEle.attr("data.spu"));
            //获取sku
            Elements skuEles = spuEle.select("li.ps.item");
            for (Element skuEle: skuEles  ) {
                //获取sku
                long sku = Long.parseLong(skuEle.select("[data.sku]").attr("data.sku"));
                //根据sku查询商品数据
                Item item = new Item();
                item.setSku(sku);
                List<Item> itemList = this.itemService.findAll(item);
                if(itemList.size()>0){
                    continue;
                }
                //设置商品的spu
                item.setSpu(spu);
                //获取商品的url
                String itemUlr = "https://item.jd.com/"+sku+".html";
                item.setUrl(itemUlr);
                String picUrl = skuEle.select("img[data-sku]").first().attr("src");
//                item.setPic();
//                item.setPrice();
//                item.setTitle();
                item.setCreatetime(item.getCreatetime());
                item.setUpdatetime(new Date());

            }
        }
    }

}
