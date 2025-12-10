import java.util.Scanner;
import java.util.List;

public class Principal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Bem-vindo ao ChefAI");
        
        
        System.out.print("Digite seu nome: ");
        String nomeUser = scanner.nextLine();
        Usuario usuario = new Usuario(nomeUser);
        
        System.out.println("\nOlá, " + usuario.getNome() + "! Vamos cadastrar os ingredientes");
        
        // Loop de cadastro
        while (true) {
            System.out.print("Nome do ingrediente ou 'fim' para terminar: ");
            String nome = scanner.nextLine();
            
            if (nome.equalsIgnoreCase("fim")) break;
            
            System.out.print("Quantidade: ");
            String qtd = scanner.nextLine();
            
            usuario.adicionarIngrediente(new Ingrediente(nome, qtd));
        }
        
        if (usuario.getDespensa().isEmpty()) {
            System.out.println("Nenhum ingrediente informado.");
            return;
        }

        // A classe 'SugestorChefIA' herda de 'Sugestor' para possuir todos os métodos definidos na classe pai
        Sugestor chef = new SugestorChefIA();
        
        System.out.println("\nConsultando,aguarde");
        
        // poliformismo para garantir que o código funcione com qualquer tipo de sugestor, não apenas com a IA e nao utilize métodos de IA
        List<Receita> sugestoes = chef.sugerirReceitas(usuario.getDespensa());
        
        System.out.println("\n Receitas Encontradas para " + usuario.getNome());
        
        if (sugestoes.isEmpty()) {
            System.out.println("Não foi possível gerar as receitas. Verifique sua conexão ou tente novamente.");
        } else {
            int contador = 1;
            for (Receita r : sugestoes) {
                System.out.println("\n OPÇÃO " + contador);
                r.exibirDetalhes();
                contador++;
            }
        }
        
        scanner.close();
    }
}