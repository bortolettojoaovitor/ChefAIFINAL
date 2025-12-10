import java.util.List;
import java.util.ArrayList;

public class Receita {
    private String nome;
    private int tempoPreparo;
    private List<String> ingredientes = new ArrayList<>();
    private List<String> passos = new ArrayList<>();

    public Receita(String nome, int tempo) {
        this.nome = nome;
        this.tempoPreparo = tempo;
    }

    public void addIngrediente(String ing) {
        ingredientes.add(ing);
    }

    public void addPasso(String passo) {
        passos.add(passo);
    }
    
    public void adicionarIngrediente(String ing) {
        addIngrediente(ing);
    }
    
    public void adicionarPasso(String passo) {
        addPasso(passo);
    }
    
    //Método responsável pela apresentação dos dados.
    public void exibirDetalhes() {
        
        System.out.println("Receita: " + nome + " | Tempo: " + tempoPreparo + " min");
        System.out.println("Ingredientes:");
        
        for(String i : ingredientes) {
            System.out.println(" * " + i);
        }
        System.out.println("\nComo preparar:");
        for(String p : passos) {
            System.out.println(" > " + p);
        }
    }
    
    public void imprimir() {
        exibirDetalhes();
    }
}