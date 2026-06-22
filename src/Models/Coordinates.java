package Models; // Define que a classe pertence ao pacote Models

public record Coordinates( // Record que representa coordenadas geográficas

                           double lat,
                           // Latitude:
                           // Mede a posição norte/sul em relação à linha do Equador.
                           // Exemplo: Brasília = -15.79

                           double lng
                           // Longitude:
                           // Mede a posição leste/oeste em relação ao Meridiano de Greenwich.
                           // Exemplo: Brasília = -47.88

) {}