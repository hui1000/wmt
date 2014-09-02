package com.walmart.rest.domain;

import com.walmart.core.domain.Store;
import com.walmart.rest.controller.*;
import com.walmart.rest.domain.RestItem;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@XmlRootElement
public class RestStore implements Serializable {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Store toStore() {
        Store store = new Store();

        store.setId(id);
        store.setName(name);
        return store;
    }

    public static RestStore fromStore(Store store) {
        RestStore restStore = new RestStore();

        restStore.setId(store.getId());
        restStore.setName(store.getName());
        return restStore;
    }

}
