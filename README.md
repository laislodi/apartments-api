# Apartments Spring Boot Project

## Jwt Configuration

Source: https://www.youtube.com/watch?v=RnZmeczS_DI

- Create JWT filter
- User class implementing UserDetails
- User service implementing UserDetailsService -> This will be used in the Security configuration
- Role Enum class
- User Repository must implement findByUsername -> this will be used in the `authenticate` function
- Configure Security to use the filter, the session manager, authorize http requests
- Authentication Controller must implement `login` and `register` functions -> these will not be authenticated
- Authentication Controller must implement non authentication functions

