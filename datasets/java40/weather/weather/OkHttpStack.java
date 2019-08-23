package com.android.wpvolley.toolbox;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.OkUrlFactory;
import com.squareup.okhttp.Request;

/**
 * An {@link com.android.wpvolley.toolbox.HttpStack HttpStack} implementation which uses OkHttp as its transport.
 */
public class OkHttpStack extends HurlStack {
    private final OkUrlFactory client;

    public OkHttpStack() {
        this(new OkHttpClient());
    }

    public OkHttpStack(OkHttpClient client) {
        if (client == null) {
            throw new NullPointerException("Client must not be null.");
        }
        this.client = new OkUrlFactory(client);

        // No SPDY :( https://github.com/square/okhttp/issues/184
        // tried URL.setURLStreamHandlerFactory(new OkHttpClient()); but not working atm
//        ArrayList< String > list = new ArrayList< String >();
//        list.add("http/1.1");
       //  client.setSslSocketFactory();
        //  client.setTransports(list);
    }

    public OkUrlFactory getClient() {
        return   client;
    }

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
//        return new OkUrlFactory(client).open(url);
        OkUrlFactory client = new OkUrlFactory(new OkHttpClient());
        return client.open(url);
    }
}