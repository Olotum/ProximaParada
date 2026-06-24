🌍 Próxima Parada

O Próxima Parada é uma aplicação desenvolvida em Java que simula um assistente de planejamento de viagens internacionais. O sistema integra diferentes APIs externas para fornecer informações completas sobre países, clima e conversão de moedas, gerando também um arquivo JSON com os dados da viagem.

✨ Funcionalidades
🔎 Busca de países utilizando a RestCountries API (v5)
🏙️ Identificação da capital e coordenadas geográficas
🌦️ Consulta de previsão do tempo via OpenWeatherMap (Forecast 5 dias)
💱 Conversão de moedas via AwesomeAPI
📊 Cálculo automático de:
Temperatura média na data selecionada
Probabilidade de chuva
💾 Geração de arquivo .json com os dados da viagem
🧠 Modelagem com records e classes Java modernas
🧠 Como o sistema funciona

O fluxo principal da aplicação é:

Usuário informa:
Destino (país)
Quantos dias até a viagem (máx. 5)
Valor disponível em reais
O sistema:
Consulta dados do país na RestCountries API
Obtém capital + coordenadas
Busca previsão do tempo para a data selecionada
Converte a moeda local para reais
Monta um objeto Viagem
O resultado:
Exibido no console
Salvo em arquivo JSON (ViagemPara{Destino}.json)
🧱 Estrutura do projeto
src/
 ├── Main.java
 └── Models/
      ├── Viagem.java
      ├── Pais.java
      ├── Currencies.java
      ├── Capital.java
      ├── Coordinates.java
      ├── Clima.java
      ├── PrevisaoDeClima.java
      ├── RespostaClima.java
      ├── RespostaRestCountry.java
      ├── ConversorDeMoeda.java
      ├── Data.java
      └── ...
🧩 Principais classes
🧳 Viagem

Classe central que agrega todas as informações da viagem:

País e capital
Data da viagem
Condições climáticas
Moeda local
Valor convertido para reais
🌦️ PrevisaoDeClima

Processa os dados da API OpenWeatherMap e calcula:

Temperatura média do dia selecionado
Se há previsão de chuva
💱 ConversorDeMoeda

Responsável por:

Buscar cotação na AwesomeAPI
Converter moeda local → BRL
Tratar erros de requisição
🔌 APIs utilizadas
🌍 RestCountries v5
🌦️ OpenWeatherMap Forecast API
💱 AwesomeAPI (cotações)
📦 Exemplo de saída
{
  "nomeDoPais": "Japan",
  "destino": "Tokyo",
  "dia": "2026-06-15",
  "chuva": false,
  "temperaturaMedia": 22.4,
  "moedaLocal": "Yen",
  "codeMoeda": "JPY",
  "cacheDaViagemMoedaLocal": 513.20
}
⚠️ Tratamento de erros

O sistema lida com:

Falhas de API (HTTP != 200)
País sem moeda cadastrada
Dados climáticos indisponíveis
Erros de conexão (IOException / InterruptedException)
Datas fora do range da previsão (fallback seguro)
🚀 Objetivo do projeto

Este projeto foi desenvolvido para praticar:

Consumo de APIs REST com Java HttpClient
Manipulação de JSON com Gson
Uso de records e modelagem orientada a dados
Integração de múltiplos serviços externos
Organização de código em camadas simples
🧪 Melhorias futuras
 Separação em camadas (Service / Controller)
 Cache de requisições HTTP
 Interface gráfica (JavaFX ou Web)
 Testes unitários (JUnit)
 Suporte a múltiplas moedas na carteira
 Refatoração para injeção de dependência
👨‍💻 Autor

Desenvolvido por José Gabriel Santos
