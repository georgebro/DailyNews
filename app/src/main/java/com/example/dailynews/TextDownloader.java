package com.example.dailynews;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// Downloads a plain text from a given url:
public class TextDownloader extends AsyncTask<String, Void, String> {

    private Callbacks callbacks; // Notify to activity what happened.
    private int httpStatusCode; // Http status code.
    private String errorMessage; // Error message.

    // Constructor:
    public TextDownloader(Callbacks callbacks) {
        this.callbacks = callbacks;
    }

    // Executes before doInBackground in the UI thread:
    protected void onPreExecute() {
        callbacks.onAboutToBegin();
    }

    // Executes in the background in a different thread than the UI thread:
    protected String doInBackground(String... params) {

        HttpURLConnection connection = null;
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {

            // Take given link:
            String link = params[0];

            // Create a url:
            URL url = new URL(link);

            // Open connection:
            connection = (HttpURLConnection)url.openConnection();

            // Check for failure:
            httpStatusCode = connection.getResponseCode();
            if(httpStatusCode != HttpURLConnection.HTTP_OK) {
                errorMessage = connection.getResponseMessage(); // Can be null.
                return null;
            }

            // Create readers:
            inputStream = connection.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            // Read downloaded text:
            StringBuilder downloadedText = new StringBuilder();
            String oneLine = bufferedReader.readLine();
            while(oneLine != null) {
                downloadedText.append(oneLine);
                downloadedText.append("\n");
                oneLine = bufferedReader.readLine();
            }

            // Return result:
            return downloadedText.toString();
        }
        catch(Exception ex) {
            errorMessage = ex.getMessage(); // Can be null.
            return null;
        }
        finally { // Close readers:
            if(connection != null) connection.disconnect();
            if(bufferedReader != null) try { bufferedReader.close(); } catch (Exception ex) { ex.printStackTrace(); }
            if(inputStreamReader != null) try { inputStreamReader.close(); } catch (Exception ex) { ex.printStackTrace(); }
            if(inputStream != null) try { inputStream.close(); } catch (Exception ex) { ex.printStackTrace(); }
        }
    }

    // Executes after doInBackground in the UI thread:
    protected void onPostExecute(String downloadedText) {
        if(downloadedText != null) // Don't check errorMessage cause it can be null even if there is an error.
            callbacks.onSuccess(downloadedText);
        else
            callbacks.onError(httpStatusCode, errorMessage);
    }

    // Callback functions:
    public interface Callbacks {
        void onAboutToBegin();
        void onSuccess(String downloadedText);
        void onError(int httpStatusCode, String errorMessage);
    }
}