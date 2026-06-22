package Models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PrevisaoDeClima {

    // Guarda a temperatura média do dia analisado
    private double temperaturaMedia;

    // Indica se existe previsão de chuva no dia
    private boolean chuva;

    // Construtor que recebe:
    // - lista completa de previsões da API (3 em 3 horas)
    // - data que o usuário quer consultar
    public PrevisaoDeClima(List<Clima> listaClimas, LocalDate dataAlvo) {

        // Converte a data para o formato do JSON da API: yyyy-MM-dd
        // Exemplo: 2026-06-13
        String dataAlvoStr = dataAlvo.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
        );

        // Filtra apenas as previsões do dia desejado
        List<Clima> previsoesDoDia = listaClimas.stream()
                .filter(clima -> clima.dt_txt().startsWith(dataAlvoStr))
                .toList();

        // Se a API não tiver dados para esse dia
        if (previsoesDoDia.isEmpty()) {

            System.out.println(
                    "⚠️ Dados para " + dataAlvoStr +
                            " não disponíveis na API. Usando valores zerados."
            );

            this.temperaturaMedia = 0.0;
            this.chuva = false;

        } else {

            // Calcula a temperatura média do dia
            double mediaTemp = previsoesDoDia.stream()
                    .mapToDouble(clima -> clima.main().temp())
                    .average()
                    .orElse(0.0);

            // Verifica se em qualquer horário do dia aparece "Rain"
            boolean vaiChover = previsoesDoDia.stream()
                    .anyMatch(clima -> clima.weather().stream()
                            .anyMatch(w -> w.main()
                                    .equalsIgnoreCase("Rain")));

            // Salva os resultados nos atributos da classe
            this.temperaturaMedia = mediaTemp;
            this.chuva = vaiChover;
        }
    }

    // Getter da temperatura média
    public double getTemperaturaMedia() {
        return temperaturaMedia;
    }

    // Getter da previsão de chuva
    public boolean getChuva() {
        return chuva;
    }
}