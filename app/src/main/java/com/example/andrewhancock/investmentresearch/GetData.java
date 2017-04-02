package com.example.andrewhancock.investmentresearch;

import android.renderscript.ScriptGroup;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Andrew Hancock on 2/24/2017.
 */

public class GetData {

    /*
    This method connects to the url sent as a parameter
     */

    public static HttpsURLConnection getConnection(String url){
        //print out the url being connected to
        System.out.println("The url that is being connected to is: " + url);
        //declares new http connection
        HttpsURLConnection web = null;

        try{
            //attempts opening the connection to the url
            web = (HttpsURLConnection)new URL(url).openConnection();
            //sets the timeout to 30 seconds(if it doesn't respond?)
            web.setReadTimeout(30000);
            //seems like I don't need this?
            //web.setRequestProperty("");
            //gets response code from webpage. Webpage returns 200 for a good read
            int webpageResponseCode = web.getResponseCode();
            //prints out response code from webpage
            System.out.println("The response code is: " + webpageResponseCode);
        }catch(MalformedURLException e){
            //reports the malformed url exception
            Log.e("getConnection()", "Could not connect: " + e.toString());
        }catch(IOException e){
            //report the exception involving input output errors
            Log.e("getConnection()", "Could not connect: " + e.toString());
        }
        //returns an http connection like its supposed to.
        return web;
    }

    /*
    This method gets the connection to the url and reads the contents of the
    String that it returns
     */

    public static String readContents(String url){
        //gets a new connection object and makes it the connection to the url
        HttpsURLConnection web = getConnection(url);
        //check to see if the connection is working
        if(web == null){
            //returns nothing if the connection doesn't return anything
            return null;
        }

        try{
            //creates a string reader that can hold up to 8kb of data at once
            //this makes sense for memory purposes
            StringBuffer sb = new StringBuffer(8192);
            //temporary string for string buffer to use each time it collects more data
            String tmp = "";
            //creates a reader from the webpage input stream
            BufferedReader br = new BufferedReader(new InputStreamReader(web.getInputStream()));
            //while the next line isn't empty keep adding lines to the string buffer
            while((tmp = br.readLine()) != null){
                sb.append(tmp).append("\n");
            }
            //close the buffer reader
            br.close();
            //return the string of input from the url
            return sb.toString();
        }catch(IOException e){
            //report the input output error
            Log.d("READ FAILED", e.toString());
            //method returns an empty string if there is an input output error
            return null;
        }
    }

}




























