# OAuth2AuthorizationServer
 OAuth2 Authorization Server with Spring Boot + Security.
 
## Required tools
1. Docker Desktop

2. pgAdmin4

## Setting up the project
1. **Run 'config-loopback.cmd'** as Administrator.

2. Open a Terminal in project's path and **run 'docker-compose up'**.

3. **Open pgAdmin4 and create a new server** with the following parameters:
   - **Name**: OAuth2AuthorizationServer
   - **Hostname/address**: localhost
   - **Port**: 5442
   - **Username**: postgres
   - **Password**: itk2022

4. Once created, **right-click on it and select 'Query Tool'**.

5. Select "Open File" and **load the 'create-tables.sql' script**.

6. Run it and **refresh the database to check success**.

7. Open your browser in private and **visit 'localhost:8080'**.

8. **A login page will appear**. The are 3 users with different roles:

   - **ROLE_ADMIN**:
     - **Username**: 'carlos.reyes@theksquaregroup.com'
     - **Password**: 'admin'

   - **ROLE_MANAGER**:
     - **Username**: 'guillermo.ceme@theksquaregroup.com'
     - **Password**: 'manager'
     
   - **ROLE_USER**:
     - **Username**: 'julio.vargas@theksquaregroup.com'
     - **Password**: 'user'

9. After a successful login, **you'll be redirected to '/'**, where you can see your user and role.

10. **Access '/docs' to review the available endpoints** with their expected inputs and outputs.

## Relationships between endpoints, roles and methods

1. **GET**:
   - **'/movies', '/movies/{id}'** and **'/docs'**:
     - ROLE_ADMIN
     - ROLE_MANAGER
     - ROLE_USER

2. **POST**:
   - **'/movies/add'**:
     - ROLE_ADMIN
     - ROLE_MANAGER

3. **PATCH**:
   - **'/movies/edit/{id}'**:
     - ROLE_ADMIN
     - ROLE_MANAGER

4. **DELETE**:
   - **'/movies/delete/{id}'**:
     - ROLE_ADMIN

## Setup and demonstration

The following video shows **how the project should be setup, as well as a brief demonstration of it**.

[![Alt text](https://img.youtube.com/vi/yHmr7AP1U4E/0.jpg)](https://www.youtube.com/watch?v=yHmr7AP1U4E)
