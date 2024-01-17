package com.example.problem_selection;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.job.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SelectProblemMenuActivity extends AppCompatActivity {
    List<String> problem_list;
    int problem_num=0;
    AssetManager assetManager;
    public static Context mContext;
    TextView filter_str;
    SeekBar filterboard;
    RecyclerView recycle_dic_problem;
    Button home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("aasdf","ASdf");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_problem_menu);

        //DBsetting
        varSetting();
        dbProblemList();
        recycleView();
        mContext = this;
        //ButtonListner setting
        listenerSetting();
        problem_score(problem_list.get(0));

    }

    private void dbProblemList(){
        try{
            assetManager =getAssets();
            String[] problem_string =assetManager.list("");
            problem_list = new ArrayList<>();
            for (String element:problem_string
            ) {
                if (element.contains("필기")){
                    problem_list.add(element);
                }

            }
        }catch (IOException e){e.printStackTrace();
        }
        ;
    }

    private void varSetting() {
        filter_str = findViewById(R.id.filter_str);
        filterboard = findViewById(R.id.filterboard);
        recycle_dic_problem = findViewById(R.id.recycle_dic_problem);
        home = findViewById(R.id.xml_home);
    }

    private void recycleView(){
        RecycleSelectProblemMenuView RecycleView = new RecycleSelectProblemMenuView(this,problem_list,problem_num);
        recycle_dic_problem.setAdapter(RecycleView);
        recycle_dic_problem.setLayoutManager(new LinearLayoutManager(this));
        recycle_dic_problem.setHasFixedSize(true);
        recycle_dic_problem.scrollToPosition(problem_num);
    }

    private void listenerSetting(){
        home.setOnClickListener(homelistener);
    }
    View.OnClickListener homelistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };

    public String problem_str(String problem_number){
        JSONObject problem_json;
        String problem_str="";
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
            problem_str = problem_json.getString("problem");

        }catch (IOException e){e.printStackTrace();} catch (JSONException e) {
            e.printStackTrace();
        }
        return problem_str;
    }
    public int problem_score(String problem_number){
        JSONObject problem_json;
        int score_str = 0;
        try{
            InputStream is = openFileInput(problem_number);
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
            score_str = problem_json.getInt("point_bar_int");


        }catch (IOException e){e.printStackTrace();} catch (JSONException e) {
            e.printStackTrace();
        }
        return score_str;
    }


}