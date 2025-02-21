import java.util.*;
import javax.swing.JOptionPane;
public class BibliotecaService {
    Scanner leitura = new Scanner(System.in);
    private ArrayList<String> lista = new ArrayList<String>();
    BibliotecaDAO bancoBiblioteca = new BibliotecaDAO();
    
    private UsuarioService novoUsuario = new UsuarioService();
    private LivroService novoLivro = new LivroService();
    private Biblioteca biblioteca = new Biblioteca();
    
    private int usuarioid = 0;
    
    public void cadastrarUsuario(String nome, String cpf, String email, String login, String senha) {
        novoUsuario.cadastrarUsuario(nome, cpf, email, login, senha);
    }
    
    public int efetuarLogin(String login, String senha) {
        usuarioid = bancoBiblioteca.verificarLoginUser(login, senha);
        if(usuarioid > 0) {
            return usuarioid;
        } else {
            JOptionPane.showMessageDialog(null, "Login ou senha incorretos!");
            return usuarioid;
        }
    }
    
    public void desativarConta(String login, int usuarioid) {
        bancoBiblioteca.desativarConta(login, usuarioid);
    }
 }