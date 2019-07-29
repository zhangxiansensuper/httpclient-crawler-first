package com.httpclient.service.impl;

import com.httpclient.dao.ItemDao;
import com.httpclient.pojo.Item;
import com.httpclient.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemDao itemDao;

    /**
     * 保存商品
     * @param item
     */
    @Override
    public void save(Item item) {
        itemDao.save(item);
    }

    @Override
    public List<Item> findAll(Item item) {
        //声明查询条件
        Example<Item> example = Example.of(item);
        //根据查询条件查询
        List<Item> list = itemDao.findAll(example);
        return  list;
    }
}
