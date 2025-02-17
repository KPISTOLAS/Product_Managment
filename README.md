#📦 Product Management Application

This is a full-stack web application designed for managing products in an inventory or store. The application allows users to create, read, update, and delete products from the database. Built using Spring Boot for the backend, Thymeleaf for the frontend, and a MySQL database for storage, the system can track multiple product attributes such as name, brand, category, price, and creation date.

##⚙️ Features

- **CRUD Operations**: 
  -📝 Create,📖 read,✏️ update, and❌ delete products.
- **Product Listing**:
  -🖥️ View products in a tabular format with details like ID, name, brand, category, price, image, and creation date.
- **Image Handling**: 
  -📸 Upload and display product images.
- **Confirmation Dialogs**: 
  -🚨 Delete action is protected with a confirmation dialog.
- **Responsive Design**:
  -📱 Uses Bootstrap for a clean and responsive UI.

##🛠️ Tech Stack

- **Backend**: Java with Spring Boot  
- **Frontend**: Thymeleaf with HTML, CSS, Bootstrap  
- **Database**: MySQL  
- **Tools**:  
  -🗄️ Spring Data JPA for database interaction  
  -🔄 Spring Boot DevTools for live reloading  
  -🎨 Bootstrap for styling  

##📝 MIT License

```bash
MIT License

Copyright (c) 2025 Pistolas Konstantinos

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

```
###🗂️ Project Structure
```bash
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.example.management
│   │   │       ├── controllers  # Controllers handle HTTP requests and responses
│   │   │       ├── models       # Domain models representing the database entities
│   │   │       ├── repositories # Repository interfaces for data access
│   │   │       └── services     # Business logic
│   │   ├── resources
│   │   │   ├── static           # Static assets like CSS, images, and JS
│   │   │   └── templates        # Thymeleaf templates for the views
│   └── test                     # Unit and integration tests
├── pom.xml                      # Project dependencies and build configuration
└── README.md
```
##🌐 More
- **Access the app at**:
http://localhost:8080
- **application.properties**:
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/management
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD
spring.jpa.hibernate.ddl-auto=update
```


