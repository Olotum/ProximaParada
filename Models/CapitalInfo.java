package Models;

import java.util.List;
//Record que contem a latitude e logitude da capital
public record CapitalInfo(
        List<Double> latlng
) {}