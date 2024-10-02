
# Desafio Back-end PicPay

O PicPay Simplificado é um desafio proposto pelo picpay afim de validar conhecimentos de candidatos em uma de suas etapas de contratação. 

Este repositório é a minha implementação desse desafio. Você pode encontrar o repositório do desafio em: https://github.com/PicPay/picpay-desafio-backend visite para uma explicação mais detalhada.

*Projeto feito por mim, sem ajuda de tutorial.*



## Entendendo o projeto

O PicPay Simplificado é uma plataforma de pagamentos simplificada. Nela é possível depositar e realizar transferências de dinheiro entre usuários. Temos 2 tipos de usuários, os comuns e lojistas, ambos têm carteira com dinheiro e realizam transferências entre eles.

### Requisitos
A seguir estão algumas regras de negócio que são importantes para o funcionamento do PicPay Simplificado:
- Para ambos tipos de usuário, precisamos do ```Nome Completo```, ```CPF```, ```e-mail``` e ```Senha```. CPF/CNPJ e e-mails devem ser únicos no sistema. Sendo assim, seu sistema deve permitir apenas um cadastro com o mesmo CPF ou endereço de e-mail;

- Usuários podem enviar dinheiro (efetuar transferência) para lojistas e entre usuários;

- Lojistas **só recebem** transferências, não enviam dinheiro para ninguém;;

- Validar se o usuário tem saldo antes da transferência;

- Antes de finalizar a transferência, deve-se consultar um serviço autorizador externo, use este mock https://util.devi.tools/api/v2/authorize para simular o serviço utilizando o verbo ```GET```;

- A operação de transferência deve ser uma transação (ou seja, revertida em qualquer caso de inconsistência) e o dinheiro deve voltar para a carteira do usuário que envia;

- No recebimento de pagamento, o usuário ou lojista precisa receber notificação (envio de email, sms) enviada por um serviço de terceiro e eventualmente este serviço pode estar indisponível/instável. Use este mock https://util.devi.tools/api/v1/notify para simular o envio da notificação utilizando o verbo ```POST```;

- Este serviço deve ser RESTFul.`

### Endpoint de transferência

Você pode implementar o que achar conveniente, porém vamos nos atentar somente ao fluxo de transferência entre dois usuários. A implementação deve seguir o contrato abaixo.

```json
POST /transfer
Content-Type: application/json

{
  "value": 100.0,
  "payer": 4,
  "payee": 15
}
```

### Detalhes da implementação

**Uso do design pattern Strategy:** Podemos perceber que há no mínimo 3 condições para que uma transferência seja realizada. Futuramente pode ser adicionada mais condições, tornando nosso código cada mais mais complexo e difícil de ser entendido. Como solução implementei esse pattern onde criamos uma interface que chamei de ```TransferenciaValidator``` ela conterá apenas um método abstrato (validar()), em seguida criei classes que a implementam, o qual cada classe será responsável por um tipo de validação, onde verifica por exemplo: ```Saldo suficiente```, ```Autorizador externo```, ```Tipo usuário```. Cada classe possui um @Component, onde dizemos para o spring ioc que ele será responsável por gerir e injetar essa dependência onde e quando for preciso. Em ```TransferenciaService``` temos um campo de lista de TransferenciaValidator (o spring fará a injeção das implementações dessa interface), no método de realizar transferencia apenas iteramos por essa lista chamando o método validar, cada TransferenciaValidator fará sua própria validação, caso dê erro, lancará uma exceção que é tratada e retornada para o cliente em formato JSON. 

*Detalhe:* *Cada classe de validação possui um @Order, onde diremos ao spring a ordem de prioridade que esses objetos serão injetados, por exemplo, em nossa lista. Assim setamos a seguinte ordem: Verifica se o usuário é lojista -> Verifica o saldo do usuário -> Verifica o autorizador externo. Assim seguindo a ideia de falhar rápido*

**Lógica de notificação:** Há um requisito que o pagador da transferência precisa receber uma notificação, esta será realizada por um outro serviço, onde poderá estar indisponível. 

Minha implementação: O importante para o pagador é se a transferência foi realizada, após a transação o usuário não precisa ficar esperando o backend fazer uma solicitação para o serviço de notificação (isso levará tempo). Conclusão, poderemos fazer de forma assíncrona. Tentaremos 3 vezes se comunicar com o serviço de notificação, onde esperaremos 3 segundos até realizar uma nova tentativa, ao final, independente de ter sido com sucesso ou não, salvamos no banco de dados o status, a hora, número de tentativas dessa notificação de transferência.

## Rode com o docker 🐳

- Inicie o docker

No terminal, Digite:
```bash
docker container run -p 8080:8080 caiovrodrigues/picpay-simplificado:latest
```
Pronto, o docker fará o download da imagem lá do docker hub e subirá um container com a aplicação sendo executada na porta 8080

Já existem 2 usuários salvos no banco, experimente fazer uma transferência entre eles, faça uma requisição do tipo POST para ```http://localhost:8080/transfer```, passando no corpo da requisição as propriedades json apresentada acima, substituindo o valor de payer por 1 e payee por 2. 

*Você poderá ver os logs da aplicação no terminal*

*Essa aplicação usa o banco de dados em memória H2, acesse http://localhost:8080/h2-console para verificar as tabelas de Usuário, Transferência e Transferência notificação*


## Rode localmente

Clone o projeto

```bash
  git clone https://github.com/caiovrodrigues/picpay-simplificado.git
```

Vá até a pasta do projeto via terminal

```bash
  cd picpay-simplificado
```

Digite o comando

```bash
  code .
```
Assim o projeto vai abrir na sua IDE e a rode

Ou abra direto na sua IDE de preferência e a rode

É necessário ter a JDK instalada na sua máquina

