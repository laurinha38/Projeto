import java.sql.*;       
import javax.swing.JOptionPane;
public class UsuarioDAO {
    Connection conexao;

    public UsuarioDAO(){
        conexao = new Conexao().getConexao();
    }
       
    public int inserirUsuario(Usuario usuarios){
        try{
            boolean r = verificarLoginRep(usuarios.getLogin());
            
            if(r == false) {
                String sql = "insert into Usuario (nome, cpf, email, login, senha, situacao) values (?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = conexao.prepareStatement(sql);
                ps.setString(1, usuarios.getNome());
                ps.setString(2, usuarios.getCpf());
                ps.setString(3, usuarios.getEmail());
                ps.setString(4, usuarios.getLogin());
                ps.setString(5, usuarios.getSenha());
                ps.setString(6, usuarios.getSituacao());
                int resul = ps.executeUpdate();

                if(resul == 1) {
                    JOptionPane.showMessageDialog(null, "Usuário inserido com sucesso! ");
                    return 1;
                } else {
                    JOptionPane.showMessageDialog(null, "Não foi possível adicionar o usuário! ");
                }
                
            } else {
                JOptionPane.showMessageDialog(null, "Usuário já existente.");
            }
            
        }catch(Exception e){
            System.out.println("Erro: " + e);
        }
        return 0;
    }
    
    public boolean verificarLoginRep(String login) {
        try {
            String sql = "select count(*) id from Usuario where login = ?;";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, login);
            ResultSet r = ps.executeQuery();
            
            if(r.next()) {
                return r.getInt(1) > 0;
            }
            
        } catch(Exception e){
            System.out.println("Erro: " + e);
        }
        return false;
    }
   
    public void mudarSenha(String senha, int usuarioid) {
        try{
            String sql = "update Usuario set senha = ?  where id = ?;";
            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setString(1, senha);
            ps.setInt(2, usuarioid);
            int r = ps.executeUpdate();
            
            if(r == 1) {
                JOptionPane.showMessageDialog(null, "Senha alterada com sucesso! ");
            } else {
                JOptionPane.showMessageDialog(null, "Não foi possível alterar a senha! ");
            }                     
        }catch(Exception e){
            System.out.println("Erro: " + e);
        }
    }
}