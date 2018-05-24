package com.example.carlo.applectorrss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.carlo.applectorrss.model.Articles;
import com.example.carlo.applectorrss.network.GsonRequest;
import com.example.carlo.applectorrss.network.RequestsManager;

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        doRequest();
    }



    private void doRequest() {

        setLoading(true);


        GsonRequest gsonRequest = new GsonRequest<>(BuildConfig.API_URL, Articles.class, null, new Response.Listener<Articles>() {

            @Override
            public void onResponse(Articles articles) {
                setLoading(false);
                //TODO: Prepare adapter of the recycler.
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                setLoading(false);
                Log.e("ERROR",error.toString());
            }
        });

        RequestsManager.getInstance().addToRequestQueue(this, gsonRequest);
    }

    private void setLoading(boolean loading) {
        if (loading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
