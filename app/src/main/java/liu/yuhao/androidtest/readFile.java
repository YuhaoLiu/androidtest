package liu.yuhao.androidtest;

import android.content.Context;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;


public class readFile {

    public static String read(String inputUrl) {

        StringBuilder result = new StringBuilder();
        try {
            URL urlObject = new URL(inputUrl);
            URLConnection cn = urlObject.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(cn.getInputStream(),"utf-8"));
            String inputLine = null;
            while ( (inputLine = in.readLine()) != null) {
                result.append(inputLine);
            }
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();

    }

    public static JSONArray getMovie(JSONObject result){
        try {
            return result.getJSONArray("movies");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Integer getTotal (JSONObject result){
        try {
            return result.getInt("total");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}



