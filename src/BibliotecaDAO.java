import java.sql.*;       
import javax.swing.JOptionPane;
public class BibliotecaDAO {
    Connection conexao;

    public BibliotecaDAO(){
        conexao = new Conexao().getConexao();
    }
    
    public int verificarLoginUser(String login, String senha) {
        String situacao = "";
        try {
            String sql = "select id, situacao from Usuario where login = ? and senha = ?;";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, senha);
            ResultSet rs = ps.executeQuery();
                  
            if(rs.next()) {
                if(rs.getInt(1) > 0) {
                    situacao = rs.getString("situacao");
                        
                    if(situacao.equalsIgnoreCase("Ativa")) {
                        return rs.getInt("id");
                    } else {
                        JOptionPane.showMessageDialog(null, "Conta inativa");
                    }
                }     
            }
            return 0;
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        return 0;
    }
    
    public void desativarConta(String login, int usuarioid) {
        String situacao = "";
        try {
            String sql = "update Usuario set situacao = 'Inativa' where login = ? and id != ?;";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, login);
            ps.setInt(2, usuarioid);
            int r = ps.executeUpdate();
            
            if(r == 1) {
                JOptionPane.showMessageDialog(null, "Conta desativada com sucesso! ");
            } else {
                JOptionPane.showMessageDialog(null, "Não foi possível dasativar a conta!");
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
    }
}