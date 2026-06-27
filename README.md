# 🎵 Playlist API

A RESTful API built with **Java 21**, **Spring Boot 4.1.0**, and **PostgreSQL** that allows users to create playlists, add songs to them, and fetch their playlists.

---

## 📋 Table of Contents

- [AI Usage](#ai-usage)
- [Technologies Used](#technologies-used)
- [Prerequisites](#prerequisites)
- [Setup Instructions](#setup-instructions)
    - [macOS Setup](#macos-setup)
    - [Windows Setup](#windows-setup)
- [Running the Application](#running-the-application)
- [API Endpoints](#api-endpoints)
- [Testing with curl](#testing-with-curl)
- [Database Schema](#database-schema)
- [Design Patterns & SOLID Principles](#design-patterns--solid-principles)
- [Project Structure](#project-structure)
- [Author](#author)
- [Troubleshooting](#trouble-shooting)

---
## 🤖 AI Usage 

This project was developed with the assistance of an AI assistant (Deepseek).
The full conversation history can be found here:

🔗 **https://chat.deepseek.com/share/ub6k49as58yzama916**  

---

## 🛠️ Technologies Used

| Technology | Version | Purpose |
| :--- | :--- | :--- |
| Java | 21 | Programming language |
| Spring Boot | 4.1.0 | Framework for building the REST API |
| Spring Data JPA | - | ORM and database access |
| PostgreSQL | 16+ | Relational database |
| Maven | 3.9.x | Dependency management and build tool |
| Lombok | - | Reducing boilerplate code |

---

## 📦 Prerequisites

Before running this project, ensure you have the following installed:

### 1. Java 21

**Check if Java is installed:**
```bash
java -version
# Should output: openjdk version "21" 
```

**Download:** [Adoptium Temurin 21](https://adoptium.net/)

---

### 2. PostgreSQL

This project requires a running PostgreSQL database.

#### macOS (via Postgres.app)
**Download:** [https://postgresapp.com/](https://postgresapp.com/)

After installation:
- Open Postgres.app and click **"Initialize"**.
- The server will run on `localhost:5432`.
- Your Mac username will be the default database user with no password.

#### Windows (via Official Installer)
**Download:** [https://www.postgresql.org/download/windows/](https://www.postgresql.org/download/windows/)

During installation:
- Set a password for the `postgres` user (e.g., `postgres` or `password`).
- Remember this password — you'll need it in the configuration.
- Keep the default port: `5432`.

---

### 3. IntelliJ IDEA (or any IDE)

**Download:** [https://www.jetbrains.com/idea/download/](https://www.jetbrains.com/idea/download/)

The **Community Edition** is free and works perfectly.

---

### 4. Maven (Optional — IntelliJ includes it)

If you want to run from the terminal:
```bash
mvn -v
# Should output: Apache Maven 3.9.x
```

---

## ⚙️ Setup Instructions

### Step 1: Clone the Repository
```bash
git clone <your-repo-url>
cd playlists
```

---

### Step 2: Configure the Database Connection

Open the file:
```
src/main/resources/application.properties
```

#### For macOS (Postgres.app default):
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=YOUR_MAC_USERNAME   # <-- Run `whoami` in terminal to find this
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=true

spring.datasource.driver-class-name=org.postgresql.Driver
```

#### For Windows (PostgreSQL official installer):
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=postgres   # <-- Use the password you set during installation

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=true

spring.datasource.driver-class-name=org.postgresql.Driver
```


---

### Step 3: Build the Project

Open the project in IntelliJ and wait for Maven to download all dependencies (this takes 1–2 minutes).

Alternatively, build from the terminal:
```bash
mvn clean install
```

---

### Step 4: Run the Application

#### In IntelliJ:
- Navigate to `src/main/java/com/razan/playlists/PlaylistsApplication.java`
- Click the green ▶️ "Run" button next to the `main()` method

#### From the terminal:
```bash
mvn spring-boot:run
```

**You should see:**
```
Started PlaylistsApplication in X.XXX seconds
5 sample songs loaded into the Songs Library!
```

---

## 📡 API Endpoints

| Method | Endpoint | Description | Request Body | Response |
| :--- | :--- | :--- | :--- | :--- |
| `POST` | `/api/playlists` | Create a new playlist | `{"name": "My Playlist", "userId": 1}` | The created playlist with an `id` |
| `POST` | `/api/playlists/{id}/songs` | Add a song to a playlist | `{"songId": 1}` | The updated playlist with the song added |
| `GET` | `/api/playlists?userId={id}` | Fetch all playlists for a user | (Query parameter) | A list of playlists with their songs |

---

## 🧪 Testing with `curl`

You can test the API directly from your terminal using `curl`.

### 1. Check Available Songs (Database)
First, check which songs are pre-loaded:
```bash
psql postgres -c "SELECT id, title, artist FROM songs;"
```

**Sample Output:**
```
 id |       title        |     artist      
----+--------------------+-----------------
  1 |     Risk it all    |    Bruno Mars
  2 |      Tears dry     |   Amy Winehouse
  3 |        Wayah       |     Amr Diab
  4 |       Mashaier     |     Sherine
  5 |       Cardigan     |   Taylor Swift
   
```

---

### 2. Create a Playlist
```bash
curl -X POST http://localhost:8080/api/playlists \
  -H "Content-Type: application/json" \
  -d '{"name": "My Playlist", "userId": 1}'
```

**Expected Response:**
```json
{"id":1,"name":"My Playlist","userId":1,"songs":[]}
```

---

### 3. Add a Song to the Playlist
*(Replace `1` with your playlist ID from Step 2)*

```bash
curl -X POST http://localhost:8080/api/playlists/1/songs \
  -H "Content-Type: application/json" \
  -d '{"songId": 1}'
```

**Expected Response:**
```json
{"id":1,"name":"My Playlist","userId":1,"songs":[{"id":1,"title":"Risk it all","artist":"Bruno Mars"}]}
```

---

### 4. Get All Playlists for a User
```bash
curl "http://localhost:8080/api/playlists?userId=1"
```

**Expected Response:**
```json
[
  {
    "id": 1,
    "name": "My Playlist",
    "userId": 1,
    "songs": [
      {"id": 1, "title": "Risk it all", "artist": "Bruno Mars"}
    ]
  }
]
```

---

## 🗄️ Database Schema

The database consists of three tables:

### `songs`
| Column | Type | Description |
| :--- | :--- | :--- |
| `id` | `BIGSERIAL` | Primary key, auto-incrementing |
| `title` | `VARCHAR` | Song title (not null) |
| `artist` | `VARCHAR` | Artist name (not null) |

### `playlists`
| Column | Type | Description |
| :--- | :--- | :--- |
| `id` | `BIGSERIAL` | Primary key, auto-incrementing |
| `name` | `VARCHAR` | Playlist name (not null) |
| `user_id` | `BIGINT` | ID of the playlist owner (not null) |

### `playlist_songs` (Join Table)
| Column | Type | Description |
| :--- | :--- | :--- |
| `playlist_id` | `BIGINT` | Foreign key to `playlists(id)` |
| `song_id` | `BIGINT` | Foreign key to `songs(id)` |

**Relationship:** Many-to-Many between `playlists` and `songs`.

---

## 🧩 Design Patterns & SOLID Principles

### Design Patterns Used

| Pattern | Where | Why |
| :--- | :--- | :--- |
| **DTO** | `api/` package | Decouples the API from the database entities. Prevents exposing internal data structures. |
| **Repository** | `repo/` package | Abstracts the data layer. The service layer doesn't know about the database implementation. |
| **Service Layer** | `core/` package | Separates business logic from HTTP handling. Controllers are "thin" and only handle requests/responses. |
| **Builder** | Lombok `@Builder` on entities | Clean object creation without long constructors. Makes code more readable. |
| **Dependency Injection** | Constructor injection in services | Follows Inversion of Control (IoC). Makes dependencies explicit and easy to mock for testing. |

### SOLID Principles Applied

| Principle | How it's Applied |
| :--- | :--- |
| **S**ingle Responsibility | Each class has one job: `PlaylistControl` → HTTP, `PlaylistManagerImp` → Business Logic, `PlaylistRepo` → Data Access. |
| **O**pen/Closed | The `PlaylistManager` interface allows adding new implementations without modifying existing code. |
| **L**iskov Substitution | Controllers depend on abstractions (`PlaylistManager` interface), not concrete implementations. |
| **I**nterface Segregation | Repository interfaces are lean — only extend `JpaRepository` with the needed methods. |
| **D**ependency Inversion | High-level modules (`PlaylistControl`) depend on abstractions (`PlaylistManager`), not low-level modules (`PlaylistManagerImp`). |

---

## 📁 Project Structure

```
playlists/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com.razan.playlists/
│   │   │       ├── api/                    # DTOs (Data Transfer Objects)
│   │   │       │   ├── AddSongRequest.java
│   │   │       │   ├── CreatePlaylistRequest.java
│   │   │       │   └── PlaylistResponse.java
│   │   │       ├── core/                   # Service Layer
│   │   │       │   ├── PlaylistManager.java      (Interface)
│   │   │       │   └── PlaylistManagerImp.java   (Implementation)
│   │   │       ├── entity/                 # JPA Entities
│   │   │       │   ├── Playlist.java
│   │   │       │   └── Song.java
│   │   │       ├── repo/                   # Repository Interfaces
│   │   │       │   ├── PlaylistRepo.java
│   │   │       │   └── SongRepo.java
│   │   │       ├── web/                    # REST Controllers
│   │   │       │   └── PlaylistControl.java
│   │   │       ├── PlaylistsApplication.java  # Main Spring Boot class
│   │   │       └── SongsLibrary.java          # Data seeder (pre-loads songs)
│   │   └── resources/
│   │       └── application.properties      # Database configuration
│   └── test/                              # Unit tests (empty for now)
├── .gitignore
├── pom.xml                                # Maven dependencies
└── README.md                              # This file
```

---

## 👤 Author

**Razan Mohamed Ismail Ahmed**

- Github: 

- Email: razanmohamed2006@gmail.com

---

## 🎯 Troubleshooting

### Error: `FATAL: password authentication failed`
**Cause:** Wrong database credentials in `application.properties`.

**Fix:**
- Double-check `spring.datasource.username` and `spring.datasource.password`.
- On macOS (Postgres.app), username is your Mac username, password is blank.
- On Windows (official installer), username is `postgres`, password is what you set during installation.

---

### Error: `Connection refused`
**Cause:** PostgreSQL is not running.

**Fix:**
- **macOS:** Open Postgres.app and click "Initialize".
- **Windows:** Open Services → PostgreSQL → Start.

---

### Error: `No default constructor for entity`
**Cause:** Lombok annotations are not being processed.

**Fix:**
- In IntelliJ: **Settings → Build, Execution, Deployment → Compiler → Annotation Processors** → Enable annotation processing.
- Or install the Lombok plugin in IntelliJ.