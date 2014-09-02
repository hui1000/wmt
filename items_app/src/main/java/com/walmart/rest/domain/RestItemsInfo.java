package com.walmart.rest.domain;

import com.walmart.core.domain.Item;
import com.walmart.rest.controller.*;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@XmlRootElement
public class RestItemsInfo implements Serializable {

    private List<RestItem> items;
    private List<String> errorMessages;

    public List<RestItem> getItems() {
        return items;
    }

    public void setItems(List<RestItem> items) {
        if (items == null) {
            this.items = Collections.emptyList();
        } else {
            this.items = Collections.unmodifiableList(items);
        }
   }

   public void setErrorMessages(List<String> errorMessages) {
       this.errorMessages = errorMessages;
   }

   public List<String> getErrorMessages() {
       return errorMessages;
   }
}
