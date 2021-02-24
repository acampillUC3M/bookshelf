package com.example.bookshelf;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ComponentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import Entidades.Libro;
import Entidades.Perfil;

/**
 * Clase para subir a la base de datos un elemento Libro
 */
public class SubirLibro extends AppCompatActivity {


    ArrayList<Libro> listalibros;
    private DatabaseReference db2;
    private Button BtnSubirLibro;
    private StorageReference Storage;


    private static final int GALLERY_INTENT = 1;
    Button btnSubirFoto;
    EditText tituloLibro;
    EditText autorLibro;
    EditText ISBNLibro;
    TextView direccionPortada;
    int libroimagen;


    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_libro);
        Storage = FirebaseStorage.getInstance().getReference();
        db2 = FirebaseDatabase.getInstance().getReference();

        tituloLibro = (EditText) findViewById(R.id.tituLibro);
        autorLibro = (EditText) findViewById(R.id.autoLibro);
        ISBNLibro = (EditText) findViewById(R.id.codiLibro);
        direccionPortada = (TextView) findViewById(R.id.dirPortada);
        btnSubirFoto = (Button) findViewById(R.id.subirFoto);
        BtnSubirLibro = (Button) findViewById(R.id.botonSubir);

        btnSubirFoto.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
            }
        });


        BtnSubirLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subirLibro();
            }
        });
        }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        final String tl = tituloLibro.getText().toString();
        if(TextUtils.isEmpty(tl)==false) {
            if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
                final Uri uri = data.getData();

                StorageReference filePath = Storage.child("portadas").child(tl);

                filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        direccionPortada.setText(uri.toString());
                        Toast.makeText(SubirLibro.this, "La foto se ha subido con éxito", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }else{
            Toast.makeText(this, "Te falta el titulo del libro", Toast.LENGTH_LONG).show();
        }
    }

    /**
     *
     */
    public void subirLibro(){

            String tl = tituloLibro.getText().toString();
            String al = autorLibro.getText().toString();
            String cl = ISBNLibro.getText().toString();
            String portadaSubida = direccionPortada.getText().toString();


            if(TextUtils.isEmpty(tl)) {

                Toast.makeText(this, "Te falta el titulo del libro", Toast.LENGTH_LONG).show();
            }else if (TextUtils.isEmpty(al)) {
                Toast.makeText(this, "Te falta el autor del libro", Toast.LENGTH_LONG).show();

            }else if (TextUtils.isEmpty(cl)) {
                Toast.makeText(this, "Te falta el código ISBN del Libro", Toast.LENGTH_LONG).show();

            }else if (TextUtils.isEmpty(portadaSubida)) {
                Toast.makeText(this, "Te falta la imagen del Libro", Toast.LENGTH_LONG).show();

            }else{
                Libro l = new Libro(tl, al, cl, null);
                db2.child("Perfil").child(Login.userG).child("Libro").child(tl).setValue(l);
                //db2.child("Perfil").child(Login.userG).child("Libro").child(tl).child(portadaSubida);
                Toast.makeText(this, "Tu libro se ha subido correctamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SubirLibro.this, Librerias.class);
                startActivity(intent);
            }

        }


}


