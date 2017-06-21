package com.example.abhi270595.footballmanager.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {
    final static String SERVICE_BASE_URL =
            "https://jsonplaceholder.typicode.com/";
final static String postsEntity = "posts";
    final static String element = "1";
    final static String userEntity = "users";
    final static String commentsEntity = "comments";



    public static URL buildUrl() {
        Uri builtUri = Uri.parse(SERVICE_BASE_URL)
                .buildUpon()
                .appendPath(postsEntity)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildAuthenticationURL() {
        Uri builtUri = Uri.parse(SERVICE_BASE_URL)
                .buildUpon()
                .appendPath(userEntity)
                .appendPath(element)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildStandingsURL() {
        Uri builtUri = Uri.parse(SERVICE_BASE_URL)
                .buildUpon()
                .appendPath(userEntity)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL buildArchiveURL() {
        Uri builtUri = Uri.parse(SERVICE_BASE_URL)
                .buildUpon()
                .appendPath(commentsEntity)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
