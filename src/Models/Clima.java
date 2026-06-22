package Models;

import java.util.List; // Importa List porque "weather" vem como array no JSON

// Record que junta as informações do clima
public record Clima(

        String dt_txt,
        // Data e horário da previsão.
        // Exemplo: "2026-06-13 12:00:00"
        // Indica para qual momento essa previsão vale.

        List<Weather> weather,
        // Lista de condições climáticas.
        // A API retorna isso como array, mesmo quando há só 1 item.
        // Exemplo: descrição, ícone, condição principal (Rain, Clouds...)

        Main main
        // Objeto com dados principais da previsão:
        // temperatura, sensação térmica, umidade, pressão etc.

) {}
//RespostaClima
// └── List<Clima>
//      ├── Clima
//      │    ├── dt_txt
//      │    ├── Main
//      │    └── List<Weather>
//      ├── Clima
//      ├── Clima
//      └── ...