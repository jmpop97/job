package com.example.job;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class problemActivity extends AppCompatActivity {
    AssetManager assetManager;
    JSONObject problem_json;
    String[] problem_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);

        //DB setting
        dbProblemList();
        dbProblemJson(problem_list[0]);

        //View setting

    }

    private void dbProblemList() {
        try{
            assetManager =getAssets();
            problem_list =assetManager.list("");
            for (String element:problem_list
            ) {
                Log.d("list",element);
            }
        }catch (IOException e){e.printStackTrace();
        }
        ;
    }

    private void dbProblemJson(String problem_number){
        try{
            InputStream is = assetManager.open(problem_number);
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader= new BufferedReader(isr);

            StringBuffer buffer= new StringBuffer();
            String line = reader.readLine();
            while (line!=null){
                buffer.append(line+"\n");
                line=reader.readLine();
            }

            String jsonData= buffer.toString();
            problem_json = new JSONObject(jsonData);
            String name= problem_json.getString("number");
            Log.d("json",name);

           }catch (IOException e){e.printStackTrace();} catch (JSONException e) {
            e.printStackTrace();
        }
        ;
    }

}