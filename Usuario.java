import java.util.ArrayList;
import java.util.List;

public class Usuario {
    
    //// Os atributos são 'private' para impedir que outras classes alterem o nome ou lista diretamente 
    private String nome;
    private List<Ingrediente> despensa;
    
    
    //Construtor: Responsável por colocar o objeto em um estado válido
    public Usuario(String nome) {
        this.nome = nome;
        this.despensa = new ArrayList<>();
    }
    
    //Método público para manipular a lista privada de forma controlada e acesso a leitura
    public void adicionarIngrediente(Ingrediente ing) {
        this.despensa.add(ing);
    }

    public List<Ingrediente> getDespensa() {
        return this.despensa;
    }
    
    public String getNome() {
        return nome;
    }
}