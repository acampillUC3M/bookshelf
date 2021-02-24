package com.example.bookshelf;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Clase encargada de permitir o denegar el acceso a la aplicación
 */
public class Login extends AppCompatActivity {

    private Button botonRegistrarse;
    private Button botonEntrar;
    EditText usr;
    EditText usr_password;
    private DatabaseReference db;
    public static String userG;
    public static String userM;
    public static String userP;

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = FirebaseDatabase.getInstance().getReference();

        botonEntrar= (Button) findViewById(R.id.botonLog);
        usr = (EditText) findViewById(R.id.userText);
        usr_password = (EditText) findViewById(R.id.passText);

        botonRegistrarse = (Button) findViewById(R.id.registrarse);
        botonEntrar = (Button) findViewById(R.id.botonLog) ;

        botonRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(Login.this, Registro.class);
            startActivity(intent);
            }
        });

            botonEntrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    signIn(usr.getText().toString(), usr_password.getText().toString());
                    userG = usr.getText().toString();
                    userP = usr_password.getText().toString();

                }

            });
        }

    /**
     *
     * @param username
     * @param password
     */
        private void signIn(final String username, final String password){
            db.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if (dataSnapshot.child("Perfil").child(username).exists()){
                        if (!username.isEmpty()){

                            if(dataSnapshot.child("Perfil").child(username).child("password").getValue().equals(password) ){
                                Toast.makeText(Login.this, "Has entrado con éxito", Toast.LENGTH_LONG).show();
                                Intent in = new Intent(Login.this, Librerias.class);
                                startActivity(in);
                            }
                            else{
                                Toast.makeText(Login.this, "La contraseña es incorrecta", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(Login.this, "Te falta el usuario", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Login.this, "El usuario no esta registrado", Toast.LENGTH_SHORT).show();
                    }
                }

                /**
                 *
                 * @param databaseError
                 */
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Login.this, "Algo esta mal", Toast.LENGTH_SHORT).show();
                }
            });



    }





}
