package com.example.bookshelf.Adaptadores;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bookshelf.Librerias;
import com.example.bookshelf.Login;
import com.example.bookshelf.R;
import com.example.bookshelf.SubirFotoLibro;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import Entidades.Libro;

/**
 *Esta clases hereda de RecyclerView e implementa un onClickListener para las acciones en cada elemento de la lista dinamica
 */
public class AdaptadorLibro extends RecyclerView.Adapter<AdaptadorLibro.ViewHolder> implements View.OnClickListener{

    LayoutInflater inflater;
    ArrayList<Libro> model;
    private DatabaseReference db2;
    private StorageReference Storage;
    AdaptadorLibro adaptadorLibroFav;
    RecyclerView recyclerViewLibrosFav;
    private static final int GALLERY_INTENT = 1;
    private Context context;


    //listener
    /**
     *Este es el "escuchador" para los onClick que hubiera en la lista dinamica
     */
    private View.OnClickListener listener;

    public AdaptadorLibro(Context context, ArrayList<Libro> model){

        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.model = model;

    }
    /**
     *Crea la vista del arrayList en la aplicacion
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.lista_libros,parent,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }
    /**
     *
     * @param listener
     */
    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;

    }
    /**
     *Funcion encargada de devolver en cada cardView cada elemento introducido en la base de datos
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final String titulo = model.get(position).getTitulo();
        String autor = model.get(position).getAutor();
        String isbn = model.get(position).getISBN();
        Uri imagen = model.get(position).getImagen();


        Storage = FirebaseStorage.getInstance().getReference();
        StorageReference storageRef = Storage.child("portadas").child(titulo);

        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            /**
             *Cuando detecta una imagen subida a la base de datos la imprime en el ImageView de cada libro
             * @param uri
             */
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).into(holder.imagen);
            }
        });

        holder.titulos.setText(titulo);
        holder.autores.setText(autor);
        holder.isbn.setText(isbn);
        holder.imagen.setImageURI(imagen);



        holder.favoritos.setOnClickListener(this);

    }
    /**
     *Devuelve la lista de elementos en funcion de su tamano, tomando como referencia la base de datos.
     * @return
     */
    @Override
    public int getItemCount() {

        return model.size();
    }
    /**
     *Implementa las funciones onClick usadas dentro del RecyclerView
     * @param v
     */
    @Override
    public void onClick(View v) {
        if(listener!=null){
           listener.onClick(v);
        }

    }
    /**
     *Clase ViewHolder para mostrar el recyclerView con los elementos de la lista.
     */
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView titulos, autores, isbn;
        ImageView imagen;
        ArrayList<Libro> listalibrosfav;
        AdaptadorLibro adaptadorLibroFav;
        RecyclerView recyclerViewLibrosFav;


        //Botones
        ImageButton favoritos;
        Button subirFoto;

        /**
         *Declaracion de los elementos que podemos ver en cada cardView del recyclerView
         * @param itemView
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titulos = itemView.findViewById(R.id.tituloLibro);
            autores = itemView.findViewById(R.id.autorlibro);
            isbn = itemView.findViewById(R.id.codigolibro);
            imagen = itemView.findViewById(R.id.imagen_libro);
            favoritos = itemView.findViewById(R.id.añadirFav);


            favoritos.setOnTouchListener(new View.OnTouchListener() {
                /**
                 *Esta funcion hace que cambie el color del icono favoritos para cada libro
                 * @param view
                 * @param event
                 * @return
                 */
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    Boolean pulsado = favoritos.isPressed() == true;
                    Boolean noPulsado = favoritos.isPressed() == false;
                    if(event.getAction() == event.getButtonState()) {
                        favoritos.setImageResource(R.drawable.ic_baseline_favorite_24);
                        favoritos.isPressed();
                    } else if(event.getAction() == event.getButtonState()) {
                        favoritos.setImageResource(R.drawable.ic_favorite);

                    }
                    return true;
                }

            });

        }


    }

    }

