package com.maniakapps.antar.firebases;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DietasAdmin extends AppCompatActivity {
    private static final String TAG = "ActividadAdmin";
    @BindView(R.id.edtTitulo)
    EditText edtTitulo;
    @BindView(R.id.edtTexto)
    EditText edtTexto;
    @BindView(R.id.btnEnviar)
    Button btnEnviar;
    @BindView(R.id.btnDietas)
    Button btnDietas;
    private DatabaseReference mDatabase;
    private String titulo;
    private String texto;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietas_admin);
        ButterKnife.bind(this);
        mDatabase = FirebaseDatabase.getInstance().getReference("dietas");

    }

    @OnClick(R.id.btnEnviar)
    public void onClick() {
        try {
            titulo = edtTitulo.getText().toString().trim();
            texto = edtTexto.getText().toString().trim();
            writeNewUser(titulo, texto);
            mostrarToast("Se ha enviado a la base!");
        } catch (Exception e) {
            mostrarToast("No se pudo guardar la dieta");
        }

    }



    private void writeNewUser(String titulo, String texto) {
        String userId = mDatabase.push().getKey();
        Dietas user = new Dietas(titulo, texto);
        assert userId != null;
        mDatabase.child(userId).setValue(user);


    }


    private void mostrarToast(String mensaje) {
        Toast.makeText(getApplicationContext(), mensaje, Toast.LENGTH_LONG).show();
    }


    @OnClick(R.id.btnDietas)
    public void onViewClicked() {
        startActivity(new Intent(this,DietasUsuario.class));
        finish();
    }
}
