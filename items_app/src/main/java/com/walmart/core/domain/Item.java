package com.walmart.core.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.beans.BeanUtils;
import java.util.*;
import javax.persistence.*;

@Entity
public class Item {
    @Id
    @GeneratedValue()
    private Long id;

    private String description;
    private String locale;
    private Double price;

   

    @ManyToMany(fetch=FetchType.EAGER)
    private List<Store> stores;

    public Item() {
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
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

    public List<Store> getStores() {
        return stores;
    }

    public void setStores(List<Store> stores) {
        this.stores = stores;
    }
}
