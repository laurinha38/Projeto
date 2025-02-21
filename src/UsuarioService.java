import java.util.*;
public class UsuarioService {
    Scanner leitura2 = new Scanner (System.in);
    UsuarioDAO bancoUsuario = new UsuarioDAO();
    
    private Usuario usuario;
    private Biblioteca biblioteca;

    public boolean cadastrarUsuario(String nome, String cpf, String email, String login, String senha) {
        String situacao = "Ativa";
        Usuario usuarios = new Usuario(nome, cpf, email, login, senha, situacao);
        int resultado = bancoUsuario.inserirUsuario(usuarios);

        if (resultado == 1) {
            return true;
        } else {
            return false;
        }
    }
    
    public void mudarSenha(String senha, int usuarioid) {
        bancoUsuario.mudarSenha(senha, usuarioid);
    }

    public Usuario getNovoUsuario() {
        return usuario;
    }

    public void setNovoUsuario(Usuario novoUsuario) {
        this.usuario = novoUsuario;
    }
}