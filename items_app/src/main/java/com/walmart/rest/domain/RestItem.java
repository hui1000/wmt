package com.walmart.rest.domain;

import com.walmart.core.domain.Item;
import com.walmart.core.domain.Store;
import com.walmart.rest.controller.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

//import org.codehaus.jackson.annotate;


@XmlRootElement
public class RestItem implements Serializable {
    private Long id;
    private String description;
    private String locale;
    private Double price;
    private List<Long> storeIds;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setStoreIds(List<Long> storeIds) {
        //System.out.println("!!!!!! storeIds=" + storeIds); 
        this.storeIds = storeIds;
    }
    public List<Long> getStoreIds() {
        return storeIds;
    }

    public Item toItem() {
        Item item = new Item();

        item.setId(id);
        item.setDescription(description);
        item.setLocale(locale);
        item.setPrice(price);

        List<Store> coreStores = new ArrayList<Store>();
        if (storeIds != null) {
            for (Long storeId: storeIds) {
                Store store = new Store();
                store.setId(storeId);
                coreStores.add(store);
            }
            item.setStores(coreStores);
        }
        return item;
    }

    public static RestItem fromItem(Item item) {
        RestItem restItem = new RestItem();

        restItem.setId(item.getId());
        restItem.setDescription(item.getDescription());
        restItem.setLocale(item.getLocale());
        restItem.setPrice(item.getPrice());

        List<Long> storeIds = new ArrayList<Long>();
        List<Store> coreStores = item.getStores();
        if (coreStores != null) {
            for (Store store: coreStores) {
                RestStore rs = new RestStore();
                storeIds.add(store.getId());
            }
            restItem.setStoreIds(storeIds);
        }

        return restItem;
    }

}
