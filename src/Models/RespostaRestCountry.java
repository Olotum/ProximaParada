package Models;
//Esse record encapsula resposta completa da API RestCountry
public record RespostaRestCountry(

        Data data
        // Objeto que contém os dados retornados pela API,
        // provavelmente uma lista de países ou informações relacionadas.

) {}
//RespostaRestCountry
// └── Data
//      └── List<Pais> (objects)
//           ├── Pais
//           │    ├── List<Capital> (capitals)
//           │    │    └── Capital
//           │    │         ├── name
//           │    │         └── Coordinates
//           │    │              ├── lat
//           │    │              └── lng
//           │    │
//           │    └── List<Currencies> (currencies)
//           │         └── Currencies
//           │              ├── code
//           │              ├── name
//           │              └── symbol
//           │
//           ├── Pais
//           ├── Pais
//           └── ...
