package Models;

// Record que contém a condição do céu
public record Weather(

        String main
        // Condição principal do clima.
        // Exemplos:
        // "Clear"  -> céu limpo
        // "Clouds" -> nublado
        // "Rain"   -> chuva
        // "Snow"   -> neve
        // "Mist"   -> névoa

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