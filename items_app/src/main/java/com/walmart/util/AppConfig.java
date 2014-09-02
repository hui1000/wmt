package com.walmart.util;

import java.util.*;
import java.net.*;
import java.io.*;


public class AppConfig 
{
    public static SimpleProperties props = new SimpleProperties("/app.properties");

    public static String getProperty(String key) { return props.getProperty(key); }
    public static boolean getBoolean(String key) { return props.getBoolean(key); }
    public static int getInteger(String key) { return props.getInteger(key); }

}
