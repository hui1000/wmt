package com.walmart.core.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.beans.BeanUtils;
import java.util.*;
import javax.persistence.*;

@Entity
public class Store {
    @Id
    @GeneratedValue()
    private Long id;

    private String name;

    @ManyToMany(mappedBy="stores")
    private List<Item> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
