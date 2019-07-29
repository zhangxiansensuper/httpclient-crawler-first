package com.httpclient.service;

import com.httpclient.pojo.Item;

import java.util.List;

public interface ItemService {
    /**
     * 保存商品
     */
    public void save(Item item);

    /**
     * 查询商品
     */
    public List<Item> findAll(Item item);
}
