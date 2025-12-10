import java.util.List;

//Esta classe define O QUE deve ser feito mas nao COMO ser feito, aplicando conceitos de abstracao
public abstract class Sugestor {
    
    //obrigado qualquer classe filha a implementar sua propria lógica, com esse metodo a classe principal pode chamar chef.sugerir receitas sem saber oq esta usando
    public abstract List<Receita> sugerirReceitas(List<Ingrediente> ingredientesDisponiveis);
    
    protected String formatarIngredientes(List<Ingrediente> ingredientes) {
        StringBuilder sb = new StringBuilder();
        for (Ingrediente i : ingredientes) {
            sb.append(i.getNome()).append(", ");
        }
        return sb.toString();
    }
}