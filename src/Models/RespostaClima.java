package Models;

import java.util.List; // Importa List para armazenar várias previsões
//Esse record encapsula resposta completa da API de previsão do tempo da OpenWeatherMap
public record RespostaClima(

        List<Clima> list
        // Lista com todas as previsões retornadas pela API.
        // Cada item representa um horário específico (de 3 em 3 horas).

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