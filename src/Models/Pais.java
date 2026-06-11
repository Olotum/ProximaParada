package Models;

import java.util.List;

public record Pais(
        List<Capital> capitals,
        List<Currencies> currencies
) {
    public String getNomeCapital() {
        if (capitals != null && !capitals.isEmpty()) {
            return capitals.get(0).name();
        }
        return "N/A";
    }
}

