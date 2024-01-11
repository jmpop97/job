package com.example.Home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.job.ProblemActivity;
import com.example.job.R;
import com.example.job.SelectProblemMenuActivity;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("ver", "1");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //vars
        Button site_button = findViewById(R.id.site);
        Button problem_button = findViewById(R.id.problem);
        Button solve_button = findViewById(R.id.solve);

        site_button.setOnClickListener(site_listener);
        problem_button.setOnClickListener(problem_listener);
        solve_button.setOnClickListener(solve_listener);

    }

    View.OnClickListener site_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            intent =new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://cafe.naver.com/go2ndlife"));
            startActivity(intent);
        }
    };
    View.OnClickListener problem_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            TextView phl = (TextView) findViewById(R.id.textView);
//            int phlength=phl.getMeasuredWidth();
            //            intent.putExtra("폰길이",phlength);

            intent = new Intent(getApplicationContext(), ProblemActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener solve_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            intent = new Intent(getApplicationContext(), SelectProblemMenuActivity.class);
            startActivity(intent);
        }
    };

}