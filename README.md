# 🚂 Train Ticket Booking System - Spring Boot Project

## 📋 Project Overview
This is a simple yet powerful **Train Ticket Booking REST API Application**.  
Built using **Java**, **Spring Boot**, and **Java Collections Framework** without any external database.  
All operations (search, booking, ticket generation, cancellation) are tested using **Postman** client.

---

## 🛠️ Technologies Used (Tech Stack)
- ⚙️ Java 17
- 🌱 Spring Boot 3.x
- 📦 Maven (Dependency Management)
- 💾 Java Collections (`HashMap`, `Queue`, `Set`, `List`) for data storage
- 🔄 RESTful APIs
- 🧪 Postman (for API testing)
- 🧰 Eclipse IDE

---

## 🚀 Main Features

- 🔍 **Search Trains** by source, destination, and date  
- 🧭 **Select Journey**: View detailed train data for booking  
- 👤 **Add Traveller** information dynamically  
- 🎟️ **Book Ticket**: Auto-generate PNR, fare, and assign seats  
- 📄 **Retrieve Ticket**: Lookup by PNR number  
- ❌ **Cancel Ticket** with a simple endpoint   
- 🎟️ **Auto-waitlist update**: Auto-process waitlist tickets after a ticket is cancelled 

All features are accessible via **REST endpoints**, designed with clean coding principles, layered architecture, and extensibility in mind.

---

## 📚 API Endpoints

|HTTP Method|					Endpoint									|				Purpose				|
|-----------|---------------------------------------------------------------|-----------------------------------|
|	GET		| `/api/trains/search?source=X&destination=Y&DepartureDate=Z`	|	Search available trains			|
|	POST	| `/api/journey/{trainId}/{departureDate}`						|	Fetch selected Train details	|
|	POST	| `/api/travellers`												|	Add a new Traveller             |
|	POST	| `/api/tickets/{trainId}/{travellerId}/{departureDate}`		|	Book a Ticket                   |
|	GET		| `/api/ticket/{pnr}`											|	Retrieve Ticket Details         |
|	DELETE	| `/api/cancel/{pnr}`											|	Cancel Ticket                   |

---

## 🧩 Project Structure

```
com.example.trainticketbooking
│
├── controller       // All REST API endpoints
├── dto              // DTO objects for presenting to the end user
├── exception        // Exception handlers for errors and invalid user input
├── mapper           // Converts model objects to DTO objects and vice-versa
├── model            // POJOs representing Trains, Travellers, Tickets
├── repository       // In-memory data storage using Java Collections
├── service          // Business Logic Layer
├── trainloader      // Creates a train list using CommandLineRunner
└── TrainTicketBookingApplication.java  // Main Spring Boot Application
```

---

## 👨‍💻 How to Run the Project

1. Clone the repository:
   ```bash
   git clone https://github.com/radhuprabu-1/train-ticket-booking.git
   cd train-ticket-booking-system
   ```

2. Build and run the Spring Boot application:
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

3. Test APIs using Postman or any REST client.

---

## 💼 Project Highlights

✅ Demonstrates practical use of:
- Spring Boot REST architecture  
- In-memory data repositories using Java collection framework (`HashMap`, `Queue`, `Set`, `List`) 
- Layered coding practices (Controller, Service, Repository)  
- DTO pattern with mapping logic  
- Exception handling using `@ControllerAdvice`  
- JUnit & Mockito for unit testing  
- Clean code with SOLID principles and Java best practices  
- API documentation-ready structure (can be extended with Swagger)

---

## 📧 Contact

If you found this project useful or want to collaborate, feel free to connect!

📩 Email: radhumahadev@gmail.com  

---

## 📌 Future Enhancements

- ✅ Add Swagger/OpenAPI documentation  
- 💾 Switch from in-memory to real database (H2/MySQL/PostgreSQL)  
- 🛡️ Add Spring-Security based authentication for users - admins, travellers  

---

⭐️ **If you like this project, please give it a star on GitHub to show support!**