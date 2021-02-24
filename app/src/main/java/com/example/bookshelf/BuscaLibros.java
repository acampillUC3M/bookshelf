package com.example.bookshelf;

import android.graphics.Matrix;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Clase para buscar información para libros
 */
public class BuscaLibros extends AsyncTask<String, Void, String> {

    private TextView tituloBuscado;
    private TextView autorBuscado;
    private TextView añoBuscado;
    private TextView descripcionBuscada;

    /**
     *
     * @param tituloBuscado
     * @param autorBuscado
     * @param añoBuscado
     * @param descripcionBuscada
     */
    public BuscaLibros(TextView tituloBuscado, TextView autorBuscado, TextView añoBuscado, TextView descripcionBuscada) {
        this.tituloBuscado = tituloBuscado;
        this.autorBuscado = autorBuscado;
        this.añoBuscado = añoBuscado;
        this.descripcionBuscada = descripcionBuscada;
    }
    /**
     *Busca silenciosamente en la API
     * @param strings
     * @return
     */
    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getBootInfo(strings[0]);
    }
    /**
     *recibe y maneja la informacion JSON de la API
     * @param s
     */
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try{
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            for (int i = 0; i < itemsArray.length(); i++) {
                JSONObject libro = itemsArray.getJSONObject(i);
                String titulo = null;
                String autor = null;
                String año = null;
                String descripcion = null;
                JSONObject volumeInfo = libro.getJSONObject("volumeInfo");
                JSONObject searchInfo = libro.getJSONObject("searchInfo");

                try {
                    titulo = volumeInfo.getString("title");
                    autor = volumeInfo.getString("authors");
                    año = volumeInfo.getString("publishedDate");
                    descripcion = volumeInfo.getString("description");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //si existen titulo y autor para un libro, sobreescribe y devuelve los datos
                if (titulo != null && autor != null) {
                    tituloBuscado.setText("Título: "+titulo);
                    autorBuscado.setText("Autor/es: "+autor);
                    añoBuscado.setText("Fecha de publicación: "+año);
                    descripcionBuscada.setText(descripcion);
                    return;
                }
            }

            tituloBuscado.setText("No hay resultados");
            autorBuscado.setText("");
            añoBuscado.setText("");
            descripcionBuscada.setText("");
        } catch (Exception e){
            tituloBuscado.setText("No hay resultados");
            autorBuscado.setText("");
            e.printStackTrace();
        }
}
}