package com.example.job;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SelectProblemMenuActivity extends AppCompatActivity {
    View filter_str;
    View filterboard;
    View recycle_dic_problem;
    View home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("aasdf","ASdf");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_problem_menu);

        //DBsetting
        varSetting();



    }

    private void varSetting() {
        filter_str = findViewById(R.id.filter_str);
        filterboard = findViewById(R.id.filterboard);
        recycle_dic_problem = findViewById(R.id.recycle_dic_problem);
        home = findViewById(R.id.xml_home);
    }
}