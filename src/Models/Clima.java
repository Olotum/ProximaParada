package Models;

import java.util.List;

// Record que junta as informações do clima
public record Clima(String dt_txt, List<Weather> weather, Main main) {}