package com.example.bookshelf;

import android.net.Uri;
import android.nfc.Tag;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Clase encargada de establecer la conexión con la API
 */
public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();


    //URL para la api de libros de google
    private static final String BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
    private static final String QUERY_PARAM = "q"; //parametro para la string de busqueda
    private static final String MAX_RESULTS = "maxResults";//parametro que limita la lista buscada
    private static final String PRINT_TYPE = "printType"; //parametro para filtrar por tipo

    /**
     *
     * @param consultaString
     * @return
     */
    static String getBootInfo(String consultaString){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;


        try{

            Uri uriPedida = Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, consultaString)
                    .appendQueryParameter(MAX_RESULTS, "10")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();
            URL requestURL = new URL(uriPedida.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //Leer la respuesta usando inputStream y StringBuffer, para transformarlo a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if(inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = reader.readLine()) != null){
                buffer.append(line + "\n");
            }
            if(buffer.length()==0){
                //Esta vacío
                return null;
            }

            bookJSONString = buffer.toString();

        }catch (Exception ex){

            ex.printStackTrace();
            return null;

        }finally{
            if(urlConnection!=null){
                urlConnection.disconnect();
            }
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Log.d(LOG_TAG, bookJSONString);
            return bookJSONString;
        }

        }

    }


