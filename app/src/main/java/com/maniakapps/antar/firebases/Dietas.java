package com.maniakapps.antar.firebases;


import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class Dietas {
    private String titulo;
    private String texto;

        Dietas() {
            // Default constructor required for calls to DataSnapshot.getValue(Dietas.class)
        }

        Dietas(String titulo, String texto) {
            this.titulo = titulo;
            this.texto = texto;
        }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }
}


