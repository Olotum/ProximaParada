package Models;

import com.google.gson.*;
// Biblioteca usada para interpretar o JSON retornado pela API

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConversorDeMoeda {

    // Cliente HTTP usado para fazer requisições para APIs externas
    private final HttpClient client = HttpClient.newHttpClient();

    // Método que converte um valor com base na moeda do país informado
    public double converterParaReais(double valor, Pais pais) {

        // Verifica se o país possui moeda cadastrada
        if (pais.currencies() == null || pais.currencies().isEmpty()) {
            throw new RuntimeException("O país não possui moeda cadastrada.");
        }

        // Pega o código da primeira moeda da lista (ex: JPY, USD, EUR)
        String codigoMoeda = pais.currencies().get(0).code();

        // Monta a URL da AwesomeAPI para consultar a cotação da moeda em relação ao Real
        String endereco =
                "https://economia.awesomeapi.com.br/json/last/"
                        + codigoMoeda + "-BRL";

        try {
            // Cria a requisição HTTP GET
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endereco))
                    .build();

            // Envia a requisição e recebe a resposta em String
            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            // Verifica se a API respondeu com sucesso (HTTP 200)
            if (response.statusCode() != 200) {
                throw new RuntimeException(
                        "Erro ao consultar a cotação. HTTP: "
                                + response.statusCode());
            }

            // Converte o JSON da resposta em JsonObject
            JsonObject root = JsonParser.parseString(response.body())
                    .getAsJsonObject();

            // Obtém a chave dinâmica do JSON (ex: JPYBRL, USDBRL)
            String chave = root.keySet().iterator().next();

            // Extrai o valor de compra da moeda ("bid")
            double bid = root
                    .getAsJsonObject(chave)
                    .get("bid")
                    .getAsDouble();

            // Realiza a conversão do valor usando a cotação
            return valor / bid;

        } catch (InterruptedException e) {
            // Caso a thread seja interrompida durante a requisição
            Thread.currentThread().interrupt();
            throw new RuntimeException("A consulta foi interrompida.", e);

        } catch (IOException e) {
            // Caso ocorra falha de rede ou comunicação com a API
            throw new RuntimeException(
                    "Falha ao consultar a cotação da moeda.", e);
        }
    }
}