# resume-parser
This application provide functionality for users to upload resume documents and match applications to ideal job positions.


###Database Configuration(Docker)
- 
* create docker-compose.yml

        version: '3'
        
        services:
        
          mysql-development:
            image: mysql:8.0.17
            environment:
              MYSQL_ROOT_PASSWORD: password(change)
              MYSQL_DATABASE: db-name(change)
            ports:
              - "3308:3306"
add persistence store to intellij config (optional)