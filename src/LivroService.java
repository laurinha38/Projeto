import java.text.SimpleDateFormat;
import java.util.*;
public class LivroService {
    Scanner leitura = new Scanner (System.in);
    Scanner leitura2 = new Scanner(System.in);
    LivroDAO bancoLivro = new LivroDAO();
    private ArrayList<String> lista = new ArrayList<String>();
    
    public boolean catalogarLivro(String autor, String genero, String titulo, String faixaEtaria, String quantidadeS) {
        int quantidade = Integer.parseInt(quantidadeS);
        
        Livro livros = new Livro(autor, genero, titulo, faixaEtaria, quantidade);
        int resultado = bancoLivro.inserirLivro(livros);
        
        if (resultado == 1) {
            return true;
        } else {
            return false;
        }
    }
    
    public void emprestarExemplar(String titulo, String quantidadeS, int usuarioid) {
        int quantLivros = Integer.parseInt(quantidadeS);

        Calendar dataEmprestimo = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date dt = dataEmprestimo.getTime();
        String dataOficial = df.format(dt);
        
        bancoLivro.emprestarExemplar(titulo, quantLivros, dataOficial, usuarioid);
    }

    public ArrayList<String> controlarHistorico(int usuarioid) {
        lista = bancoLivro.controlarHistorico(usuarioid);
        return lista;
    }
    
    public void renovarEmprestimos(String tituloRen, int usuarioid) {
        bancoLivro.renovarEmprestimoLi(tituloRen, usuarioid);
    }
    
    public void devolverExemplar(String tituloDev, String quantidadeS, int usuarioid) {
       int quantLivros = Integer.parseInt(quantidadeS);
       bancoLivro.devolverExemplar(tituloDev, quantLivros, usuarioid);
    }
}