package com.walmart.util;

import java.io.*;
import java.net.*;
import java.util.*;

public class SimpleUtil
{
    public static void toFile(String filename, String message) // {{{
    {
        try
        {
            PrintWriter out =
                new PrintWriter(
                    new BufferedWriter(
                        new FileWriter(
                            new File(filename))));
            out.print(message);
            out.close();
        }  catch(IOException e) {
            e.printStackTrace();
        }
    } // }}}

    public static void appendFile(String filename, String message) // {{{
    {
        try
        {
            PrintWriter out =
                new PrintWriter(
                    new BufferedWriter(
                        new FileWriter(
                            new File(filename), true)));
            out.println(message);
            out.close();
        }  catch(IOException e) {
            e.printStackTrace();
        }
    } // }}}

}
