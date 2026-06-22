package Models; // Define que essa classe pertence ao pacote Models,
// onde ficam os modelos de dados da aplicação.

public record Capital( // Declara um record chamado Capital.
                       String name,   // Armazena o nome da capital (ex: "Brasília").

                       Coordinates coordinates // Armazena as coordenadas da capital.
                       // Coordinates provavelmente é outro record/classe com latitude e longitude.
) {} // Como é record, Java gera automaticamente:
// - construtor
// - getters (name() e coordinates())
// - toString()
// - equals()
// - hashCode()