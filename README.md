# PRIMEIRO COMMIT 

CURSO COMPLETO SPRING REACT

# ARQUITETURA DO PROJETO BACKEND É DIVIDIDO EM 3 CAMADAS:
## MODEL
* Repositories
* Classes DAO
* Persistência 
## SERVICE
* Regras de negócio
## CONTROLLER
* Endpoints
* DTOs

# Spring
* O container no spring é formado por beans gerenciados, esses beans são instâncias de objetos de configuração, negócio, infraestrutura, componentes e outros.
* Para serem reconhecidos com beans gerenciados ou springBeans, devem ser anotados com os SpringAnotaion, exemplo: @Component, @Service, @Controller, @Bean dentre outros.
* Por padrão os beans são singletons. sendo uma instância de cada por aplicação.

# Testes
	Testes de integração são testes que dependem de configurações ou serviços externos, exemplo: Banco de Dados, Apis etc.
@SpringBootTest sobe o contexto do spring para executar os testes, isso inclui classes de serviços, configuração e repository. Dependendo do teste a ser feito, não vale a pena usar tal anotação, exemplo uma classe de teste que faça teste somente no banco de dados, o melhor seria usar @DataJpaTest.












