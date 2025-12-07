# Void Apparel Backend API

Complete Spring Boot REST API backend for the Void Apparel e-commerce platform.

## Features

- **User Authentication**: JWT-based authentication with signup, login, and "remember me" functionality
- **Product Catalog**: Products for Jeans, Winter wear, and Accessories with customization options
- **Shopping Cart**: Full cart management with promo code support
- **Order Management**: Create orders from cart and view order history
- **Custom Designs**: Save and manage custom product design drafts
- **Security**: BCrypt password hashing and JWT token-based authentication
- **API Documentation**: Interactive Swagger UI documentation

## Technology Stack

- Java 17
- Spring Boot 3.2.0
- Spring Security with JWT
- Spring Data JPA
- H2 Database (in-memory, for development)
- PostgreSQL support (for production)
- Lombok
- SpringDoc OpenAPI (Swagger)
- Maven

## Prerequisites

- Java 17 or higher
- Maven 3.6+

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/anamtakmal/void-apparel.git
cd void-apparel
```

### 2. Build the Project

```bash
mvn clean install
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### 4. Access H2 Console (Development)

- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:voidapparel`
- Username: `sa`
- Password: (leave blank)

### 5. Access Swagger API Documentation

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- OpenAPI Docs: `http://localhost:8080/v3/api-docs`

## API Endpoints

### Authentication (`/api/auth`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/auth/signup` | Register new user | No |
| POST | `/api/auth/login` | User login | No |
| GET | `/api/auth/me` | Get current user info | Yes |
| POST | `/api/auth/forgot-password` | Request password reset | No |

### Products (`/api/products`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/products` | List all products | No |
| GET | `/api/products/{id}` | Get product details | No |
| GET | `/api/products/category/{category}` | Get products by category | No |
| GET | `/api/products/customization-options/{category}` | Get customization options | No |

### Cart (`/api/cart`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| GET | `/api/cart` | Get current user's cart | Yes |
| POST | `/api/cart/items` | Add item to cart | Yes |
| PUT | `/api/cart/items/{itemId}` | Update cart item quantity | Yes |
| DELETE | `/api/cart/items/{itemId}` | Remove item from cart | Yes |
| POST | `/api/cart/promo` | Apply promo code | Yes |
| DELETE | `/api/cart/promo` | Remove promo code | Yes |

### Orders (`/api/orders`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/orders` | Create order from cart | Yes |
| GET | `/api/orders` | Get user's order history | Yes |
| GET | `/api/orders/{id}` | Get order details | Yes |

### Custom Designs (`/api/designs`)

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/designs` | Save custom design draft | Yes |
| GET | `/api/designs` | Get user's saved designs | Yes |
| GET | `/api/designs/{id}` | Get specific design | Yes |
| PUT | `/api/designs/{id}` | Update design | Yes |
| DELETE | `/api/designs/{id}` | Delete design | Yes |

## Sample Data

The application initializes with sample data:

### Products
- **Jeans**: Classic Bootcut Jeans ($149.99), Slim Fit Jeans ($139.99)
- **Winter**: Premium Hoodie ($89.99), Leather Jacket ($299.99)
- **Accessories**: Leather Tote Bag ($79.99), Designer Wallet ($49.99)

### Promo Codes
- `VOID20`: 20% discount
- `WELCOME10`: 10% discount

## Example API Requests

### 1. User Signup

```bash
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john@example.com",
    "password": "password123"
  }'
```

### 2. User Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123",
    "rememberMe": false
  }'
```

### 3. Add Item to Cart

```bash
curl -X POST http://localhost:8080/api/cart/items \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "productId": 1,
    "quantity": 1,
    "customizations": {
      "fit": "Bootcut",
      "fabric": "Denim (Classic)",
      "patches": "true",
      "color": "#222222"
    }
  }'
```

### 4. Apply Promo Code

```bash
curl -X POST http://localhost:8080/api/cart/promo \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "code": "VOID20"
  }'
```

### 5. Create Order

```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "shippingAddress": "123 Main St, City, State 12345",
    "billingAddress": "123 Main St, City, State 12345",
    "paymentMethod": "credit_card"
  }'
```

