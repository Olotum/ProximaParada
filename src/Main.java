import Models.*;
// Importa todas as classes do pacote Models

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
// Gson para converter objetos Java <-> JSON

import java.io.FileWriter;
// Escreve arquivos

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
// Classes para requisições HTTP

import java.time.LocalDate;
// Trabalha com datas

import java.util.List;
import java.util.Scanner;
// Scanner lê entradas do usuário

public class Main {

    public static void main(String[] args) {

        // Scanner para entrada pelo terminal
        Scanner sc = new Scanner(System.in);

        // Configuração do Gson
        Gson gson = new GsonBuilder()

                // Ensina o Gson a serializar LocalDate
                .registerTypeAdapter(
                        LocalDate.class,
                        (com.google.gson.JsonSerializer<LocalDate>)
                                (src, typeOfSrc, context) ->
                                        new com.google.gson.JsonPrimitive(
                                                src.format(
                                                        java.time.format.DateTimeFormatter.ISO_LOCAL_DATE
                                                )
                                        )
                )

                // Ensina o Gson a desserializar LocalDate
                .registerTypeAdapter(
                        LocalDate.class,
                        (com.google.gson.JsonDeserializer<LocalDate>)
                                (json, typeOfT, context) ->
                                        LocalDate.parse(json.getAsString())
                )

                // Formata JSON bonitinho
                .setPrettyPrinting()
                .create();

        // Variáveis iniciais
        String busca = "";
        int dias = 0;

        // Loop principal do programa
        while (!busca.equalsIgnoreCase("sair")) {

            System.out.println("Para onde pretende viajar? (ou digite 'sair')");
            busca = sc.nextLine();

            // Encerra o programa
            if (busca.equalsIgnoreCase("sair")) {
                break;
            }

            // Pergunta quantos dias no futuro
            System.out.println("Daqui quantos dias? (max 5)");
            dias = sc.nextInt();
            sc.nextLine(); // limpa buffer

            // Calcula data da viagem
            LocalDate dia = LocalDate.now().plusDays(dias);

            // Pergunta valor em reais
            System.out.println("Quanto dinheiro em reais está levando?");
            double valorEmReais = sc.nextDouble();
            sc.nextLine();

            PrevisaoDeClima previsaoDeClima = null;

            try {
                // Monta URL da API de países
                String endereco =
                        "https://api.restcountries.com/countries/v5?q="
                                + busca;

                // Cria cliente HTTP
                HttpClient client = HttpClient.newBuilder()
                        .followRedirects(HttpClient.Redirect.NORMAL)
                        .build();

                // Cria requisição HTTP GET
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(endereco))
                        .header("Accept", "application/json")
                        .header(
                                "Authorization",
                                "Bearer rc_live_ea0884d118c44ac68e16bbb9f9b04aa6"
                        )
                        .GET()
                        .build();

                // Envia requisição
                HttpResponse<String> response =
                        client.send(
                                request,
                                HttpResponse.BodyHandlers.ofString()
                        );

                String json = response.body();

                // Verifica sucesso
                if (response.statusCode() != 200) {
                    System.out.println(
                            "⚠️ Erro na API de Países. Status: "
                                    + response.statusCode()
                    );
                    continue;
                }

                // Converte JSON para objeto Java
                RespostaRestCountry resposta =
                        gson.fromJson(
                                json,
                                RespostaRestCountry.class
                        );

                // Verifica se há país retornado
                if (resposta != null &&
                        resposta.data() != null &&
                        !resposta.data().objects().isEmpty()) {

                    // Pega primeiro país
                    Pais pais =
                            resposta.data().objects().get(0);

                    // Se houver capital
                    if (pais.capitals() != null &&
                            !pais.capitals().isEmpty()) {

                        try {
                            // Monta URL da API de clima usando latitude/longitude da capital
                            String urlClima =
                                    "https://api.openweathermap.org/data/2.5/forecast?lat="
                                            + pais.capitals().getFirst().coordinates().lat()
                                            + "&lon="
                                            + pais.capitals().getFirst().coordinates().lng()
                                            + "&appid=2af7c8f6a9ed4c16d9be22dd31f34b1b"
                                            + "&units=metric";

                            HttpRequest requestClima =
                                    HttpRequest.newBuilder()
                                            .uri(URI.create(urlClima))
                                            .build();

                            HttpResponse<String> responseClima =
                                    client.send(
                                            requestClima,
                                            HttpResponse.BodyHandlers.ofString()
                                    );

                            if (responseClima.statusCode() == 200) {

                                // Converte JSON de clima
                                RespostaClima respostaClima =
                                        gson.fromJson(
                                                responseClima.body(),
                                                RespostaClima.class
                                        );

                                // Extrai lista de climas
                                List<Clima> climas =
                                        respostaClima.list();

                                // Processa clima do dia escolhido
                                previsaoDeClima =
                                        new PrevisaoDeClima(
                                                climas,
                                                dia
                                        );

                            } else {
                                System.out.println(
                                        "⚠️ Não foi possível obter dados meteorológicos."
                                );
                            }

                        } catch (Exception e) {
                            System.out.println(
                                    "⚠️ Erro ao buscar dados de clima."
                            );
                        }
                    }

                    // Cria objeto final da viagem
                    Viagem viagem = new Viagem(
                            busca,
                            valorEmReais,
                            dia,
                            pais,
                            previsaoDeClima
                    );

                    // Mostra resultado
                    System.out.println(viagem);

                    // Gera arquivo JSON
                    String nomeArquivo =
                            "ViagemPara"
                                    + viagem.getDestino()
                                    .replace(" ", "")
                                    + ".json";

                    FileWriter escrita =
                            new FileWriter(nomeArquivo);

                    escrita.write(gson.toJson(viagem));
                    escrita.close();

                    System.out.println(
                            "✅ Arquivo " + nomeArquivo + " gerado com sucesso!"
                    );
                }

            } catch (Exception e) {
                System.out.println(
                        "⚠️ Erro inesperado ao buscar dados do país."
                );
                e.printStackTrace();
            }
        }

        // Fecha scanner
        sc.close();
    }
}