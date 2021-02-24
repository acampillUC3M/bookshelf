package com.example.bookshelf;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import Entidades.Libro;

public class SubirFotoLibro extends AppCompatActivity {


    ArrayList<Libro> listalibros;
    ImageView imagenLibro;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_foto_libro);
    }


}