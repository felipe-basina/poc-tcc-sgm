# SISTEMA DE GESTÃO INTEGRADA MUNICIPAL - SGM
###### Desenvolvimento da prova de conceito (POC) para trabalho de conclusão de curso pós graduação Arquitetura de Software Distribuído PUC MG

### Arquitetura
![alt text](/dgs/poc.jpeg)
Esta solução de arquitetura é composta pelos seguintes componentes:
##### safim-api
Responsável por verificar se um usuário encontra-se cadastrado e ativo no Sistema Administrativo-Financeiro de Gestão Municipal
##### mua-api
Responsável por cadastrar e recuperar dados de usuário. Para o cadastro de um usuário este componente deverá se comunicar com o componente **safim-api**, através do componente **sgm-api-gateway**, para definir o tipo de perfil correto para o mesmo
##### sgm-api-gateway
Responsável por permitir a comunicação entre e com microsserviços. No caso, as seguintes comunicações são consideradas:
* mua-api x safim-api
* sgm-web x mua-api
##### sgm-web
Responsável por prover as interafaces de usuário do sistema. Além disso, faz parte deste componente as funções de **autenticação** e **autorização** de usuários
