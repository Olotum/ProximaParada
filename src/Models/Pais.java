package Models;

import java.util.List;
import java.util.Map;
//Record que junta as informações do pais
public record Pais(
        //nome digitado durante a pesquisa pelo usuario
        List<String> capital,
        Map<String, Currencies> currencies,
        CapitalInfo capitalInfo
) {
}
