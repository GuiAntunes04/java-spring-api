# 📺 Series Catalog API

A console-driven Java + Spring Boot application built as part of Alura's course on **Lambdas, Streams, and the Spring Framework**. It consumes the [OMDb API](https://www.omdbapi.com/) to search for TV series and episodes, persists them to a PostgreSQL database via Spring Data JPA, and lets the user query the local catalog through an interactive terminal menu.

## ✨ Features

- 🔍 Search TV series by title through the OMDb API and save them to the database
- 📅 Fetch and store all episodes of a series, season by season
- 📋 List every series saved in the catalog, sorted by genre
- 🔎 Query the local catalog by:
  - Title
  - Actor
  - Minimum rating
  - Genre/category
- 🍿 Find short, highly-rated series ("weekend binge" list)
- 🏆 Show the top 5 best-rated episodes of a given series
- Custom JPQL queries via Spring Data JPA (`@Query`)
- JSON deserialization of the OMDb API responses using Jackson (`records` + `@JsonAlias`)

## 🏗️ Architecture

```
src/main/java/br/com/alura/exerciciosSpring/
├── ExerciciosSpringApplication.java   # Spring Boot entry point (CommandLineRunner)
├── main/
│   └── Main.java                      # Console menu / application flow
├── dto/                                # Records mapping raw OMDb JSON responses
│   ├── SerieDto.java
│   └── EpisodeDto.java
├── model/                              # JPA entities and domain data
│   ├── SerieData.java                  # @Entity - series table
│   ├── EpisodeData.java                # @Entity - episodes table
│   ├── SeasonData.java                 # Intermediate record used during API parsing
│   └── Category.java                   # Genre enum (EN <-> PT mapping)
├── repository/
│   └── SerieRepository.java            # Spring Data JPA repository + custom queries
└── service/
    ├── GetApi.java                     # HTTP client for the OMDb API
    ├── ConvertData.java                # JSON -> Object conversion (Jackson)
    └── IConvertData.java               # Conversion contract
```

The project follows a simple layered structure:
- **DTOs** decouple the external OMDb API format from the internal domain model
- **Models** are JPA entities persisted to PostgreSQL (`SerieData` 1:N `EpisodeData`)
- **Services** isolate HTTP requests and JSON parsing from business logic
- **Repository** leverages Spring Data JPA derived queries and custom JPQL for more complex lookups

## 🛠️ Tech Stack

- Java 25
- Spring Boot 4.0.5 (`spring-boot-starter`, `spring-boot-starter-data-jpa`)
- PostgreSQL (`org.postgresql` driver)
- Jackson (`jackson-databind`) for JSON parsing
- Maven (with wrapper `mvnw` / `mvnw.cmd`)
- [OMDb API](https://www.omdbapi.com/) as the external data source

## 🚀 Getting Started

### Prerequisites

- JDK 25
- PostgreSQL running locally or accessible remotely
- An [OMDb API key](https://www.omdbapi.com/apikey.aspx) (free tier available)

### 1. Clone the repository

```bash
git clone https://github.com/GuiAntunes04/java-spring-api.git
cd java-spring-api
```

### 2. Configure environment variables

The application reads database credentials from environment variables (see `src/main/resources/application.properties`):

```bash
export DB_HOST=localhost:5432/series_db
export DB_USER=your_postgres_user
export DB_PASSWORD=your_postgres_password
```

> Create the `series_db` database beforehand — Hibernate will create/update the tables automatically (`spring.jpa.hibernate.ddl-auto=update`).

### 3. Set your OMDb API key

The API key is currently defined as a constant in `Main.java` (`API_KEY`). Replace it with your own key from OMDb before running the project.

### 4. Run the application

```bash
./mvnw spring-boot:run
```

On startup, an interactive menu will appear in the console:

```
Escolha uma opção:

1 - Buscar séries
2 - Buscar episódios por série
3 - Listar séries buscadas
4 - Buscar série por título
5 - Buscar série pelo ator
6 - Buscar série pela avaliação
7 - Buscar série pela categoria
8 - Buscar séries curtas e bem avaliadas (Para maratonar no fim de semana)
9 - Top episódios de uma série

0 - Sair
```

## 🧪 Tests

A base test class (`ExerciciosSpringApplicationTests`) is included via `spring-boot-starter-test`. Run it with:

```bash
./mvnw test
```

## 📌 Notes

- Series genres are mapped between English (as returned by OMDb) and Portuguese (as typed by the user) through the `Category` enum.
- Numeric/date fields returned by OMDb that come as `"N/A"` or malformed values are safely handled with try/catch fallbacks when building `EpisodeData`.

## 👤 Author

**Guilherme Henrique Antunes**
[GitHub](https://github.com/GuiAntunes04)

## 📄 License

This project was built for educational purposes as part of Alura's Java course.
