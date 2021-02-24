package com.example.bookshelf;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link frag4#newInstance} factory method to
 * create an instance of this fragment.
 */
public class frag4 extends Fragment {

    private static String TAG = frag4.class.getSimpleName();
    private EditText busquedaLibro;
    private TextView tituloBuscado, autorBuscado, añoBuscado,descripcionBuscada;


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
    public frag4() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment frag4.
     */
    // TODO: Rename and change types and number of parameters
    public static frag4 newInstance(String param1, String param2) {
        frag4 fragment = new frag4();
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
        //return inflater.inflate(R.layout.fragment_frag4, container, false);

        final View view = inflater.inflate(R.layout.fragment_frag4, container, false);

        busquedaLibro = (EditText) view.findViewById(R.id.busquedaLibro);
        tituloBuscado = (TextView) view.findViewById(R.id.tituloLibroBuscado);
        autorBuscado = (TextView)  view.findViewById(R.id.autorLibroBuscado);
        añoBuscado = (TextView) view.findViewById(R.id.añoLibroBuscado);
        descripcionBuscada = (TextView) view.findViewById(R.id.descripcionLibroBuscado);

        Button botonBuscarLibro = (Button) view.findViewById(R.id.bottonBuscarLibros);

        botonBuscarLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscarLibros(v);
            }
        });
        return view;
    }

    /**
     *Llama a la funcion de buscar libros al pulsar el boton de búsqueda
     * @param view
     */
    public void buscarLibros(View view) {

        String consulta = busquedaLibro.getText().toString();
        Log.i(TAG, "Buscados: " + consulta);
        if(consulta.length()!=0){
            new BuscaLibros(tituloBuscado,autorBuscado,añoBuscado,descripcionBuscada).execute(consulta);
        }else{
            Toast.makeText(getActivity(),"Por favor busca un libro antes de tocar el botoncito", Toast.LENGTH_SHORT).show();
        }
    }
}