package com.example.problem;

import static java.lang.Boolean.FALSE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.AssetManager;
import android.os.Bundle;
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
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ProblemActivity extends AppCompatActivity {
    Boolean open=FALSE;
    int problem_num=0;
    AssetManager assetManager;

    List<String> problem_list;
    TextView problem;
    TextView user_solution;
    TextView solution;
    TextView point;
    SeekBar point_bar;
    Button open_solution;
    Button next_problem;
    Button home;
    RecyclerView menu;

    String problem_str="";
    String user_solution_str="";
    String solution_str="";
    String point_str="";
    int point_bar_int=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem);

        //DB setting
        varSetting();
        dbProblemList();
        dbProblemNum();
        dbProblemJson(problem_list.get(problem_num));
        dbUserJson(problem_list.get(problem_num));
        //View setting
        viewProblem();
        recycleView();
        //ButtonListner setting
        listenerSetting();
    }



    //var setting
    private void varSetting() {
        problem = findViewById(R.id.xml_problem_text);
        user_solution = findViewById(R.id.xml_user_solution);
        solution = findViewById(R.id.xml_solution);
        point = findViewById(R.id.xml_point);
        point_bar = findViewById(R.id.xml_point_bar);
        open_solution = findViewById(R.id.xml_open_solution);
        next_problem = findViewById(R.id.xml_next_problem);
        home = findViewById(R.id.xml_home);
        menu = findViewById(R.id.xml_problem_list);
    }

    //read
    private void dbProblemNum() {
        JSONObject problem_json;
        user_solution_str = "";
        point_bar_int = 0;
        try{
            InputStream is = openFileInput("problem_num.json");
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
            problem_num = problem_json.getInt("problem_num");

        }catch (IOException e){e.printStackTrace();} catch (JSONException e) {
            e.printStackTrace();
        }
        ;
    }
    private void dbProblemList() {
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
    private void dbProblemJson(String problem_number){
        JSONObject problem_json;
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
            solution_str = problem_json.getString("solution");

           }catch (IOException e){e.printStackTrace();} catch (JSONException e) {
            e.printStackTrace();
        }
        ;
    }
    private void dbUserJson(String problem_number){
        JSONObject problem_json;
        user_solution_str = "";
        point_bar_int = 0;
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
            user_solution_str = problem_json.getString("user_solution_str");
            point_bar_int = problem_json.getInt("point_bar_int");


        }catch (IOException e){e.printStackTrace();} catch (JSONException e) {
            e.printStackTrace();
        }
        ;
    }

    //save
    private void saveLastNum() throws JSONException {
        JSONObject obj = new JSONObject();
        obj.put("problem_num", problem_num);
        try{
            OutputStream out;
            out = openFileOutput("problem_num.json",MODE_PRIVATE);
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(out,"UTF-8"));
            writer.append(obj.toString());
            writer.close();
        }catch (IOException e){
        }
    }
    private void saveUserDB() throws JSONException {
        JSONObject obj = new JSONObject();
        user_solution_str=user_solution.getText().toString();
        obj.put("user_solution_str", user_solution_str);
        obj.put("point_bar_int", point_bar_int);
        try{
            OutputStream out;
            out = openFileOutput(problem_list.get(problem_num),MODE_PRIVATE);
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(out,"UTF-8"));
            writer.append(obj.toString());
            writer.close();
        }catch (IOException e){
        }
    }


    //view
    private void viewProblem() {
        problem.setText(problem_str);
        if (open){
            point_str = "점수 : "+point_bar_int+"/10";

            point.setVisibility(View.VISIBLE);
            point_bar.setVisibility(View.VISIBLE);

            solution.setText(solution_str);
            point.setText(point_str);
            point_bar.setProgress((int) point_bar_int);
            open_solution.setText("모범답안 닫기");

        }
        else{
            point.setVisibility(View.GONE);
            point_bar.setVisibility(View.GONE);

            solution.setText("");
            user_solution.setText(user_solution_str);
            open_solution.setText("모범답안");
        }
    }
    private void recycleView(){
        RecycleProblemView problemMenuRecycleView = new RecycleProblemView(this,problem_list,problem_num);
        menu.setAdapter(problemMenuRecycleView);
        menu.setLayoutManager(new LinearLayoutManager(this));
        menu.setHasFixedSize(true);
        menu.scrollToPosition(problem_num);
    }
    //control
    private void listenerSetting() {
        open_solution.setOnClickListener(solutionlistener);
        next_problem.setOnClickListener(nextlistener);
        home.setOnClickListener(homelistener);
        point_bar.setOnSeekBarChangeListener(pointlistener);
        RecycleProblemView.setOnItemClickListener(menuitemListener);
    }
    View.OnClickListener solutionlistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            open=!open;
            viewProblem();
        }
    };
    View.OnClickListener nextlistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                saveUserDB();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            open=FALSE;
            if (problem_num+1 < problem_list.size()){
                problem_num+=1;
                dbProblemJson(problem_list.get(problem_num));
                dbUserJson(problem_list.get(problem_num));
                viewProblem();
            }
            else{
                finish();
            }
        }
    };
    View.OnClickListener homelistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finish();
        }
    };
    SeekBar.OnSeekBarChangeListener pointlistener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            point_bar_int=i;
            viewProblem();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    RecycleProblemView.OnItemClickListener menuitemListener= new RecycleProblemView.OnItemClickListener() {
        @Override
        public void onItemClick(View v, int pos, int n) {
            problem_num=pos;
            dbProblemJson(problem_list.get(problem_num));
            dbUserJson(problem_list.get(problem_num));
            viewProblem();
            recycleView();
        }
    };
    //close
    @Override
    protected void onPause(){
        super.onPause();
        try {
            saveLastNum();
            saveUserDB();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    }
