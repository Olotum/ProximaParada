import Models.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (com.google.gson.JsonSerializer<LocalDate>) (src, typeOfSrc, context) ->
                        new com.google.gson.JsonPrimitive(src.format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE)))
                .registerTypeAdapter(LocalDate.class, (com.google.gson.JsonDeserializer<LocalDate>) (json, typeOfT, context) ->
                        LocalDate.parse(json.getAsString()))
                .setPrettyPrinting()
                .create();
        String busca = "";
        int dias = 0;

        while(!busca.equalsIgnoreCase("sair")) {
            System.out.println("Para onde pretende viajar? (ou digite 'sair')");
            busca = sc.nextLine();

            if (busca.equalsIgnoreCase("sair")) {
                break;
            }

            System.out.println("Daqui quantos dias? (max 5)");
            dias = sc.nextInt();
            sc.nextLine();

            LocalDate dia = LocalDate.now().plusDays(dias);

            System.out.println("Quanto dinheiro em reais está levando?");
            double valorEmReais = sc.nextDouble();
            sc.nextLine();

            PrevisaoDeClima previsaoDeClima = null;

            try {
                String endereco = "https://api.restcountries.com/countries/v5?q=" + busca;

                HttpClient client = HttpClient.newBuilder()
                        .followRedirects(HttpClient.Redirect.NORMAL)
                        .build();

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(endereco))
                        .header("Accept", "application/json")
                        .header("Authorization", "Bearer rc_live_ea0884d118c44ac68e16bbb9f9b04aa6")
                        .GET()
                        .build();

                HttpResponse<String> response =
                        client.send(request, HttpResponse.BodyHandlers.ofString());

                String json = response.body();

                if (response.statusCode() != 200) {
                    System.out.println("⚠️ Erro na API de Países. Status: " + response.statusCode());
                    continue;
                }

                RespostaRestCountry resposta = gson.fromJson(json, RespostaRestCountry.class);

                if (resposta != null && resposta.data() != null && !resposta.data().objects().isEmpty()) {
                    Pais pais = resposta.data().objects().get(0);

                    if (pais.capitals() != null && !pais.capitals().isEmpty()) {
                        try {
                            String urlClima = "https://api.openweathermap.org/data/2.5/forecast?lat="
                                    + pais.capitals().getFirst().coordinates().lat()
                                    + "&lon=" + pais.capitals().getFirst().coordinates().lng()
                                    + "&appid=2af7c8f6a9ed4c16d9be22dd31f34b1b&units=metric";

                            HttpRequest requestClima = HttpRequest.newBuilder()
                                    .uri(URI.create(urlClima))
                                    .build();

                            HttpResponse<String> responseClima =
                                    client.send(requestClima, HttpResponse.BodyHandlers.ofString());

                            if (responseClima.statusCode() == 200) {
                                RespostaClima respostaClima = gson.fromJson(responseClima.body(), RespostaClima.class);
                                List<Clima> climas = respostaClima.list();
                                previsaoDeClima = new PrevisaoDeClima(climas, dia);
                            } else {
                                System.out.println("⚠️ Não foi possível obter os dados meteorológicos. Status: " + responseClima.statusCode());
                            }

                        } catch (Exception e) {
                            System.out.println("⚠️ Erro ao buscar dados de clima.");
                        }
                    }

                    Viagem viagem = new Viagem(busca, valorEmReais, dia, pais, previsaoDeClima);
                    System.out.println(viagem);

                    String nomeArquivo = "ViagemPara" + viagem.getDestino().replace(" ", "") + ".json";
                    FileWriter escrita = new FileWriter(nomeArquivo);
                    escrita.write(gson.toJson(viagem));
                    escrita.close();
                    System.out.println("✅ Arquivo " + nomeArquivo + " gerado com sucesso!");
                }
            } catch (Exception e) {
                System.out.println("⚠️ Erro inesperado ao buscar dados do país.");
                e.printStackTrace();
            }
        }
        sc.close();
    }
}