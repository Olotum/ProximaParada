package Models;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PrevisaoDeClima {
    private double temperaturaMedia;
    private boolean chuva;
    public PrevisaoDeClima(List<Clima> listaClimas, LocalDate dataAlvo) {
        String dataAlvoStr = dataAlvo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        List<Clima> previsoesDoDia = listaClimas.stream()
                .filter(clima -> clima.dt_txt().startsWith(dataAlvoStr))
                .toList();

        if (previsoesDoDia.isEmpty()) {
            System.out.println("⚠️ Dados para " + dataAlvoStr + " não disponíveis na API. Usando valores zerados.");
            this.temperaturaMedia = 0.0;
            this.chuva = false;
        } else {
            double mediaTemp = previsoesDoDia.stream()
                    .mapToDouble(clima -> clima.main().temp())
                    .average()
                    .orElse(0.0);

            boolean vaiChover = previsoesDoDia.stream()
                    .anyMatch(clima -> clima.weather().stream()
                            .anyMatch(w -> w.main().equalsIgnoreCase("Rain")));

            this.temperaturaMedia = mediaTemp;
            this.chuva = vaiChover;
        }
    }
    public double getTemperaturaMedia() {
        return temperaturaMedia;
    }

    public boolean getChuva() {
        return chuva;
    }
}