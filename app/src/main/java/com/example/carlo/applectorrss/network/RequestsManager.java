package com.example.carlo.applectorrss.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by: 'davidpizarro' on '23/3/17'
 * Copyright © 2017 Minsait. All rights reserved.
 */
public class RequestsManager {

    private static RequestsManager mInstance;
    private RequestQueue mRequestQueue;

    private RequestsManager() {
    }

    public static synchronized RequestsManager getInstance() {
        if (mInstance == null) {
            mInstance = new RequestsManager();
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue(Context ctx) {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Context ctx, Request<T> req) {
        getRequestQueue(ctx).add(req);
    }
}


