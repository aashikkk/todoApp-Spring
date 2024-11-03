To define properties,
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/todoapp
spring.datasource.username=root
spring.datasource.password=asd123
```

When return String,
```java
@RestController
    public class TodoController {
    
        @GetMapping("/")
        public String hello(){
            return "Hello World";
        }
    }
```

When we need to return as JSON,

```java
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    class Message{
        private String message;
    }
    
    
    @RestController
    public class TodoController {
    
        @GetMapping("/")
        public Message hello(){
            return new Message("Hello World");
        }
    }
```

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/todoapp
spring.datasource.username=root
spring.datasource.password=asd123
spring.jpa.hibernate.ddl-auto=update // update whenever the database schema changes
```
