# 📚 README

---

## 🎯 Membros do Grupo

- **Caio Eloi Campos - 2020031498** - Desenvolvimento Backend (Kotlin/Java)

---

## 📝 Explicação do Sistema

O projeto é um sistema bancário que implementa funcionalidades essenciais para o gerenciamento de contas e transações em um banco. Ele utiliza arquivos JSON para persistência dos dados e armazena informações das contas bancárias, bem como transações associadas a cada conta. A arquitetura modular e organizada visa garantir a separação das responsabilidades entre os componentes do sistema, facilitando tanto a manutenção quanto a adição de novas funcionalidades.

### Principais Componentes:
1. **AccountService**:
Responsável pela lógica das operações de conta, como depósitos, saques e transferências. Ele manipula os saldos das contas e registra transações no histórico das mesmas.
2. **BankService**:
É a camada central do projeto, responsável por:

   - Criar novas contas bancárias.
   - Realizar transferências entre contas.
   - Depositar e sacar valores.
   - Persistir os dados das contas no armazenamento e garantir que as informações estejam sempre atualizadas nos arquivos JSON.

3. **Persistência em Arquivos JSON**:
O sistema armazena as contas e transações no arquivo accounts.json. Isso é gerenciado pela classe FileManagerUtil, garantindo a leitura e gravação dos dados em arquivos.

### Funcionalidades Implementadas:
- **Depósito**: Adiciona valores ao saldo da conta.
- **Saque**: Reduz o saldo da conta respeitando a validação do saldo disponível.
- **Transferência**: Move valores entre contas, garantindo transações seguras.
- **Extrato da Conta**: Gera relatórios detalhados das transações e saldo das contas.
- **Resumo do Banco** Apresenta a visão consolidada das contas e seus saldos.

Esse projeto foi projetado para ser facilmente testável, com suporte a testes unitários e mocks. Isso assegura a confiabilidade das operações e a robustez do sistema em cenários de desenvolvimento e produção.

---

## ⚛️ Tecnologias Utilizadas

- **Kotlin**  
  Linguagem de programação principal para desenvolvimento do projeto.

- **Java**  
  Utilizada em conjunto com Kotlin, especialmente em partes legadas e integração com dependências já existentes.

- **FileManagerUtil**  
  Responsável pelo acesso e manipulação das contas em arquivos JSON no sistema.

- **JUnit (Testes Unitários)**  
  Framework de testes utilizado para assegurar que o sistema funciona corretamente.

- **Maven**  
  Gerenciador de dependências e build system para configurar e executar o projeto.

- **IDE**
    - IntelliJ IDEA para desenvolvimento do projeto.

---

## 📌 Como Executar o Projeto?

Para rodar o projeto localmente:

1. Clone este repositório.
2. Compile e instale as dependências do projeto:

```shell
mvn clean install
```
3. Inicie o projeto
```shell
mvn spring-boot:run
```
4. Caso queira executar os testes
```shell
mvn test
```

# 🚧 Melhorias Futuras
- Integração com Banco de Dados Relacional: Implementar o armazenamento em um banco como PostgreSQL para escalabilidade e performance.
- APIs REST: Expor funcionalidades do sistema por meio de endpoints REST para acesso remoto.
- Interface Web e Mobile: Criar interfaces de usuário amigáveis para interagir facilmente com o sistema bancário.
- Segurança Avançada: Implementar autenticação e criptografia das transações e saldos.

# 📧 Contato
### Para dúvidas e sugestões, entre em contato:

- Caio Eloi - [caioeloi09@gmail.com / caioest@ufmg.br]

