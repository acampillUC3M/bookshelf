package com.example.bookshelf;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import Entidades.Perfil;

/**
 * Clase para crear un nuevo usuario
 */
public class Registro extends AppCompatActivity {

    EditText reg_Nombre;
    EditText reg_mail;
    EditText reg_password;
    EditText reg_password2;
    Button btnRegistro;
    private DatabaseReference db;


    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);



        db = FirebaseDatabase.getInstance().getReference();

        reg_Nombre = (EditText) findViewById(R.id.userReg);
        reg_mail = (EditText) findViewById(R.id.mailReg);
        reg_password = (EditText) findViewById(R.id.passReg);
        reg_password2 = (EditText) findViewById(R.id.passReg2);
        btnRegistro = (Button) findViewById(R.id.botonReg);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarRegistro();
            }
        });

    }

    /**
     *Guarda el registro en la base de datos
     */
    public void guardarRegistro(){
        final String Nombre = reg_Nombre.getText().toString();
        final String email = reg_mail.getText().toString();
        final String password = reg_password.getText().toString();
        final String password2 = reg_password2.getText().toString();
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Perfil").child(Nombre).exists()) {
                    Toast.makeText(Registro.this, "El usuario ya existe", Toast.LENGTH_SHORT).show();

                    if (!Nombre.isEmpty()) {

                        if (!email.isEmpty()) {

                            if (!password.isEmpty()) {

                                if (TextUtils.equals(password, password2)) {

                                } else {
                                    Toast.makeText(Registro.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Registro.this, "Te falta la contraseña", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(Registro.this, "Te falta el e-mail", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Registro.this, "Te falta el Nombre", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Perfil p = new Perfil(Nombre, email, password);
                    db.child("Perfil").child(Nombre).setValue(p);
                    Toast.makeText(Registro.this, "Te has registrado correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Registro.this, Login.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Registro.this, "Algo esta mal", Toast.LENGTH_SHORT).show();
            }
        });

            }

}
