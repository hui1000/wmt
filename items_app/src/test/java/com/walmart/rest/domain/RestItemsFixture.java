package com.walmart.rest.domain;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class RestItemsFixture {

  public static String NEW_ITEM_DESC = "New Toy Item";
  public static String UPDATE_ITEM_DESC = "Update Toy Item ";
  public static String NEW_ITEM_LOCALE= "US";
  public static Double NEW_ITEM_PRICE = Double.valueOf(5.99);
  public static List<Long> NEW_ITEM_STOREIDS = Arrays.asList(Long.valueOf(2), Long.valueOf(5));


  public static String NEW_MULTI_ITEMS_DESC = "New Toy Item ";
  public static int NEW_MULTI_ITEMS_NUM = 5;

  public static int UPDATE_MULTI_ITEMS_NUM = 5;
  public static String UPDATE_ITEM_LOCALE= "CN";
  public static Double UPDATE_ITEM_PRICE = Double.valueOf(10.99);
  public static List<Long> UPDATE_ITEM_STOREIDS = Arrays.asList(Long.valueOf(4), Long.valueOf(1));


  public static String updateItemJSON(int id) {
      String request  = "{\"items\":[{\"id\":" + id + ", \"description\":\""
          + UPDATE_ITEM_DESC + id 
          + "\", \"locale\":\"" + UPDATE_ITEM_LOCALE
          + "\", \"price\":\"" + UPDATE_ITEM_PRICE
          + "\", \"storeIds\":" + UPDATE_ITEM_STOREIDS 
          + "}]}";
      System.out.println("--- request is "+ request);
      return request;
  }

  public static String newItemJSON() {
      String request  = "{\"items\":[{\"description\":\""+ NEW_ITEM_DESC 
          + "\", \"locale\":\"" + NEW_ITEM_LOCALE
          + "\", \"price\":\"" + NEW_ITEM_PRICE
          + "\", \"storeIds\":" + NEW_ITEM_STOREIDS 
          + "}]}";
      System.out.println("--- request is "+ request);
      return request;
  }

  public static String newMultipleItemsJSON() {
      String request  = "{\"items\":[";
      
      // try 5 items 
      for (int i = 0; i < NEW_MULTI_ITEMS_NUM; i++) {
          request = request + "{\"description\":\""+ NEW_MULTI_ITEMS_DESC 
          + i + "\", \"locale\":\"" + NEW_ITEM_LOCALE
          + "\", \"storeIds\":" + NEW_ITEM_STOREIDS;

          if (i==NEW_MULTI_ITEMS_NUM-1) {
              request = request +"}";
          } else {
              request = request +"}, ";
          }
      }
      request = request + "]}";
      System.out.println("--- request is "+ request);
      return request;
  }

  public static String updateMultipleItemsJSON(List<Long> update_ids) {
      String request  = "{\"items\":[";
      
      int i = 0;
      // use the id list passed  
      for (Long item_id: update_ids) {
          request = request + "{\"description\":\""+ UPDATE_ITEM_DESC 
          + "\", \"locale\":\"" + UPDATE_ITEM_LOCALE
          + "\", \"price\":\"" + UPDATE_ITEM_PRICE
          + "\", \"storeIds\":" + UPDATE_ITEM_STOREIDS
          + ", \"id\":" + item_id;

          i = i+1; 
          if (i==update_ids.size()) {
              request = request +"}";
          } else {
              request = request +"}, ";
          }
      }
      request = request + "]}";
      System.out.println("--- request is "+ request);
      return request;
  }
}

