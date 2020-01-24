O projeto foi desenvolvido utilizando o SpringTollSuite4 (STS). Basta fazer o download do projeto no GitHub em formato .zip, descompactar em máquina local e impotar, através do STS já instalado, na máquina local. Para isso, ao abrir o STS, através do menu principal, execute na sequência: Menu Principal-> File->Import->Existing Maven Projects->Selecione a pasta do projeto descompactado baixado e marque o arquivo que aparecer na janela de opções. Essas operações irão abrir o projeto Maven.


Para executar, basta localizar no Package Explorer (ao lado esquerdo da tela) o arquivo "NasaApplication.java". Ao aparecer na janela o código do arquivo, clique no botão de Play na Barra de Botões superior, execute como "Spring Boot App".

No pacote "com.example.nasa.controller" é vista a classe da API desenvolvida. A classe NasaController.java tem dois métodos: buscarMedia() que calcula a média de temperaturas e o método buscarPorId() que busca a temperatura de um SOL específico. O valor de temperatura utilizado para cálculo da média ou recuperação foi o campo "av" da classe "AT" de um número SOL.

Para testar a API, utilizou-se o sistema de testes "PostMan". Nos teste efetuados utiliza-se o comando de requisição "GET"e "http://localhost:8080//my-api.com/nasa/temperature" para obter a média de temperaturas e, por exemplo, "http://localhost:8080//my-api.com/nasa/temperature/405" para obter um valor de temperatura específico.

O sistema retorna o valor socilitado em formato jason.

O seguinte link " https: //api.nasa.gov/insight_weather/?api_key=DEMO_KEY&feedtype=json&ver=1.0" foi utilizado para obter os dados de temperatura do site da NASA.
