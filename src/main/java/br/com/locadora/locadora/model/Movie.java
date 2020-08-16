package br.com.locadora.locadora.model;

import javax.persistence.*;

@Entity
public class Movie {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @Column
    private int qtd;

    public Movie() {
    }

    public Movie(long id, String name, int qtd) {
        this.id = id;
        this.name = name;
        this.qtd = qtd;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQtd() {
        return qtd;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }
}
