package com.walmart.util;

import java.util.*;
import java.net.*;
import java.io.*;


public class SimpleProperties
{
    private Properties properties = null;
    private ResourceBundle _ResourceBundle = null;

    public SimpleProperties(String propFileName)
    {
        load(propFileName);
    }

    public void load(String filename)
    {
        properties = new Properties();
        try{
            System.err.println(" searching " + filename);
            java.net.URL url = SimpleProperties.class.getResource(filename);

            if (url != null)
            {
                System.err.println("** Read parameters from " + url);

                InputStream is = url.openStream();
                properties.load(is);
                is.close();
            }
            System.err.println("** config file loaded !!" );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SimpleProperties() { }

    /**
     * Retrieves a String value based on a String key
     */
    public String get(String name) {
        return getProperty(name);
    }
    public String getProperty(String name) {
        String value = getProperty(name, null);
        System.out.println("name=" +name + " value=" + value);
        if (value == null) {
            System.err.println("parameter " + name + " is not defined in properties file");
        }
        return value;
    }

    public String getProperty(String name, String defaultValue) {
        String value = properties.getProperty(name);
        return value != null ? value : defaultValue;
    }

    public boolean getBoolean(String key)
    {
        return Boolean.valueOf(getProperty(key)).booleanValue();
    }

    public int getInteger(String key)
    {
        return Integer.parseInt(getProperty(key));
    }

    public long getLong(String key) {
        return Long.parseLong(getProperty(key));
    }

    public int getInteger(String key, int defaultIntValue)
    {
        String valueStr = getProperty(key);
        if (valueStr == null)
            return defaultIntValue;
        else
            return Integer.parseInt(getProperty(key));
    }
}
