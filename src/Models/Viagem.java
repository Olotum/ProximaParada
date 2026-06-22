package Models;

import java.time.LocalDate;

public class Viagem {

    // Nome do país pesquisado pelo usuário
    private String nomeDoPais;

    // Capital / destino da viagem
    private String destino;

    // Dia da viagem
    private LocalDate dia;

    // Indica se vai chover
    private boolean chuva;

    // Temperatura média prevista
    private double temperaturaMedia;

    // Nome da moeda local
    private String moedaLocal;

    // Código da moeda local (USD, BRL, JPY...)
    private String codeMoeda;

    // Valor convertido para a moeda/local ou em reais (nome pode ser melhorado)
    private double cacheDaViagemMoedaLocal;

    public Viagem(
            String busca,
            double valor,
            LocalDate dia,
            Pais pais,
            PrevisaoDeClima previsaoDeClima
    ) {

        // Guarda o nome do país digitado pelo usuário
        this.nomeDoPais = busca;

        // Define a capital do país como destino
        this.destino = pais.getNomeCapital();

        // Guarda a data da viagem
        this.dia = dia;

        // Obtém previsão de chuva
        this.chuva = previsaoDeClima.getChuva();

        // Obtém temperatura média prevista
        this.temperaturaMedia =
                previsaoDeClima.getTemperaturaMedia();

        // Pega o nome da primeira moeda do país
        this.moedaLocal =
                pais.currencies().getFirst().name();

        // Pega o código da moeda
        this.codeMoeda =
                pais.currencies().getFirst().code();

        // Instancia conversor de moeda
        ConversorDeMoeda conversorDeMoeda =
                new ConversorDeMoeda();

        // Calcula valor convertido
        this.cacheDaViagemMoedaLocal =
                conversorDeMoeda.converterParaReais(valor, pais);
    }

    // Getter do país
    public String getNomeDoPais() {
        return nomeDoPais;
    }

    // Getter do destino
    public String getDestino() {
        return destino;
    }

    // Getter da data
    public LocalDate getDia() {
        return dia;
    }

    // Getter da chuva
    public boolean isChuva() {
        return chuva;
    }

    // Getter da temperatura média
    public double getTemperaturaMedia() {
        return temperaturaMedia;
    }

    // Getter da moeda local
    public String getMoedaLocal() {
        return moedaLocal;
    }

    // Getter do código da moeda
    public String getCodeMoeda() {
        return codeMoeda;
    }

    // Getter do valor convertido
    public double getCacheDaViagemMoedaLocal() {
        return cacheDaViagemMoedaLocal;
    }

    // Representação textual da classe
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