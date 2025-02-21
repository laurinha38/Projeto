import java.util.*;
public class Biblioteca {
    Scanner leitura = new Scanner(System.in);
    private ArrayList<Usuario> usuarios = new ArrayList<Usuario>();
    private ArrayList<Livro> livros = new ArrayList<Livro>();
    private ArrayList<Livro> emprestimos = new ArrayList<Livro>();
    
    private Usuario usuario;
    private Livro livro;
    
    private int resp;

    public Scanner getLeitura() {
        return leitura;
    }

    public void setLeitura(Scanner leitura) {
        this.leitura = leitura;
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public ArrayList<Livro> getLivros() {
        return livros;
    }

    public void setLivros(ArrayList<Livro> livros) {
        this.livros = livros;
    }

    public ArrayList<Livro> getEmprestimos() {
        return emprestimos;
    }

    public void setEmprestimos(ArrayList<Livro> emprestimos) {
        this.emprestimos = emprestimos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public int getResp() {
        return resp;
    }

    public void setResp(int resp) {
        this.resp = resp;
    }
}