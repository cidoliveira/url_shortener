# URL Shortener

URL-shortening application built with Java, Spring Boot, Angular, and PostgreSQL. The interface submits a URL to the API and displays a short link. Opening that link redirects the browser to the saved address.

The backend generates four-letter lowercase codes and stores the original URL and its code in PostgreSQL. Addresses without an `http://` or `https://` prefix receive `https://` automatically.

## Technology

- Java 17 and Spring Boot 4.1.1
- Spring Web MVC, Spring Data JPA, and Hibernate
- PostgreSQL
- MapStruct 1.6.3, Lombok, and Apache Commons Text
- Angular 21, TypeScript 5.9, and Tailwind CSS 4
- Maven and npm

## Running locally

Install JDK 17, Maven 3.9.x, Node.js 24.x, and PostgreSQL. Configure `JAVA_HOME` to use JDK 17 and make `mvn`, `node`, and `npm` available in your shell.

### Database

Create a database on your PostgreSQL server:

```sql
CREATE DATABASE url_shortener;
```

Use an existing PostgreSQL account with permission to create and update tables in this database. Supply that account's actual username and password in the commands below.

Hibernate creates or updates the `tb_links` table on startup through `ddl-auto=update`. The project does not use versioned database migrations.

### Backend

Run these commands from the repository root.

Windows (PowerShell):

```powershell
$env:SPRING_DATASOURCE_URL = 'jdbc:postgresql://localhost:5432/url_shortener'
$env:SPRING_DATASOURCE_USERNAME = '<your-database-username>'
$env:SPRING_DATASOURCE_PASSWORD = '<your-database-password>'

mvn -f backend/pom.xml spring-boot:run
```

Linux and macOS:

```sh
export SPRING_DATASOURCE_URL='jdbc:postgresql://localhost:5432/url_shortener'
export SPRING_DATASOURCE_USERNAME='<your-database-username>'
export SPRING_DATASOURCE_PASSWORD='<your-database-password>'

mvn -f backend/pom.xml spring-boot:run
```

The API runs at `http://localhost:8080` by default. These instructions use an installed Maven distribution. The Unix wrapper is also available inside `backend/`; the Windows wrapper at the repository root is not alongside its `.mvn` configuration.

### Frontend

Open a second terminal at the repository root:

```sh
cd frontend
npm ci
npm start
```

Open `http://localhost:4200`, enter a URL, and select **Shorten Url**. The generated link points to the backend redirect endpoint.

## Configuration

Backend settings are defined in [`application.properties`](backend/src/main/resources/application.properties). Spring Boot's environment variables override those settings:

| Variable | Purpose | Default in the project |
|----------|---------|------------------------|
| `SPRING_DATASOURCE_URL` | PostgreSQL JDBC connection URL | `jdbc:postgresql://localhost:5432/url_shortener` |
| `SPRING_DATASOURCE_USERNAME` | PostgreSQL account | `postgres` |
| `SPRING_DATASOURCE_PASSWORD` | PostgreSQL password | Read from `postgres_password` when not overridden |

If using the configured defaults, export `postgres_password` instead of `SPRING_DATASOURCE_PASSWORD`. A `.env` file is not loaded automatically by the application.

The frontend API address and generated-link base URL are set in [`app.ts`](frontend/src/app/app.ts). Both currently use `http://localhost:8080`. The backend allows browser requests from `http://localhost:4200` through its controller's CORS configuration. Update these values if changing the local addresses.

## Endpoints

| Method | Path | Behavior |
|--------|------|----------|
| `POST` | `/urlshortener/post` | Save a URL and return its generated code with `201 Created` |
| `GET` | `/urlshortener/{code}` | Redirect with `302 Found`, or return `404 Not Found` when the code does not exist |

Request body for `POST /urlshortener/post`:

```json
{
  "receivedUrl": "example.com/article"
}
```

Example response; the ID and code are generated:

```json
{
  "id": 1,
  "receivedUrl": "https://example.com/article",
  "shortenedUrl": "abcd"
}
```

`shortenedUrl` contains the code, not a complete URL. In this example, opening `http://localhost:8080/urlshortener/abcd` returns a redirect with `Location: https://example.com/article`.

## Tests and build

Run backend commands from the repository root, with the database variables set and PostgreSQL available:

```sh
mvn -f backend/pom.xml test
mvn -f backend/pom.xml clean package
```

The backend suite contains one application-context test and requires a database connection. To package the application without running tests:

```sh
mvn -f backend/pom.xml -DskipTests package
```

The executable JAR is written to `backend/target/url_shortener-0.0.1-SNAPSHOT.jar`:

```sh
java -jar backend/target/url_shortener-0.0.1-SNAPSHOT.jar
```

From `frontend/`:

```sh
npm test -- --watch=false
npm run build
```

The frontend build is written to `frontend/dist/frontend/`. Its two component tests still include a starter-title assertion expecting `Hello, frontend`; that assertion currently fails because the page title is `Url Shortener`. There are no end-to-end tests.

## Current behavior

- Codes have a database uniqueness constraint, but generation does not retry collisions.
- URL normalization adds a scheme; it does not validate the URL or check whether the destination exists. Empty or malformed requests have no dedicated validation response.
- The interface displays the returned link, without loading or error feedback.
- Links have no expiration, custom aliases, or click tracking.

## Project structure

```text
backend/
  pom.xml
  src/main/java/com/cidoliveira/url_shortener/
    UrlShortenerApplication.java
    UrlShortener/              Controller, service, repository, entity, DTO, and mapper
  src/main/resources/
    application.properties
  src/test/                    Application-context test
frontend/
  src/app/                     Angular component, template, styles, and tests
  package.json
  angular.json
```
