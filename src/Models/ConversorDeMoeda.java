package Models;

import com.google.gson.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
    public class ConversorDeMoeda {

        private final HttpClient client = HttpClient.newHttpClient();

        public double converterParaReais(double valor, Pais pais) {
            if (pais.currencies() == null || pais.currencies().isEmpty()) {
                throw new RuntimeException("O país não possui moeda cadastrada.");
            }

            // NOVA FORMA: Pega o primeiro item da lista e extrai o 'code' (ex: "JPY")
            String codigoMoeda = pais.currencies().get(0).code();

            String endereco =
                    "https://economia.awesomeapi.com.br/json/last/"
                            + codigoMoeda + "-BRL";

            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(endereco))
                        .build();

                HttpResponse<String> response =
                        client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() != 200) {
                    throw new RuntimeException(
                            "Erro ao consultar a cotação. HTTP: "
                                    + response.statusCode());
                }

                JsonObject root = JsonParser.parseString(response.body())
                        .getAsJsonObject();

                String chave = root.keySet().iterator().next();

                double bid = root
                        .getAsJsonObject(chave)
                        .get("bid")
                        .getAsDouble();

                return valor * bid;

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("A consulta foi interrompida.", e);

            } catch (IOException e) {
                throw new RuntimeException(
                        "Falha ao consultar a cotação da moeda.", e);
            }
        }
    }