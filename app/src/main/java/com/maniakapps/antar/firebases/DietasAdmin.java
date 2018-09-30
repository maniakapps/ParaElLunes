package com.maniakapps.antar.firebases;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

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
    @BindView(R.id.listViewUser)
    ListView listViewUser;
    private ArrayList<String> list;
    private ArrayList<String> listTextos;
    ArrayAdapter<String> adapter;
    String options;
    private DatabaseReference mDatabase;
    private String titulo;
    private String texto;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dietas_admin);
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        ButterKnife.bind(this);
        mDatabase = FirebaseDatabase.getInstance().getReference("dietas");
        DietasUsuario ds = new DietasUsuario();
        getDatas();

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
    public void getDatas() {
        FirebaseDatabase database;
        DatabaseReference ref;
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("dietas");
        list = new ArrayList<>();
        listTextos = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listViewUser.setAdapter(adapter);
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final Dietas value = dataSnapshot.getValue(Dietas.class);
                assert value != null;
                list.add(value.getTitulo());
                listTextos.add(value.getTexto());
                adapter.notifyDataSetChanged();
                listViewUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        openDialog(listTextos.get(position));

                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        ref.addChildEventListener(childEventListener);


    }

    public void onBackPressed(){
        startActivity(new Intent(this,DietasAdmin.class));
    }
    private void displayToast(String a){
        Toast.makeText(getApplicationContext(),a,Toast.LENGTH_LONG).show();
    }

    public void openDialog(String green) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        // Set Custom Title
        TextView title = new TextView(this);
        // Title Properties
        title.setText("Informacion de la dieta");
        title.setPadding(10, 10, 10, 10);   // Set Position
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.BLACK);
        title.setTextSize(20);
        alertDialog.setCustomTitle(title);

        // Set Message
        TextView msg = new TextView(this);
        // Message Properties
        msg.setText(green);
        msg.setGravity(Gravity.CENTER_HORIZONTAL);
        msg.setTextColor(Color.BLACK);
        alertDialog.setView(msg);

        // Set Button
        // you can more buttons
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"Lo tengo!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Perform Action on Button
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"Volver", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Perform Action on Button
            }
        });

        new Dialog(getApplicationContext());
        alertDialog.show();

        // Set Properties for OK Button
        final Button okBT = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        LinearLayout.LayoutParams neutralBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        neutralBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        okBT.setPadding(50, 10, 10, 10);   // Set Position
        okBT.setTextColor(Color.BLUE);
        okBT.setLayoutParams(neutralBtnLP);

        final Button cancelBT = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams negBtnLP = (LinearLayout.LayoutParams) okBT.getLayoutParams();
        negBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        cancelBT.setTextColor(Color.RED);
        cancelBT.setLayoutParams(negBtnLP);
    }
}
