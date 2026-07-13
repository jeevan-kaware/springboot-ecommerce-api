# 🛒 Spring Boot E-Commerce API

A complete E-Commerce REST API built using **Spring Boot, Spring Security, JWT Authentication, PostgreSQL, and Spring AI**.

This project provides secure APIs for users, products, categories, cart management, orders, admin operations, and AI-based product recommendations.

---

# 🚀 Features

## Authentication & Security

- User Registration
- User Login
- JWT Authentication
- Refresh Token System
- Password Encryption using BCrypt
- Role Based Authorization
    - USER
    - ADMIN


## Product Management

- Create Product
- Update Product
- Delete Product
- Get All Products
- Search Products
- Category Based Product Filtering


## Category Management

- Create Category
- Update Category
- Delete Category
- Get Categories


## Shopping Cart

- Add Product to Cart
- Update Cart Quantity
- Remove Cart Item
- Clear Cart


## Order Management

- Create Orders
- View User Orders
- View Order Details


## Admin Features

- Manage Users
- Manage Orders
- Update Order Status


## AI Integration

- AI Product Recommendation System
- Powered by Spring AI and Gemini AI Model


---

# 🛠 Tech Stack

## Backend

- Java 21
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- Maven


## Database

- PostgreSQL


## Security

- JWT
- BCrypt Password Encoder


## Documentation

- Swagger OpenAPI


## AI

- Spring AI
- Gemini AI


## Tools

- IntelliJ IDEA
- Git
- GitHub
- Postman


## 📂 Project Structure

```text
src/main/java/com/jeevan/ecommerce
├── controller
├── service
├── repository
├── entity
├── dto
├── security
├── config
├── enums
├── exception
└── EcommerceApplication.java
```

# 🔐 Environment Configuration

Create a `.env` file in the project root.

```properties
DB_URL=your_database_url
DB_USERNAME=your_username
DB_PASSWORD=your_password
OPENAI_API_KEY=your_api_key
```

Note:
Environment variables are not committed to GitHub for security reasons.

---

# ▶️ Run Project

Clone repository:
```bash
git clone https://github.com/jeevan-kaware/springboot-ecommerce-api.git
```

Navigate: 
```bash
cd springboot-ecommerce-api
```

Run application:
```bash
./mvnw spring-boot:run
```
Application runs on: http://localhost:8080


---

# 📖 API Documentation

Swagger UI: http://localhost:8080/swagger-ui/index.html


---

# 📸 Screenshots


## Swagger Documentation

![Swagger](Screenshots/01-swagger-home.png)


## Authentication APIs

![Auth](Screenshots/02-auth-controller.png)


## Product APIs

![Product](Screenshots/03-product-controller.png)


## Cart APIs

![Cart](Screenshots/05-cart-controller.png)


## Order APIs

![Order](Screenshots/06-order-controller.png)


## Admin APIs

![Admin](Screenshots/07-admin-controller.png)


## AI Recommendation Response

![AI Response](Screenshots/13-ai-response.png)


## PostgreSQL Database

![Database](Screenshots/14-postgresql-database.png)


---

# 🔮 Future Enhancements

- Payment Gateway Integration
- Docker Deployment
- Cloud Deployment
- Email Notification System
- Product Reviews and Ratings


---

# 👨‍💻 Author

**Jeevan Kaware**

Java Backend Developer

GitHub:
https://github.com/jeevan-kaware
