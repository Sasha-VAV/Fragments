package com.example.fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.AnimatorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class ValuteFragment extends Fragment {
    Handler handler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataThread dataThread = new DataThread();
        dataThread.start();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1,container,false);


        /*valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));
        valutes.add(new Valute("US dollar","121", null));*/
        /*
        https://www.cbr-xml-daily.ru/daily_json.js

        https://gist.github.com/sanchezzzhak/8606e9607396fb5f8216/raw/8a7209a4c1f4728314ef4208abc78be6e9fd5a2f/ISO3166_RU.json*/



        RecyclerView recyclerView = view.findViewById(R.id.RV);
        //ValuteAdapter valuteAdapter = new ValuteAdapter(valutes);
        //recyclerView.setAdapter(valuteAdapter);

        handler = new Handler(){
            @Override
            public void handleMessage(@NonNull Message msg){
                super.handleMessage(msg);

                ArrayList<Valute> valutes = (ArrayList<Valute>) msg.obj;
                ValuteAdapter valuteAdapter = new ValuteAdapter(valutes);
                recyclerView.setAdapter(valuteAdapter);

            }
        };

        return view;
    }

    class DataThread extends Thread{

        ArrayList<Valute> valutes = new ArrayList<>();

        @Override
        public void run() {
            super.run();
            try {
                URL infoLink = new URL("https://www.cbr-xml-daily.ru/daily_json.js");
                URL pictureLink = new URL("https://gist.github.com/sanchezzzhak/8606e9607396fb5f8216/raw/8a7209a4c1f4728314ef4208abc78be6e9fd5a2f/ISO3166_RU.json");

                String infoString = "";
                String pictureString = "";
                Bitmap bitmap = null;

                Scanner in = new Scanner(infoLink.openStream());
                while (in.hasNext()){
                    infoString += in.nextLine();
                }
                in.close();
                in = new Scanner(pictureLink.openStream());
                while (in.hasNext()){
                    pictureString += in.nextLine();
                }
                in.close();

                JSONObject jsonInfo = new JSONObject(infoString);
                JSONArray jsonPicture = new JSONArray(pictureString);

                JSONObject jsonValutes = jsonInfo.getJSONObject("Valute");
                for (int i=0; i < jsonValutes.names().length(); i++){
                    JSONObject jsonValute = jsonValutes.getJSONObject(jsonValutes.names().getString(i));
                    String charCode = jsonValute.getString("CharCode").substring(0,2);
                    for (int j = 0; j < jsonPicture.length(); j++){
                        if (jsonPicture.getJSONObject(j).getString("iso_code2").equals(charCode)){
                            String picUrl = jsonPicture.getJSONObject(j).getString("flag_url");
                            URL url = new URL("https:" + picUrl);

                            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                            con.setConnectTimeout(1500);
                            con.setReadTimeout(1500);

                            con.connect();

                            int responseCode = con.getResponseCode();

                            if (responseCode == 200){
                                InputStream inputStream = con.getInputStream();
                                bitmap = BitmapFactory.decodeStream(inputStream);
                            }
                        }
                    }
                    valutes.add(new Valute(jsonValute.getString("Name"), jsonValute.getString("Value"), bitmap));
                }

                Message msg = new Message();
                msg.obj = valutes;
                handler.sendMessage(msg);

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
