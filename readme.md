# Microserviços usando Java, Spring Boot, Maven e Kafka

## Informações
This repository is used as the example for the following articles:
O repositório serve para mostrar meu conhecimento nas tecnologias e de ajuda aos Devs:
https://github.com/fvieira-code/sample-spring-kafka-microservices

## Descrição
Existem e (três) microserviços: \

`order-service` - Envia as mensagens de `Order` para o Kafka topic and que vai distribuir as transações \
`payment-service` - Executa a regra transacional do preço da `Order` no consumidor enviou \
`stock-service` - Executa as transações na loja com base no número de produtos no `Order`

Aqui está o diagrama de nossa arquitetura:
![image](https://github.com/fvieira-code/sample-spring-kafka-microservices/master/arch.png)

(1) `order-service` envia uma nova `Order` -> `status == NEW` \
(2) `payment-service` e `stock-service` recebe `Order` e realiza uma transação local nos dados \
(3) `payment-service` and `stock-service` envia uma resposta `Order` -> `status == ACCEPT` ou `status == REJECT` \
(4) `order-service` processa a entrada de pedidos de `payment-service` e `stock-service`, faz a composição `Order` e envia pedido com novo status -> `status == CONFIRMATION` ou `status == ROLLBACK` ou `status == REJECTED` \
(5) `payment-service` e `stock-service` recebe `Order` com um status atualizado e "commit" ou "rollback" uma transação local feita antes

## Executando o docker em sua máquina local

Você pode executar facilmente todos os aplicativos no Docker com suporte Spring Boot para
(a) Testcontainers
(b) Docker Compose

(a) Para Testcontainers
Vá para o diretório `order-service` e execute:
```shell
$ mvn clean spring-boot:test-run
```

Então vá para o diretório `payment-service` e execute:
```shell
$ mvn clean spring-boot:test-run
```

Finalizando vá para o diretório `stock-service` e execute:
```shell
$ mvn clean spring-boot:test-run
```

Você terá três aplicativos em execução com um único Kafka compartilhado em Testcontainers.

(b) Para executar o Docker Compose

Primeiro construa todo o projeto e imagens com o seguinte comando:
```shell
$ mvn clean package -DskipTests -Pbuild-image
```

Após o comando acima, vá para os diretórios na seguinte ordem : `order-service`, `payment-service` ou `stock-service` e execute:
```shell
$ mvn spring-boot:run
```

Você vai iniciar seu aplicativo e inicia o Kafka com outros dois contêiners com o Docker Compose.

Acesse o Kafdrop e veja tudo que foi criado no Kafka:
http://localhost:19000/
