package com.fabianardila.proyectogradouts.modelos;

public class Categorias {

    private String id;
    private String title;

    public Categorias(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public Categorias() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
