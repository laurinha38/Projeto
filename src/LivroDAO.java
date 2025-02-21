import java.sql.*;       
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;

public class LivroDAO {
    Connection conexao;

    public LivroDAO(){
        conexao = new Conexao().getConexao();        
    }
       
    public int inserirLivro(Livro livros){
        try{
            boolean r = verificarTitulo(livros.getTitulo());
            
            if(r == false) {
                String sql = "insert into Livro (autor, genero, titulo, faixaEtaria, quantidade) values (?, ?, ?, ?, ?)";
                PreparedStatement ps = conexao.prepareStatement(sql);
                
                ps.setString(1, livros.getAutor());
                ps.setString(2, livros.getGenero());
                ps.setString(3, livros.getTitulo());
                ps.setString(4, livros.getFaixaEtaria());
                ps.setInt(5, livros.getQuantidade());
                ps.executeUpdate();

                JOptionPane.showMessageDialog(null, "Livro inserido com sucesso");
                return 1;
            } else {
                String sql2 = "select quantidade from Livro where titulo = ?;";
                PreparedStatement ps2 = conexao.prepareStatement(sql2);
                
                ps2.setString(1, livros.getTitulo());
                ResultSet rs = ps2.executeQuery();
            
                if(rs.next()) {
                    int quant = rs.getInt("quantidade");
                    int quantFinal = quant + livros.getQuantidade();
                    
                    String sql3 = "update Livro set quantidade = ?  where titulo = ?;";
                    PreparedStatement ps3 = conexao.prepareStatement(sql3);
                    
                    ps3.setInt(1, quantFinal);
                    ps3.setString(2, livros.getTitulo());
                    int re = ps3.executeUpdate();
                    
                    if(re == 1) {
                        JOptionPane.showMessageDialog(null, "Livro inserido com sucesso");
                        return 1;
                    } else if (re > 1 || re < 1) {
                        JOptionPane.showMessageDialog(null, "Não foi possivel inserir o livro! ");
                    }
                }
            }          
        }catch(Exception e){
          System.out.println("Erro: " + e);
        }
        return 0;
    }
    
    public boolean verificarTitulo(String titulo) {
        try {
            String sql = "select count(*) id from Livro where titulo = ?;";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setString(1, titulo);
            ResultSet r = ps.executeQuery();
            
            if(r.next()) {
                return r.getInt(1) > 0;
            }
            
        } catch(Exception e){
            System.out.println("Erro: " + e);
        }
        return false;
    }
    
    public void emprestarExemplar(String titulo, int quantLivros, String dataOficial, int usuarioid) {
      int quant = 0;
        try {
            String sql = "select quantidade from Livro where titulo = ?;";
            PreparedStatement ps = conexao.prepareStatement(sql);
            
            ps.setString(1, titulo);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                quant = rs.getInt("quantidade");
                
                if(quant > quantLivros) {
                    quant = quant - quantLivros;
                
                    Calendar dataAtual = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    dataAtual.add(Calendar.DAY_OF_MONTH, 7);
                    Date dt = dataAtual.getTime();
                    String dataDev = df.format(dt);
                    
                    atualizarQuatEmpr(titulo, quant, quantLivros, dataOficial, dataDev, usuarioid);
                    JOptionPane.showMessageDialog(null, "A data de devolução é: " + dataDev);
                }
                else {
                    JOptionPane.showMessageDialog(null, "A quantidade desejada execede a disponivel! ");
                }
            }
        
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
    }
    
