package Models; // (Se estiver no mesmo pacote dos outros models)

public record Main(

        double temp
        // Temperatura em graus (depende da unidade configurada na API)
        // Exemplo: 20.02 °C

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
