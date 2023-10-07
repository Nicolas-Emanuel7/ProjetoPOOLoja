
# ProjetoPOOLoja

## Componentes : 

* Anabel Marinho Soares <br>
* Nicolas Emanuel Alves Costa <br>
* Thiago Luan Moreira Sousa <br>
* Ytalo Dias Costa <br>

## Objetivo :

O objetivo principal do projeto é recriar, de forma básica e primitiva dentro de um terminal, o sistema de uma loja online que, nesse caso, seria de uma loja de calçados. Dentro desse sistema existem dois tipos de interfaces, a interface do Gerente do sistema e a interface do Cliente. Com isso, podemos ter um breve vislumbre de como é o desenvolvimento de um sistema de e-commerce real, e como as diversas partes em movimento de um sistema trabalham para que um todo funcione da forma desejada.
 
Um lado do sistema é o Gerenciamento de pedidos online em uma loja de calçados. Esse lado do sistema permite uma gestão simplificada de produtos, estoque, clientes e seus pedidos.

O outro lado do sistema é a interface do cliente que irá comprar dessa loja. Esse lado do sistema permite a visualização dos produtos disponíveis no sistema, a compra desses produtos e a visualização de pedidos anteriores, com os seus atributos.

O sistema contará com o acesso ao banco de dados PostgreSQL, que permitirá que sejam salvas informações coletadas durante a utilização do sistema. Dados que serão utilizados para o funcionamento de todos métodos do sistema.

## Funcionalidades implementadas

Primeiro decidimos que seria um sistema que tratasse de pedidos de produtos de uma plataforma virtual. Produtos esses que idealizamos como calçados. Para começar criamos as entidades: Calçado, Tamanho (depois renomeado para Estoque), Cliente, Gerente, Pedido, Item de Pedido e também os Enums: StatusPedido e TipoCalçado.  Todos com os atributos necessários que havíamos planejado. 

Em seguida, implementamos o escopo do Menu com os métodos que achamos necessários e a conexão com o banco de dados PostgreSql. Após a implementação dos métodos que envolviam a classe cliente e sua conexão com o banco de dados, nos foi relatado pelo professor que poderíamos substituir as classes Cliente e Gerente por uma única classe de Usuário, então o fizemos.

Também foi implementado os métodos que envolviam as classes de Calçado e Estoque e sua conexão com o banco de dados, o que permitiu que pudéssemos finalmente implementar as funções do Pedido, assim tendo todos os métodos inseridos no sistema. Sem demora, foram feitos alguns diversos ajustes para que tudo funcionasse da forma que desejamos e implementamos uma classe de exceção para então podermos nos sentir satisfeitos com o resultado do projeto. 

As maiores dificuldades vieram da implementação dos métodos do pedido em si, pois esses métodos envolviam partes e objetos de outras classes do programa, como o usuário e o calçado, o que tornou muito mais complicado fazer com que todas essas partes funcionassem em conjunto, mas com persistência e muito testes, acabou funcionando de forma aceitável. Outras dificuldades vieram da implementação do banco de dados, pois a princípio não era algo tão intuitivo de utilizar, mas após pesquisas e estudos sobre as funcionalidades, se tornou um pouco mais simples.

Durante todo o projeto a comunicação entre o grupo era de fácil acesso, todos compreenderam bem suas tarefas e as cumpriram a tempo e de forma que satisfizesse todo o grupo.

## Funcionalidades não concluídas

* Sistema de avaliação e comentários

Esse sistema serviria para que os clientes, após fazer um pedido, pudessem dar uma nota de 0 a 5, e uma avaliação textual sobre sua satisfação com o produto. Essa função não foi implementada pois consideramos que complicaria mais o código e deixaria a navegação do menu pelo terminal mais confusa e complexa.
          
* Interface gráfica
  
Seria utilizado as funcionalidades providas pelo próprio java para fazer uma interface gráfica interativa para o sistema. Porém, devido à complicação de implementar essas funções de forma que funcionassem com o sistema, e até mesmo que ficasse visualmente agradável, acabou causando um certo desprazer aos integrantes do grupo, o que fez com que desistimos da ideia.

* Classe de endereço mais aprofundada
  
Seria criada uma entidade separada do usuário para cadastrar seus endereços, de forma que pudesse dar mais detalhes. Exemplos de atributos: Cidade, UF, CEP, Rua, Bairro e número. Porém essa ideia foi descartada devido à complicação que isso traria para a navegação do terminal e a experiência básica de usuário que inicialmente projetamos. 

##  Conclusão

* Grau de dificuldade do projeto
  
O desenvolvimento do sistema teve um grau de dificuldade razoável onde, apesar de se ter em mente o objetivo final das funções, muitas vezes os métodos não funcionam da forma desejada. E era difícil encontrar a parte específica de se ajustar, devido tantos elementos trabalharem em conjunto. Fazer a conexão com o banco de dados também não foi exatamente intuitivo, já que a utilização do banco de dados ainda era um conceito muito novo para os integrantes do grupo.

* Importância dos conhecimentos obtidos
  
A experiência obtida através do projeto vai nos ajudar a entender melhor o funcionamento de sistemas maiores e mais complexos, sejam em Java ou não, onde se é necessário dar atenção e compreender as diversas partes que se movem e trabalham em conjunto para o funcionamento de um sistema. Entender o funcionamento de um banco de dados e como utilizar o SQL também será de suma importância para seguir esse caminho.

* Benefícios e prejuízos em relação a uma prova convencional
  
A tarefa de fazer um projeto como esse é muito importante para adquirir conhecimento e experiência nesse ramo. Onde se tem mais tempo para estudar como certas coisas funcionam, o ato de resolver problemas e ter novas ideias para se implementar no sistema simplesmente não são possíveis em uma prova convencional com uma tarefa predeterminada e tempo bem mais limitado.

* Acompanhamento do professor
  
Graças aos aconselhamentos do professor e de outros discentes, pudemos melhorar e sintetizar a codificação do sistema. Como quando foi aconselhado que as classes Cliente e Gerente, que compartilhavam de atributos parecidos, fossem unidos em uma só entidade: Usuário. 

* Comentários gerais / autoavaliação

A concepção de um projeto desse nível trouxe a oportunidade para que os integrantes tivessem uma prévia de como é o desenvolvimento de um sistema de alta escala. Foram necessários estudos a parte e um aprofundamento no conceito de banco de dados para que tivéssemos a capacidade de fazer com que o sistema funcionasse de uma forma aceitável. No fim, a codificação poderia ser melhor, mais limpa e o sistema como um todo poderia ser mais complexo e abranger mais funções. Porém, vendo o projeto como uma espécie de conceito do trabalho em java com conexão em banco de dados, foi uma experiência agradável e produtiva, que trouxe experiências valiosas para o futuro no ramo.