    public void atualizarQuatEmpr(String titulo, int quant, int quantLivros, String dataOficial, String dataDev, int usuarioid){
        try {
            String sql = "update Livro set quantidade = ? where titulo = ?;";
            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setInt(1, quant);
            ps.setString(2, titulo);
            int r = ps.executeUpdate();

            if(r == 1) {
                livrosEmprestados(titulo, quantLivros, dataOficial, dataDev, usuarioid);
                JOptionPane.showMessageDialog(null, "Empréstimo realizado com sucesso! ");
                
                Calendar dataAtual = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date dt = dataAtual.getTime();
                String dataA = df.format(dt);
                
                calcularDataPassada(dataA, usuarioid);                
            } else if (r > 1 || r < 1) {
                JOptionPane.showMessageDialog(null, "Não foi possivel realizar o emprestimo!");
            }
        
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
    }
    
    public void calcularDataPassada(String dataA, int usuarioid) throws SQLException {
        Calendar dataAtual = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        dataAtual.add(Calendar.DAY_OF_MONTH, -30);
        Date dt = dataAtual.getTime();
        String dataPassada = df.format(dt);
        
        String sql = "select count(*) id_UsuarioFK from livrosEmprestimos where dataEmprestimo <= ? and dataEmprestimo > ? and id_UsuarioFK = ?;";
        PreparedStatement ps = conexao.prepareStatement(sql);
                
        ps.setString(1, dataA);
        ps.setString(2, dataPassada);
        ps.setInt(3, usuarioid);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            int count = rs.getInt(1);
            JOptionPane.showMessageDialog(null, "Você fez " + count + " empréstimos no último mês!");
        }
    }
    
    public void livrosEmprestados(String tituloEmpr, int quant, String dataOficial, String dataDev, int usuarioid) {
        try{
            boolean res = verificarTituloeID(tituloEmpr, usuarioid);
            
            if(res == false) {
                String sql = "insert into LivrosEmprestimos (titulo, quantidade, dataEmprestimo, dataEstimadaDev, id_UsuarioFK) values ( ?, ?, ?, ?, ?)";
                PreparedStatement ps = conexao.prepareStatement(sql);

                ps.setString(1, tituloEmpr);
                ps.setInt(2, quant);
                ps.setString(3, dataOficial);
                ps.setString(4, dataDev);
                ps.setInt(5, usuarioid);
                int r = ps.executeUpdate();

                if (r == 1) {
                    System.out.println("\nLivro inserido com sucesso! ");
                } else {
                    System.out.println("\nNão foi possivel inserir o livro!");
                }
            } else {
                String sql2 = "select quantidade from LivrosEmprestimos where titulo = ? and id_UsuarioFK = ?;";
                PreparedStatement ps2 = conexao.prepareStatement(sql2);
                
                ps2.setString(1, tituloEmpr);
                ps2.setInt(2, usuarioid);
                ResultSet rs = ps2.executeQuery();
                
                if(rs.next()) {
                    int quantidade = rs.getInt("quantidade");
                    int quantFinal = quantidade + quant;
                    
                    String sql3 = "update LivrosEmprestimos set quantidade = ? where titulo = ? and id_UsuarioFK = ?;";
                    PreparedStatement ps3 = conexao.prepareStatement(sql3);
                    
                    ps3.setInt(1, quantFinal);
                    ps3.setString(2, tituloEmpr);
                    ps3.setInt(3, usuarioid);
                    int re = ps3.executeUpdate();
                    
                    if(re == 1) {
                        System.out.println("Livro inserido com sucesso");
                    } else {
                        System.out.println("Não foi possivel inserir o livro! ");
                    }
                }
            }
                           
        }catch(Exception e){
            System.out.println("Erro: " + e);
        }
    }
    
    public boolean verificarTituloeID(String titulo, int usuarioid) {
        try {
            String sql = "select count(*) from LivrosEmprestimos where id_UsuarioFK = ? and titulo = ?;";
            PreparedStatement ps = conexao.prepareStatement(sql);
            ps.setInt(1, usuarioid);
            ps.setString(2, titulo);
            ResultSet r = ps.executeQuery();
            
            if(r.next()) {
                return r.getInt(1) > 0;
            }
            
        } catch(Exception e){
            System.out.println("Erro: " + e);
        }
        return false;
    }
    
