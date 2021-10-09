package br.com.dio.projetocadastrousuario.model;

public class Usuarios {

    private Long id;
    private String nomeUsuario;
    private String email;
    private int Telefone;

    public Long getId() {
        return id;
    }

    public static void setId(Long id) {
        this.id = id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public static void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getTelefone() {
        return Telefone;
    }

    public void setTelefone(int telefone) {
        this.Telefone = telefone;
    }
}
