# User Subscription API

## Overview
This API allows users to manage their subscriptions. Users can create accounts, subscribe to plans, upgrade subscriptions, and view their subscription history.

## Requirements
- Spring Web
- Lombok
- PostgreSQL Driver
- Spring JPA

## Database Schema
### Users Table
```
Column  |          Type          | Collation | Nullable |             Default              
--------+------------------------+-----------+----------+----------------------------------
 id     | bigint                 |           | not null | generated by default as identity
 email  | character varying(255) |           | not null | 
 name   | character varying(255) |           | not null | 
Indexes:
    "users_pkey" PRIMARY KEY, btree (id)
    "uk6dotkott2kjsp8vw4d0m25fb7" UNIQUE CONSTRAINT, btree (email)
Referenced by:
    TABLE "user_subscriptions" CONSTRAINT "fk3l40lbyji8kj5xoc20ycwsc8g" FOREIGN KEY (user_id) REFERENCES users(id)
```

### User Subscriptions Table
```
Column      |              Type              | Collation | Nullable |             Default              
-----------------+--------------------------------+-----------+----------+----------------------------------
 id              | bigint                         |           | not null | generated by default as identity
 end_date        | timestamp(6) without time zone |           | not null | 
 start_date      | timestamp(6) without time zone |           | not null | 
 status          | character varying(255)         |           | not null | 
 subscription_id | bigint                         |           | not null | 
 user_id         | bigint                         |           | not null | 
Indexes:
    "user_subscriptions_pkey" PRIMARY KEY, btree (id)
Foreign-key constraints:
    "fk3l40lbyji8kj5xoc20ycwsc8g" FOREIGN KEY (user_id) REFERENCES users(id)
    "fkcrkxok09b5ucoqbd9gpuqy2kb" FOREIGN KEY (subscription_id) REFERENCES subscriptions(id)
```

### Subscriptions Table
```
Column |          Type          | Collation | Nullable |             Default              
--------+------------------------+-----------+----------+----------------------------------
 id     | bigint                 |           | not null | generated by default as identity
 name   | character varying(255) |           |          | 
 type   | character varying(255) |           |          | 
Indexes:
    "subscriptions_pkey" PRIMARY KEY, btree (id)
Check constraints:
    "subscriptions_type_check" CHECK (type::text = ANY (ARRAY['SILVER'::character varying, 'GOLD'::character varying, 'PLATINUM'::character varying]::text[]))
Referenced by:
    TABLE "user_subscriptions" CONSTRAINT "fkcrkxok09b5ucoqbd9gpuqy2kb" FOREIGN KEY (subscription_id) REFERENCES subscriptions(id)
```

## Base URL
```
http://localhost:8080
```
Replace `localhost:8080` with your actual server address.

## Running the Application
Run the Spring Boot application using:
```bash
./mvnw spring-boot:run
```
Or if using Gradle:
```bash
./gradlew bootRun
```

---

## User Controller Endpoints

### 1. Create a User
```bash
curl -X POST http://localhost:8080/users/create \
     -H "Content-Type: application/json" \
     -d '{
         "username": "parth bagda",
         "email": "parthbagda94@gmail.com",

     }'
```

### 2. Get All Users
```bash
curl -X GET http://localhost:8080/users
```

### 3. Get User by ID
```bash
curl -X GET http://localhost:8080/users/1
```

### 4. Delete User
```bash
curl -X DELETE http://localhost:8080/users/1
```

### 5. Get Current Active Plan for User
```bash
curl -X GET http://localhost:8080/users/activeplan/1
```

---

## Subscription Controller Endpoints

### 6. Get All Subscriptions
```bash
curl -X GET http://localhost:8080/subscriptions
```

---

## User Subscription Controller Endpoints

### 7. Subscribe User to a Subscription Plan
```bash
curl -X POST http://localhost:8080/subscription/1/subscribe/2
```

### 8. Upgrade User's Subscription Plan
```bash
curl -X PUT http://localhost:8080/subscription/1/upgrade/3
```

### 9. Unsubscribe User
```bash
curl -X DELETE http://localhost:8080/subscription/1/unsubscribe
```

### 10. Get User's Subscription History
```bash
curl -X GET http://localhost:8080/subscription/1/history
```

---
