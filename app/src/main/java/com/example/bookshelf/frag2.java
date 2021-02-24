package com.example.bookshelf;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.bookshelf.Adaptadores.AdaptadorLibro;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URL;
import java.util.ArrayList;

import Entidades.Libro;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frag2#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag2 extends Fragment {


    ImageView imagenFoto;
    TextView tituloLibroFav, autorLibroFav, codigoLibroFav;
    AdaptadorLibro adaptadorLibro;
    RecyclerView recyclerViewLibros;
    ArrayList<Libro> listalibros;
    private DatabaseReference db2;
    private StorageReference Storage;


    /**
     *
     */
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    /**
     *
     */
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public frag2() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frag2.
     */
    // TODO: Rename and change types and number of parameters
    public static frag2 newInstance(String param1, String param2) {
        frag2 fragment = new frag2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    /**
     *Crea la vista del fragmento actual
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_frag2, container, false);

        View view = inflater.inflate(R.layout.fragment_frag2, container, false);

        recyclerViewLibros = view.findViewById(R.id.myRecyclerView);
        listalibros = new ArrayList<>();
        //cargar la lista
        cargarLista();

        //mostrar datos
        //mostrarLista();

        FloatingActionButton btnLanzarActivity = (FloatingActionButton) view.findViewById(R.id.añadelibros);

        btnLanzarActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),SubirLibro.class);
                startActivity(intent);
            }
        });
        return view;
    }
    /**
     *
     */
    public void mostrarLista(){
        recyclerViewLibros.setLayoutManager(new LinearLayoutManager(getContext()));
        adaptadorLibro = new AdaptadorLibro(getContext(),listalibros);
        recyclerViewLibros.setAdapter(adaptadorLibro);

    }
    /**
     *Añade elementos a la base de datos y los muestra en el recyclerView
     */
    public void cargarLista(){

        db2 = FirebaseDatabase.getInstance().getReference();
        //listalibros.add(new Libro("titulo","autor","ISBN",R.drawable.lineadefuego));


        db2.child("Perfil").child(Login.userG).child("Libro").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        final String titulo = ds.child("titulo").getValue().toString();
                        final String autor = ds.child("autor").getValue().toString();
                        final String ISBN = ds.child("isbn").getValue().toString();


                        listalibros.add(new Libro(titulo, autor, ISBN, null));
                    }
                    recyclerViewLibros.setLayoutManager(new LinearLayoutManager(getContext()));
                    adaptadorLibro = new AdaptadorLibro(getContext(),listalibros);
                    recyclerViewLibros.setAdapter(adaptadorLibro);
                }
            }

            /**
             *
             * @param databaseError
             */
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
