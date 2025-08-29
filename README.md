# VÖBB – Library System Management

The **VÖBB Library Management System** is a full-stack web application designed to simulate the operations of a
large-scale public library network, inspired
by [Association of Public Libraries in Berlin (VÖBB).](https://www.voebb.de/aDISWeb/app?service=direct/0/Home/$DirectLink&sp=SPROD00)
The platform provides an intuitive and robust interface for both users and administrators, streamlining essential
workflows such as book and media cataloging, item tracking, user reservations, and borrowing management. It features
role-based access control, supporting three distinct user roles: Admin, Registered User, and Guest, each with tailored
access and functionality.

**Quick Login:** Use the following credentials for easy access –

```markdown 
| Role   | Username (Email)         | Password  |
|--------|--------------------------|-----------|
| Admin  | admin1@example.com       | 12345678  |
| Client | client2@example.com      | 12345678  |
```

## Table of Contents

1. [Feature Set](#feature-set)
2. [Technologies Used](#tools-&-technologies-used)
3. [System Architecture Overview](#system-architecture-overview)
4. [Setup and Installation](#setup-and-installation)
5. [Documentation](#documentation)
6. [Developer Team](#project-developer-team)
7. [Contributing](#contributing)

## Feature Set

### Guest Features

- **Browse & Search Product Catalog** – Search by title, media type, or associated library using full-text search and filter options.
- **View Product Availability & Locations** – See real-time availability status for each item and the libraries where it can be accessed.

### User Features

- **Registration/Login** – Secure authentication via email or phone number.

- **Profile Management** – Update password, contact information, and personal details.

- **Reservation System** – Place holds on items for up to 3 days; reservations auto-expire if not collected within the hold period.

- **Borrowing & Reservation History** – Access a complete record of past and active reservations and borrowings.

- **Due-Date Reminders** – Receive in-app notifications when due dates are approaching.

- **Includes All Guest Features** – Users retain access to all search and viewing capabilities available to guests.

### **Admin Features**
Administrators manage all system content, users, and operations with **full CRUD control** and **advanced filtering** for precision oversight.

- **Core Management** – Create, update, or remove:
    - **Users** – Accounts, roles, and status.
    - **Products** – Books, eBooks, DVDs, board games, metadata.
    - **Items** – Physical copies with availability status.
    - **Libraries** – Branch details and locations.
    - **Reservations** – Holds and expiry.
    - **Borrowings** – Loan records and return status.

- **Targeted Search & Filtering** – Quickly locate records by key attributes:
    - **Users** – ID, email, name, status.
    - **Products** – ID, title, type.
    - **Items** – ID, type, status, library.
    - **Libraries** – ID, name, district.
    - **Reservations** – User ID, item ID, library.
    - **Borrowings** – User ID, item ID, library, loan status.


## Tools & Technologies Used

### Backend : ![Java](https://img.shields.io/badge/Java-ED8B00?logo=java&logoColor=white) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=white) ![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?logo=springsecurity&logoColor=white)

### Frontend: ![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?logo=thymeleaf&logoColor=white) ![HTML5](https://img.shields.io/badge/HTML5-E34F26?logo=html5&logoColor=white) ![CSS3](https://img.shields.io/badge/CSS3-1572B6?logo=css3&logoColor=white) ![Bootstrap](https://img.shields.io/badge/Bootstrap-7952B3?logo=bootstrap&logoColor=white) ![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?logo=javascript&logoColor=black)

### Database: ![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?logo=postgresql&logoColor=white)

### ORM: ![Hibernate](https://img.shields.io/badge/Hibernate-59666C?logo=hibernate&logoColor=white)

### Deployment ![Docker](https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=white) ![Render](https://img.shields.io/badge/Render-000000?logo=render&logoColor=white)

### Build Tool ![Maven](https://img.shields.io/badge/Maven-C71A36?logo=apachemaven&logoColor=white)

## System Architecture Overview

- Follows the **MVC architecture**: Model, View, Controller
- Structured into **MVC, Service, and Persistence** layers for clear responsibility separation
- Implements the **Controller-Service-Repository** pattern to promote clean, modular code
- Uses **JPA entities** with relational integrity for robust database modeling
- Integrates **role-based access control** via Spring Security
- Applies **data validation** using Spring's built-in validation annotations
- Designed for **scalability**, **maintainability**, and **testability** across all layers

### Database Design:

The database is built with a fully normalized schema in PostgreSQL, using foreign keys, constraints, and advanced
features like GIN indexes, views, and scheduled jobs to support robust library operations and full-text search.
Read more about Database Design [here.](https://github.com/nat-laz/voebb_library_management_system)

View the full [**Database Schema Diagram**](https://dbdiagram.io/d/VOEEB-FINAL-PROJECT-68135f451ca52373f517834b) on dbdiagram.io.


## Setup and Installation

### 1. Clone the Repository

```bash
git git@github.com:bruch-alex/voebb-final-project.git
cd voebb-final-project
```

### 2. Create a PostgreSQL Database 

Make sure PostgreSQL is installed and the service is running.

<details>
<summary><strong>Linux / macOS Setup</strong></summary>

1. **Connect to PostgreSQL**  

```bash
psql -U postgres
```

2. **Create the database**

```sql
   CREATE DATABASE voebb_library_db;
   ```

3. **(Optional) Create a dedicated user**

```sql
   CREATE USER voebb_user WITH PASSWORD 'your_password';
   GRANT ALL PRIVILEGES ON DATABASE voebb_library_db TO voebb_user;
   ```

</details>

<details>
<summary><strong>Windows Setup</strong></summary>

1. **Connect to PostgreSQL**  

```bash
psql -U postgres -h localhost -p 5432
```

2. **Create the database**

```sql
   CREATE DATABASE voebb_library_db;
   ```

3. **(Optional) Create a dedicated user**

```sql
   CREATE USER voebb_user WITH PASSWORD 'your_password';
   GRANT ALL PRIVILEGES ON DATABASE voebb_library_db TO voebb_user;
   ```

</details>

### 3. Configure Environment Variables

Create a `.env` file in the root of the project directory and fill it with the required environment variables for
database connection.

Example `.env` file:

```env
DB_URL=jdbc:postgresql://localhost:5432/voebb_library_db || your_db_name
DB_USERNAME=your-db-username
DB_PASSWORD=your-db-password
```

### 4. Run the Application

Build and start the app:

```cli
mvn spring-boot:run
```

The app will be running at `http://localhost:8080`.



## Documentation [WIP]

The full endpoint reference is organized in the [`docs/`](./docs) folder:

- **[api-endpoints.md](src/main/resources/docs/api-endpoints.md)** — REST Endpoints for AJAX calls, used internally by the admin panel to dynamically fetch or create data (creators/roles) without reloading the page.
- **[admin-endpoints.md](.src/main/resources/docs/admin-endpoints.md)** — Admin panel routes for managing users, products, items, libraries, borrowings, and reservations.
- **[public-endpoints.md](src/main/resources//docs/public-endpoints.md)** — Public site routes (home, search, product details, user profile).


## Project Developer Team

[Mitali Soti](https://github.com/mitalisoti), [Alex Bruch](https://github.com/bruch-alex), [Natalie Lazarev](https://github.com/nat-laz), [Marc Stiehm](https://github.com/Rikupp17)

## Contributing

We welcome contributions to this project! Whether it's bug fixes, feature additions, or documentation improvements, your
help is appreciated.