    public ArrayList<String> controlarHistorico(int usuarioid) {
        ArrayList<String> lista = new ArrayList<String>();
        try{
            String sql = "select titulo, quantidade, dataEmprestimo, dataDev, id_UsuarioFK from LivrosEmprestimos where id_UsuarioFK = ?";
            PreparedStatement ps = conexao.prepareStatement(sql);
                   
            ps.setInt(1, usuarioid);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                String data = rs.getString("dataDev");
                
                if(data == null) {
                    String l = rs.getString("titulo") + (" | ") + rs.getInt("quantidade") + (" | ") + rs.getString("dataEmprestimo") + (" | ") + rs.getInt("id_UsuarioFK");
                    lista.add(l);
                } else {
                    String l = rs.getString("titulo") + (" | ") + rs.getInt("quantidade") + (" | ") + rs.getString("dataEmprestimo") + (" | ") + rs.getString("dataDev") + (" | ") + rs.getInt("id_UsuarioFK");
                    lista.add(l);
                }
            }
               return lista;            
        }catch(Exception e){
            System.out.println("Erro: " + e);
        }
        return lista;
    }
    
    public void devolverExemplar(String tituloDev, int quantLivros, int usuarioid) {
        try {
            String sql = "select quantidade from Livro where titulo = ?;";
            PreparedStatement ps = conexao.prepareStatement(sql);
            
            ps.setString(1, tituloDev);
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                int quant = rs.getInt("quantidade");
                quant = quant + quantLivros;
                atualizarQuatDev(tituloDev, quant, usuarioid);
            }
        
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
    }
    
    public void atualizarQuatDev(String tituloDev, int quant, int usuarioid){
        try {
            String sql2 = "update Livro set quantidade = ?  where titulo = ?;";
            PreparedStatement ps2 = conexao.prepareStatement(sql2);

            ps2.setInt(1, quant);
            ps2.setString(2, tituloDev);
            int r = ps2.executeUpdate();

            if(r == 1) {
                JOptionPane.showMessageDialog(null, "Devolução realizada com sucesso! ");
                dataDeDevolucaoExe(tituloDev, usuarioid);
            } else {
                JOptionPane.showMessageDialog(null, "Não foi possível realizar a devolução! ");
            }
        
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
    }
    
    public void dataDeDevolucaoExe(String tituloDev, int usuarioid) {
        try {
            Calendar dataAtual = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date dt = dataAtual.getTime();
            String dataDev = df.format(dt);
            
            String sql = "update LivrosEmprestimos set dataDev = ?  where titulo = ? and id_UsuarioFK = ?;";
            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setString(1, dataDev);
            ps.setString(2, tituloDev);
            ps.setInt(3, usuarioid);
            int r = ps.executeUpdate();
            
            if(r == 1) {
                JOptionPane.showMessageDialog(null, "Devolução realizada com sucesso! ");
            } else {
                JOptionPane.showMessageDialog(null, "Não foi possivel realizar a devolução! ");
            }
            
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
    }
    
    public void renovarEmprestimoLi(String tituloRen, int usuarioid) {
        try {
            Calendar dataAtual = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            dataAtual.add(Calendar.DAY_OF_MONTH, 7);
            Date dt = dataAtual.getTime();
            String dataRen = df.format(dt);
            
            String sql = "update LivrosEmprestimos set dataRen = ?  where titulo = ? and id_UsuarioFK = ?;";
            PreparedStatement ps = conexao.prepareStatement(sql);

            ps.setString(1, dataRen);
            ps.setString(2, tituloRen);
            ps.setInt(3, usuarioid);
            int r = ps.executeUpdate();
            
            if(r == 1) {
                JOptionPane.showMessageDialog(null, "Renovação realizada com sucesso! Você tem mais 7 dias para devolvê-lo.");
            } else {
                JOptionPane.showMessageDialog(null, "Não foi possivel realizar a renovação! ");
            }
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
        
    }
}