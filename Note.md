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


The equals and hashCode methods are used to compare objects in Java. When you use the filter method in your code, it relies on the equals method to determine if two User objects are the same. If the equals method is not properly overridden, the comparison may fail even if the User objects represent the same user.  

#### equals Method

The equals method is used to compare the current object with another object to check for equality. By default, the equals method in the Object class checks for reference equality, meaning it will only return true if both references point to the same object in memory.  

#### hashCode Method

The hashCode method returns an integer representation of the object. It is used in hash-based collections like HashMap, HashSet, etc. If two objects are equal according to the equals method, they must have the same hash code.  
Overriding equals and hashCode
To ensure that two User objects are considered equal if they have the same id, you need to override the equals and hashCode methods in the User class.  Here is how you can override these methods in the User class:

```java
@Override
public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return id == user.id;
}

@Override
public int hashCode() {
    return Objects.hash(id);
}
```

**equals Method:** This method first checks if the current object (this) is the same as the object being compared (o). If they are the same, it returns true. If the object being compared is null or not of the same class, it returns false. Finally, it compares the id fields of both objects to determine equality.

**hashCode Method:** This method returns a hash code value for the object based on the id field. It uses the Objects.hash method to generate the hash code.

By overriding these methods, you ensure that two User objects with the same id are considered equal, which is crucial for the filter method to work correctly in your TodoController.