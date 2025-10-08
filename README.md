# Personal Finance - Configuração do Projeto

Este guia descreve como configurar e rodar o projeto **Personal Finance** usando **IntelliJ Community** e **PostgreSQL via Docker**.

---

## 1️⃣ Pré-requisitos

- Java 17+ instalado (preferencialmente via SDKMAN ou JDK oficial)
- Maven 3.8+
- Docker Desktop instalado e configurado com **WSL2** (Windows) ou Docker nativo (Linux/Mac)
- IntelliJ IDEA Community Edition

---

## 2️⃣ Configuração do IntelliJ

1. Abra o IntelliJ e clique em **File → Open**.
2. Selecione a pasta raiz do projeto (`personal-finance`).
3. Aguarde o IntelliJ carregar o projeto Maven e baixar as dependências.

### Configurar run/debug (Application)

1. Vá em **Run → Edit Configurations → + → Application**
2. Configure:
    - **Name:** PersonalFinanceApplication
    - **Main class:** `com.metodosageis.personal_finance.PersonalFinanceApplication`
    - **Use classpath of module:** `personal-finance`
3. Clique em **Apply** e depois **OK**.

4. Para rodar, selecione a configuração **PersonalFinanceApplication** e clique em **Run**.

---


## 3️⃣ Corrigindo Lombok no IntelliJ

Caso o projeto não reconheça as anotações do Lombok (ex: `@Builder`, `@Getter`, `@Setter`):

1️⃣ **Instalar o plugin Lombok**

- Vá em `File > Settings > Plugins > Marketplace`.
- Busque por **Lombok** e instale.
- Reinicie o IntelliJ.

2️⃣ **Ativar annotation processing**

- Vá em `File > Settings > Build, Execution, Deployment > Compiler > Annotation Processors`.
- Marque **Enable annotation processing**.
- Clique OK.

3️⃣ **Reimportar dependências Maven**

- Clique com o botão direito no projeto → **Maven → Reimport**.
- Ou no terminal:

```bash
mvn clean install 
```


## 4️⃣ Configuração do Banco com Docker

1. O arquivo `docker-compose.yml` está localizado em:  /personal-finance/src/main/docker/banco/docker-compose.yml
2. ### Subir o banco

No terminal:

```bash
cd src/main/docker/banco
docker-compose up -d
```