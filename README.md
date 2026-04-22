# 🏥 CareSync Health Portal – Backend

## 📌 Overview

This is the backend service for the **CareSync Health Portal**, built using **Spring Boot**. It provides secure APIs for:

* Authentication (JWT-based)
* Doctor management
* Slot generation & scheduling
* Appointment booking system

---

## 🧰 Tech Stack

* Java 17+
* Spring Boot
* Spring Security (JWT Authentication)
* Spring Data JPA (Hibernate)
* MySQL Database

---

## 📁 Project Structure

```
backend/
│── controller/
│── service/
│── repository/
│── model/
│── dto/
│── config/
│── security/
│
└── application.properties
```

---

## 🚀 Features

### 🔐 Authentication

* JWT-based login/register
* Role-based access (USER / DOCTOR)

---

### 👨‍⚕️ Doctor Module

* Register doctor
* Search doctors
* View all doctors

---

### 📅 Slot Management

* Auto slot generation
* Morning & afternoon sessions
* Prevent duplicate slots

---

### 🗓️ Appointment Booking

* Book slot
* Prevent double booking
* Role-based access control

---

## ⚙️ Slot Generation Logic

Slots are generated:

* Daily using scheduled task
* For next 7 days
* Time slots:

    * 10:00 → 13:00
    * 14:00 → 16:00
* Interval: 30 minutes

---

## ⚙️ Setup Instructions

### 1. Clone project

```
git clone <your-repo-link>
```

### 2. Configure Database

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/healthportal
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
```

---

### 3. Run Application

```
mvn spring-boot:run
```

Server runs on:

```
http://localhost:2000
```

---

## 🔐 Security Configuration

* JWT Filter implemented
* Stateless session management
* Role-based endpoints:

    * USER → Book appointments
    * DOCTOR → Manage appointments

---

## 🌐 API Endpoints

### Auth

```
POST /api/auth/register
POST /api/auth/login
```

### Doctors

```
GET /api/doctor/getall
GET /api/doctor/search
```

### Slots

```
GET /api/slots/{doctorId}/{date}
```

### Appointments

```
POST /api/appointments/book-slot
GET /api/appointments
```

---

## ⚠️ Common Issues

### ❌ No slots generated

* Check scheduler
* Ensure doctor exists
* Check DB entries

### ❌ CORS error

Update allowed origins in:

```
SecurityConfig
```

---

## 🔄 Scheduler

```java
@Scheduled(cron = "0 0 0 * * ?")
```

Runs daily to generate future slots.

---

## 📌 Future Improvements

* Email notifications
* Payment integration
* Doctor availability customization
* Admin dashboard

---

## 👨‍💻 Author

Ragavan R

---

