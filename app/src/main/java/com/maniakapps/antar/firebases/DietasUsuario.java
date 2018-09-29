package com.maniakapps.antar.firebases;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DietasUsuario extends AppCompatActivity {
    @BindView(R.id.listViewUser)
    ListView listViewUser;
    private ArrayList<String> list;
    private ArrayList<String> listTextos;
    ArrayAdapter<String> adapter;
    String options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);
        ButterKnife.bind(this);
        getDatas();

    }

    private void getDatas() {
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
                        displayToast("Click ListItem Number " + position);
                        displayToast(listTextos.get(position));

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

}
