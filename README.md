# sp_security_3.2.1
spring boot security version 3.2.1

# application YML configuration with mySQL
  spring:
  datasource:
    url: jdbc:mysql://localhost:3306/sp_jwt3.2.1
    username: root
    password:
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
    database: mysql
    database-platform: org.hibernated.dialect.MySQL8Dialect
    
# create an Entity User (implementing UserDetails) and enumeration Role(USER, ADMIN)
# create repository User
