import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.ArrayList;

//Estende a classe abstrata 'Sugestor', herdando seus métodos e obrigacoes
public class SugestorChefIA extends Sugestor {

    //poliformismo sobreescrito: Implementamos o método abstrato definido na classe pai
    @Override
    public List<Receita> sugerirReceitas(List<Ingrediente> ingredientes) {
        List<Receita> sugestoes = new ArrayList<>();
        String listaIngredientes = formatarIngredientes(ingredientes);
        
        try {
            
            //Conexão e Busca
            String respostaAPI = consultarLLM(listaIngredientes);
            //processamento
            sugestoes = processarResposta(respostaAPI);
        } catch (Exception e) {
            System.out.println("ERRO ao consultar IA: " + e.getMessage());
        }
        
        return sugestoes;
    }
    
    
    //Método responsável pela comunicação HTTP com a API externa
    private String consultarLLM(String ingredientes) throws Exception {
        
        // Busca as credenciais na classe de Configuração(encapsulamento)
        String apiKey = Configuracao.getApiKey().trim();
        String url = Configuracao.getApiUrl().trim();
        String model = Configuracao.getModel().trim();
        
        // Prompt ajustado para 3 receitas e separador |||
        String prompt = "Sou um cozinheiro restrito. Tenho APENAS estes ingredientes: " + ingredientes + ". " +
            "Gere 3 sugestões de receitas simples e rápidas (max 30 min) usando PRIORITARIAMENTE esses ingredientes. " +
            "Pode usar sal, água e óleo básico. " +
            "DE DETALHES SOBRE O MODO DE PREPARO." +
            "FORMATO DE RESPOSTA OBRIGATÓRIO: " +
            "Separe uma receita da outra usando '|||'. " +
            "Dentro da receita, separe os dados por ponto-e-vírgula. " +
            "Exemplo: Nome Receita;Tempo;Ing1, Ing2;Passo 1. Passo 2. ||| Nome Receita 2;20;Ing3;Passos...";

        String promptSeguro = limparTextoJSON(prompt);

        String jsonBody = "{"
                + "\"model\": \"" + model + "\","
                + "\"messages\": [{\"role\": \"user\", \"content\": \"" + promptSeguro + "\"}],"
                + "\"temperature\": 0.4" 
                + "}";
        
        // Padrão Builder para criar a requisição HTTP
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + apiKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        // Validação do código de status HTTP
        if (response.statusCode() != 200) {
            throw new RuntimeException("Falha na API: " + response.statusCode());
        }
        
        return response.body();
    }
    
    // Método auxiliar para escapar caracteres especiais e evitar JSON inválido
    private String limparTextoJSON(String texto) {
        if (texto == null) return "";
        return texto.replace("\"", "\\\"").replace("\n", " ").replace("\r", "");
    }
    //Transforma a String bruta (JSON) retornada pela IA em Objetos Java (Receita)/ PARSING MANUAL
    // Demonstra lógica de programação ao manipular Strings sem usar bibliotecas
    private List<Receita> processarResposta(String jsonResponse) {
        List<Receita> receitas = new ArrayList<>();
        
        try {
            
            int chaveIndex = jsonResponse.indexOf("\"content\"");
            if (chaveIndex == -1) return receitas;

            int doisPontosIndex = jsonResponse.indexOf(":", chaveIndex);
            int inicioValor = jsonResponse.indexOf("\"", doisPontosIndex + 1);
            if (inicioValor == -1) return receitas;

            String resto = jsonResponse.substring(inicioValor + 1);
            String content = resto.split("\"")[0];
            
            content = content.replace("\\n", " ").replace("\\r", " ").replace("\\", "").trim();
            
            String[] receitasBrutas = content.split("\\|\\|\\|");
            
            for (String recTexto : receitasBrutas) {
                if (recTexto.trim().isEmpty()) continue;
                
                String[] partes = recTexto.trim().split(";");
                
                if (partes.length >= 4) {
                    String nome = partes[0].trim();
                    int tempo = 0;
                    try {
                        tempo = Integer.parseInt(partes[1].replaceAll("[^0-9]", ""));
                    } catch (Exception e) {}
                    
                    Receita r = new Receita(nome, tempo);
                    
                    String[] ings = partes[2].split(",");
                    for(String i : ings) r.addIngrediente(i.trim());
                    
                    r.addPasso(partes[3].trim()); 
                    
                    receitas.add(r);
                }
            }
            
        } catch (Exception e) {
            System.out.println("Erro ao processar resposta da IA: " + e.getMessage());
        }
        
        return receitas;
    }
}