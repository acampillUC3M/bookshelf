package com.example.bookshelf;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frag3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag3 extends Fragment {

    private DatabaseReference db2;
    private FirebaseAuth mAuth;
    private StorageReference Storage;
    private ImageView fotoUsuario;
    private TextView usuario;
    private TextView email;
    private TextView password;
    private Button subirFoto;
    private static final int GALLERY_INTENT = 1;
    private static final int MY_INTENT_REQUEST_CODE = 1;
    /**
     *
     */
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     *
     */
    public frag3() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frag3.
     */
    // TODO: Rename and change types and number of parameters
    public static frag3 newInstance(String param1, String param2) {
        frag3 fragment = new frag3();
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
        mAuth = FirebaseAuth.getInstance();
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
        //return inflater.inflate(R.layout.fragment_frag3, container, false);
        mAuth = FirebaseAuth.getInstance();
        db2 = FirebaseDatabase.getInstance().getReference();


        View view = inflater.inflate(R.layout.fragment_frag3, container, false);
        Storage = FirebaseStorage.getInstance().getReference();
        usuario = (TextView) view.findViewById(R.id.perfilUsuario);
        email = (TextView) view.findViewById(R.id.mailUsuario);
        password = (TextView) view.findViewById(R.id.editTextTextPassword);
        fotoUsuario = (ImageView) view.findViewById(R.id.fotoPrefil);
        subirFoto = (Button) view.findViewById(R.id.subirFotoPerfil);


        subirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                //startActivityForResult(intent, MY_INTENT_REQUEST_CODE);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
            }
        });
        StorageReference storageRef = Storage.child("FotosUsuario").child(Login.userG);

        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(frag3.this).load(uri).into(fotoUsuario);
            }
        });

        db2.child("Perfil").child(Login.userG).addValueEventListener(new ValueEventListener() {
            /**
             *Muestra los datos del perfil
             * @param dataSnapshot
             */
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                        final String username = dataSnapshot.child("nombre").getValue().toString();
                        final String useremail = dataSnapshot.child("email").getValue().toString();
                        final String userpassword = dataSnapshot.child("password").getValue().toString();

                        usuario.setText(username);
                        email.setText(useremail);
                        password.setText(userpassword);
                }
            }

            /**
             *
             * @param databaseError
             */
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Ha fallado la conexión", Toast.LENGTH_SHORT).show();
            }
        });

    return view;
    }

    /**
     *Sube la imagen a nuestro perfil
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INTENT && resultCode == Activity.RESULT_OK)  {
            final Uri uri = data.getData();

            StorageReference filePath = Storage.child("FotosUsuario").child(Login.userG);

            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    //Glide.with(frag3.this).load(uri).into(fotoUsuario);
                    Toast.makeText(getContext(), "La foto se ha subido con éxito", Toast.LENGTH_SHORT).show();
                }

            });

        }
    }
}