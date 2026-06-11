package Models;

import java.time.LocalDate;

public class Viagem {
    private String nomeDoPais;
    private String destino;
    private LocalDate dia;
    private boolean chuva;
    private double temperaturaMedia;
    private String moedaLocal;
    private String codeMoeda;
    private double cacheDaViagemMoedaLocal;

    public Viagem(String busca, double valor, LocalDate dia, Pais pais, PrevisaoDeClima previsaoDeClima) {
        this.nomeDoPais = busca;
        this.destino = pais.getNomeCapital();
        this.dia = dia;
        this.chuva = previsaoDeClima.getChuva();
        this.temperaturaMedia = previsaoDeClima.getTemperaturaMedia();
        this.moedaLocal = pais.currencies().getFirst().name();
        this.codeMoeda = pais.currencies().getFirst().code();
        ConversorDeMoeda conversorDeMoeda = new ConversorDeMoeda();
        this.cacheDaViagemMoedaLocal = conversorDeMoeda.converterParaReais(valor, pais);
    }

    public String getNomeDoPais() {
        return nomeDoPais;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getDia() {
        return dia;
    }

    public boolean isChuva() {
        return chuva;
    }

    public double getTemperaturaMedia() {
        return temperaturaMedia;
    }

    public String getMoedaLocal() {
        return moedaLocal;
    }

    public String getCodeMoeda() {
        return codeMoeda;
    }

    public double getCacheDaViagemMoedaLocal() {
        return cacheDaViagemMoedaLocal;
    }

    @Override
    public String toString() {
        return "Viagem{" +
                "nomeDoPais='" + nomeDoPais + '\'' +
                ", destino='" + destino + '\'' +
                ", dia=" + dia +
                ", chuva=" + chuva +
                ", temperaturaMedia=" + temperaturaMedia +
                ", moedaLocal='" + moedaLocal + '\'' +
                ", codeMoeda='" + codeMoeda + '\'' +
                ", cacheDaViagemMoedaLocal=" + cacheDaViagemMoedaLocal +
                '}';
    }
}