## Configuration

### JWT Settings

Configure JWT in `application.properties`:

```properties
app.jwt.secret=YourSecretKeyHere
app.jwt.expiration=86400000
app.jwt.remember-me-expiration=604800000
```

### Database Configuration

#### H2 (Development - Default)
```properties
spring.datasource.url=jdbc:h2:mem:voidapparel
spring.datasource.username=sa
spring.datasource.password=
```

#### PostgreSQL (Production)
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/voidapparel
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
```

### CORS Configuration

CORS is configured in `CorsConfig.java` to allow requests from:
- `http://localhost:3000`
- `http://localhost:8080`
- `http://127.0.0.1:5500`

Add more origins as needed.

## Project Structure

```
src/main/java/com/voidapparel/
├── VoidApparelApplication.java       # Main application class
├── config/
│   ├── SecurityConfig.java           # Security configuration
│   ├── CorsConfig.java               # CORS configuration
│   └── DataInitializer.java          # Sample data loader
├── controller/
│   ├── AuthController.java           # Authentication endpoints
│   ├── ProductController.java        # Product endpoints
│   ├── CartController.java           # Cart endpoints
│   ├── OrderController.java          # Order endpoints
│   └── CustomDesignController.java   # Custom design endpoints
├── model/
│   ├── User.java                     # User entity
│   ├── Product.java                  # Product entity
│   ├── Cart.java                     # Cart entity
│   ├── CartItem.java                 # Cart item entity
│   ├── Order.java                    # Order entity
│   ├── OrderItem.java                # Order item entity
│   ├── CustomDesign.java             # Custom design entity
│   └── PromoCode.java                # Promo code entity
├── repository/
│   ├── UserRepository.java           # User repository
│   ├── ProductRepository.java        # Product repository
│   ├── CartRepository.java           # Cart repository
│   ├── OrderRepository.java          # Order repository
│   ├── CustomDesignRepository.java   # Custom design repository
│   └── PromoCodeRepository.java      # Promo code repository
├── service/
│   ├── AuthService.java              # Authentication service
│   ├── ProductService.java           # Product service
│   ├── CartService.java              # Cart service
│   ├── OrderService.java             # Order service
│   └── CustomDesignService.java      # Custom design service
├── dto/
│   ├── LoginRequest.java             # Login request DTO
│   ├── SignupRequest.java            # Signup request DTO
│   ├── AuthResponse.java             # Auth response DTO
│   ├── CartItemRequest.java          # Cart item request DTO
│   ├── CartResponse.java             # Cart response DTO
│   ├── PromoCodeRequest.java         # Promo code request DTO
│   ├── OrderRequest.java             # Order request DTO
│   └── CustomDesignRequest.java      # Custom design request DTO
├── security/
│   ├── JwtTokenProvider.java         # JWT token utility
│   ├── JwtAuthenticationFilter.java  # JWT filter
│   └── UserDetailsServiceImpl.java   # User details service
└── exception/
    ├── GlobalExceptionHandler.java   # Global exception handler
    ├── ResourceNotFoundException.java # Custom exception
    └── BadRequestException.java      # Custom exception
```

## Cart Calculations

The cart automatically calculates:
- **Subtotal**: Sum of all item prices
- **Discount**: Applied from promo code (e.g., VOID20 = 20% off)
- **Tax**: 8% of (Subtotal - Discount)
- **Total**: Subtotal - Discount + Tax

## Customization Pricing

Each customization option adds $10 to the product price:
- Patches: +$10
- Embroidery: +$10
- Rips: +$10
- etc.

## Security

- Passwords are hashed using BCrypt
- JWT tokens for stateless authentication
- Token expiration: 24 hours (7 days with "remember me")
- Protected endpoints require valid JWT token

## Testing

Run tests with:

```bash
mvn test
```

## Building for Production

```bash
mvn clean package
java -jar target/void-apparel-backend-1.0.0.jar
```

## License

This project is part of the Void Apparel e-commerce platform.

## Support

For issues or questions, please open an issue on the GitHub repository.
