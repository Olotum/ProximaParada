package Models;

import java.util.List;
import java.util.Map;

public record Pais(
        //nome digitado durante a pesquisa pelo usuario
        List<String> capital,
        Map<String, Moeda> currencies,
        CapitalInfo capitalInfo
) {
}
