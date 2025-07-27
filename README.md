 <body>
    <h1>💈 BarberSchedule_Back</h1>

   <p>
      Este é o <strong>back-end</strong> de um sistema de agendamento para barbearias, desenvolvido com <strong>Spring Boot</strong> e <strong>Java</strong>. A API permite o gerenciamento de usuários, horários e serviços de forma eficiente.
    </p>

  <h2>🔧 Funcionalidades</h2>
    <ul>
      <li>Cadastro e autenticação de usuários</li>
      <li>Criação e cancelamento de agendamentos</li>
      <li>Listagem de horários disponíveis</li>
      <li>Cadastro de barbeiros</li>
      <li>Validação de conflitos de horário</li>
    </ul>

   <h2>🛠 Tecnologias Utilizadas</h2>
    <ul>
      <li>Java 17</li>
      <li>Spring Boot</li>
      <li>Spring Security</li>
      <li>JPA / Hibernate</li>
      <li>PostgreSQL</li>
      <li>Lombok</li>
    </ul>

  <h2>📦 Como Executar</h2>
    <ol>
      <li>Clone o projeto:</li>
      <pre><code>git clone https://github.com/Jpsoaresfig/barberSchedule_Back.git</code></pre>

   <li>Configure o arquivo <code>application.properties</code> com suas credenciais do banco de dados:</li>
      <pre><code>
spring.datasource.url=jdbc:postgresql://localhost:5432/seubanco
spring.datasource.username=usuario
spring.datasource.password=senha
      </code></pre>

  <li>Rode o projeto:</li>
      <pre><code>./mvnw spring-boot:run</code></pre>
    </ol>

  <h2>📁 Estrutura Básica</h2>
    <ul>
      <li><code>/controller</code> – Endpoints da API</li>
      <li><code>/service</code> – Regras de negócio</li>
      <li><code>/repository</code> – Acesso ao banco</li>
      <li><code>/model</code> – Entidades JPA</li>
      <li><code>/config</code> – Configurações do Spring</li>
    </ul>

   <h2>🧪 Testes</h2>
    <p>
      Em breve será adicionada cobertura de testes automatizados com JUnit.
    </p>

   <h2>📬 Contato</h2>
    <p>
      Projeto desenvolvido por <strong>João Pedro</strong>.<br />
      GitHub: <a href="https://github.com/Jpsoaresfig" target="_blank">Jpsoaresfig</a>
    </p>
  </body>
</html>
