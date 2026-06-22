package Models; // Define que a classe pertence ao pacote Models

public record Currencies( // Record que representa uma moeda

                          String code,
                          // Código internacional da moeda (padrão ISO 4217)
                          // Ex: "USD", "BRL", "JPY", "EUR"

                          String name,
                          // Nome da moeda
                          // Ex: "US Dollar", "Brazilian Real", "Japanese Yen"

                          String symbol
                          // Símbolo usado para representar a moeda
                          // Ex: "$", "R$", "¥", "€"

) {}
