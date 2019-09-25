package com.bawp.nodo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bawp.nodo.model.NoDo;
import com.bawp.nodo.model.NoDoViewModel;
import com.bawp.nodo.ui.NoDoListAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int NEW_NODO_REQUEST_CODE = 1;
    private NoDoListAdapter noDoListAdapter;
    private NoDoViewModel noDoViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        noDoViewModel = ViewModelProviders.of(this)
                .get(NoDoViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        noDoListAdapter = new NoDoListAdapter(this);
        recyclerView.setAdapter(noDoListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(MainActivity.this, NewNoDoActivity.class);
               startActivityForResult(intent, NEW_NODO_REQUEST_CODE);
            }
        });

        noDoViewModel.getAllNoDos().observe(this, new Observer<List<NoDo>>() {
            @Override
            public void onChanged(@Nullable List<NoDo> noDos) {
                //update the cached copy of nodos in the adapter
                noDoListAdapter.setNoDos(noDos);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_NODO_REQUEST_CODE && resultCode == RESULT_OK ) {
            assert data != null;
            NoDo noDo = new NoDo(data.getStringExtra(NewNoDoActivity.EXTRA_REPLY));
            noDoViewModel.insert(noDo);
        }else {
            Toast.makeText(this, R.string.empty_not_saved, Toast.LENGTH_LONG)
                    .show();
        }
    }
}
