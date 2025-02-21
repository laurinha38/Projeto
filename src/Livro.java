import java.util.*;
public class Livro {
    private int quantidade;
    private String titulo, autor, genero, faixaEtaria;

    public Livro(String autor, String genero, String titulo, String faixaEtaria, int quantidade) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
        this.faixaEtaria = faixaEtaria;
        this.quantidade = quantidade;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getGenero() {
        return genero;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getFaixaEtaria() {
        return faixaEtaria;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setFaixaEtaria(String faixaEtaria) {
        this.faixaEtaria = faixaEtaria;
    }
}