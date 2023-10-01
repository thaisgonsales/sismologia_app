package com.aiep.simologia_app;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.aiep.simologia_app.adapter.SismosAdapter;
import com.aiep.simologia_app.model.Sismos;
import com.aiep.simologia_app.network.ApiClient;
import com.aiep.simologia_app.network.ApiSismos;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<Sismos> sismos;
    private RecyclerView recyclerView;
    private SismosAdapter sismosAdapter;
    private Timer timer;
    private Handler handler = new Handler();
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rvSismoPrincipal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        sismos = new ArrayList<>();
        sismosAdapter = new SismosAdapter(sismos, getApplicationContext());
        recyclerView.setAdapter(sismosAdapter);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                actualizarManualmente();
            }
        });

        startAutoUpdate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startAutoUpdate();
        showSismos();
    }

    private void showSismos() {
        Call<List<Sismos>> call = ApiClient.getClient().create(ApiSismos.class).getSismos();
        call.enqueue(new Callback<List<Sismos>>() {
            @Override
            public void onResponse(@NonNull Call<List<Sismos>> call, @NonNull Response<List<Sismos>> response) {
                if (response.isSuccessful()) {
                    sismos.clear();
                    sismos.addAll(response.body());
                    sismosAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(MainActivity.this, "Actualización exitosa", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Sismos>> call, @NonNull Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopAutoUpdate();
    }

    private void startAutoUpdate() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        showSismos();
                    }
                });
            }
        }, 0, 600000);
    }

    private void stopAutoUpdate() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private void actualizarManualmente() {
        Call<List<Sismos>> call = ApiClient.getClient().create(ApiSismos.class).getSismos();
        call.enqueue(new Callback<List<Sismos>>() {
            @Override
            public void onResponse(@NonNull Call<List<Sismos>> call, @NonNull Response<List<Sismos>> response) {
                if (response.isSuccessful()) {
                    sismos.clear();
                    sismos.addAll(response.body());
                    sismosAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(MainActivity.this, "Actualización exitosa", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Sismos>> call, @NonNull Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_LONG).show();
            }
        });
    }
}
