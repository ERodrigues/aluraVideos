## Introdução 
### AluraFlix
Projeto criado para implementar todo desenvolvimento backend de uma plataforma que deve permitir ao usuário montar playlists com links para seus vídeos preferidos, separados por categorias

## Começando

Para executar o projeto, será necessário instalar os seguintes programas:

- [JDK 11: Necessário para executar o projeto Java](https://www.oracle.com/br/java/technologies/javase-jdk11-downloads.html)
- [Maven 3.6.3: Necessário para realizar o build do projeto Java](https://maven.apache.org/docs/3.6.3/release-notes.html)
- O desenvolvimento foi feito no IntelliJ, mas pode usar a IDE que você está acostumado a utilizar;
- Banco de dados PostgreSQL;

## Desenvolvimento

Para iniciar o desenvolvimento, é necessário clonar o projeto do GitHub num diretório de sua preferência:

```shell
cd "diretorio de sua preferencia"
git clone https://github.com/ERodrigues/aluraVideos
```

### Construção

Para construir o projeto com o Maven, executar os comando abaixo:

```shell
mvn clean install
```

O comando irá baixar todas as dependências do projeto e criar um diretório *target* com os artefatos construídos, que incluem o arquivo jar do projeto. Além disso, serão executados os testes unitários, e se algum falhar, o Maven exibirá essa informação no console.

## Features

O projeto pode ser usado como modelo para iniciar o desenvolvimento de um projeto Java usando o Maven. Ele também demonstra de forma prática como configurar o Maven em um projeto Java.
Além de servir como exemplo de um projeto Java com Maven, o projeto ainda possui as seguintes features: 
- Cadastro de vídeos; 
- Alteração de um registro de vídeo; 
- Exclusão de um vídeo do banco de dados; 
- Listagem de todos os vídeos cadastrados;
