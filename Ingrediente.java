public class Ingrediente {
    private String nome;
    private String quantidade;

    public Ingrediente(String nome, String quantidade) 
    {
        //encapsulamento para garantir que nao serao alterados diretamentes
        this.nome = nome;
        this.quantidade = quantidade;
    }
    //Getters para ler os dados privados
    public String getNome() { return nome; }
    public String getQuantidade() { return quantidade; }

    @Override //garante que está sobrescrevendo um método e não criando um novo
    public String toString() {
        return nome + " (" + quantidade + ")";
    }
}