# Tournament Manager Application

A Spring Boot web application for managing tournaments, players, and matches with full CRUD functionality and a simple user-friendly interface.


## Tech Stack

- **Java 17**
- **Spring Boot 3**
- **Thymeleaf**
- **Spring Data JPA** + **Hibernate**
- **H2**


## Folder Structure
- controllers/ – Spring MVC controllers
- services/ – Business logic
- repositories/ – JPA repositories
- model/ – Domain models (Tournament, Player, Match, etc.)
- templates/ – Thymeleaf views
- static/ – Static assets (CSS)


## Features

- View, create, and manage **tournaments**
  
  ![tournaments](https://github.com/user-attachments/assets/b3ba0e4c-b370-4e45-a1ad-bd101675c427)

- Add and manage **players** assigned to specific tournaments

  ![players](https://github.com/user-attachments/assets/827e6782-3122-49ba-a205-c7d96fbcf24f)  

- Automatically **generate matches** based on players in a tournament

  ![players2](https://github.com/user-attachments/assets/2c389b76-bb07-4261-a77e-9f90bc380855)

- **Update match winners**, track **standings**, and view **summary pages**

  ![matches](https://github.com/user-attachments/assets/44881ebe-97af-48dc-8cd2-77bb1557bd44)

  ![summary](https://github.com/user-attachments/assets/b761dde0-474b-403b-91c2-c8be92fd2499)

- Search and paginate through players before assigning
- Handle restricted actions (e.g., can't delete players after matches are generated)


## Known Limitations

- Currently no authentication/authorization layer.
- No front-end JavaScript framework used (pure Thymeleaf).


## Future Enhancements

- Implement user accounts and login - Replace manual player additions with a user authentication system allowing account creation, login, and player self-management.
- Implement more tournament formats - Extend the system beyond Round Robin by introducing the Swiss tournament format as the next supported structure.
- Migrate from H2 to PostgreSQL – Replace the in-memory H2 database with a persistent PostgreSQL setup for production-ready data storage.
- Replace Thymeleaf with a modern frontend framework (likely React) – Decouple the frontend from Spring MVC and implement a more dynamic and scalable UI using React or another SPA framework.
