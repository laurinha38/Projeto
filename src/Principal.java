import java.util.*;
public class Principal {
    public static void main(String a[]) {
        Scanner leitura = new Scanner(System.in);
        BibliotecaService biblioteca = new BibliotecaService();

        System.out.println("Nome: ");
        String nome = leitura.nextLine();
        nome = leitura.nextLine();
        System.out.println("Cpf: ");
        String cpf = leitura.nextLine();
        System.out.println("Email: ");
        String email = leitura.nextLine();
        System.out.println("Login: ");
        String login = leitura.nextLine();
        System.out.println("Senha: ");
        String senha = leitura.nextLine();
        biblioteca.cadastrarUsuario(nome, cpf, email, login, senha);
    }
}