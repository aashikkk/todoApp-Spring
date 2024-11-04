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


The method “getAuthorities()” is used to get the list of roles assigned to the user. 

The method “getUsername()” is used to get the username of the user. 

The method “getPassword()” is used to get the password of the user. 

The method “isEnabled()” is used to check if the user is enabled or disabled. 

The method “isAccountNonExpired()” is used to check if the user account is expired or not. 

The method “isAccountNonLocked()” is used to check if the user account is locked or not. 

The method “isCredentialsNonExpired()” is used to check if the user credentials are expired or not.

**Defined in application.properties**

The secret key must be an HMAC hash string of 256 bits; otherwise will throw error.

The token expiration time is expressed in milliseconds, so remember if your token expires too soon.
