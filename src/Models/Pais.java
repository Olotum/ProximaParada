package Models;

import java.util.List; // Importa List para armazenar múltiplas capitais/moedas

public record Pais(

        List<Capital> capitals,
        // Lista de capitais do país.
        // A maioria dos países terá 1 capital, mas alguns podem ter mais.

        List<Currencies> currencies
        // Lista de moedas usadas no país.
        // Ex: USD, BRL, EUR etc.

) {

    public String getNomeCapital() {
        // Método auxiliar para pegar o nome da primeira capital

        if (capitals != null && !capitals.isEmpty()) {
            // Verifica se a lista existe e tem pelo menos 1 item
            return capitals.get(0).name();
            // Retorna o nome da primeira capital
        }

        return "N/A";
        // Caso não exista capital cadastrada
    }
}

