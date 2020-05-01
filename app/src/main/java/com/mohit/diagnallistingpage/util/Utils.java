package com.mohit.diagnallistingpage.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.google.gson.Gson;
import com.mohit.diagnallistingpage.R;
import com.mohit.diagnallistingpage.data.remote.response.page.PageListResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class Utils {
    private static final String TAG = "Utils";

    /**
     * This method used to load the page list data
     *
     * @param context pass the application context
     * @param page    pass the count of page to be shown
     * @return return the page list data
     */
    public static PageListResponse loadInfiniteFeeds(Context context, int page) {
        Gson gson = new Gson();
        PageListResponse pageListResponse = gson.fromJson(loadJSONFromAsset(context, "CONTENTLISTINGPAGE-PAGE" + page + ".json"), PageListResponse.class);
        return pageListResponse;
    }

    /**
     * This method used to load the json data from Asset folder
     *
     * @param context      pass the application context
     * @param jsonFileName pass the json file Name
     * @return return the json daata
     */
    private static String loadJSONFromAsset(Context context, String jsonFileName) {
        String json = null;
        InputStream is = null;
        try {
            AssetManager manager = context.getAssets();
            Log.d(TAG, "path " + jsonFileName);
            is = manager.open(jsonFileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public static String removeExtension(String s) {

        String separator = System.getProperty("file.separator");
        String filename;

        // Remove the path upto the filename.
        int lastSeparatorIndex = s.lastIndexOf(separator);
        if (lastSeparatorIndex == -1) {
            filename = s;
        } else {
            filename = s.substring(lastSeparatorIndex + 1);
        }

        // Remove the extension.
        int extensionIndex = filename.lastIndexOf(".");
        if (extensionIndex == -1)
            return filename;

        return filename.substring(0, extensionIndex);
    }

    public static int getColorAccent(Context context) {
        return R.color.colorYellow;
    }
}
