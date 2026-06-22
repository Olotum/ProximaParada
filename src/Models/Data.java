package Models; // Define que pertence ao pacote Models

import java.util.List; // Importa List para armazenar vários países

public record Data(

        List<Pais> objects
        // Lista contendo vários objetos do tipo Pais

) {}