package com.example.oderapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.oderapp.R;

public class SearchActivity extends AppCompatActivity {
    Toolbar toolbarSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mapping();
        initActionBar();
    }

    private void mapping() {
        toolbarSearch = findViewById(R.id.toolbarSearch);
    }

    private void initActionBar() {
        setSupportActionBar(toolbarSearch);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarSearch.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}