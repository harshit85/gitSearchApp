package com.example.gitsearchappp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button mySearcbbutton;
    private ProgressBar progressBar;
    ArrayList<ItemLayout> itemLayout;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton btnFavouite;
    private SqliteHelper sqliteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.Search);
        mySearcbbutton = findViewById(R.id.btn_search);
        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        progressBar = findViewById(R.id.progressBar);
        itemLayout = new ArrayList<>();
        mySearcbbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchResults(view);
            }
        });
        btnFavouite = findViewById(R.id.btn_favorite);
        btnFavouite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFavourite();
            }
        });
    }

    public void searchResults(View view) {
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        String reppositoryName;
        reppositoryName =editText.getText().toString();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/search/").addConverterFactory(GsonConverterFactory.create()).build();
        APISearchRepositries searchRepositries = retrofit.create(APISearchRepositries.class);
        Call<Repositry> call = searchRepositries.getSearchResult(reppositoryName, "stars", "desc");
        call.enqueue(new Callback<Repositry>() {
            @Override
            public void onResponse(Call<Repositry> call, Response<Repositry> response) {
                progressBar.bringToFront();
                progressBar.setVisibility(View.VISIBLE);
                if (!response.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    return;
                }
                progressBar.setVisibility(View.GONE);
                Repositry repo = response.body();
                if(itemLayout.size()>0)
                {
                    itemLayout.removeAll(itemLayout);
                }
                for (items item : repo.getItems()) {
                    itemLayout.add(new ItemLayout(item.getId(), item.getFull_name(), item.getDescription(), item.getLanguage()));
                }
                mAdapter = new ItemAdapter(itemLayout);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<Repositry> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
            }
        });

    }

    public void showFavourite() {
        Intent intent = new Intent(MainActivity.this,Main2Activity.class);
        startActivity(intent);
    }

    public void showDialogMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


    @Override
    protected void onResume() {
        super.onResume();
        mAdapter = new ItemAdapter(itemLayout);
        mRecyclerView.setAdapter(mAdapter);
    }

}
