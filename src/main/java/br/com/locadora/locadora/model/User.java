package br.com.locadora.locadora.model;



import javax.persistence.*;

@Entity(name = "Customer")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @Column
    private String cpf;

    public User() {
    }

    public User(long id, String name, String cpf) {
        this.id = id;
        this.cpf = cpf;
        this.name = name;

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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
