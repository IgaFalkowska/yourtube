#Instruction to run application

## BACKEND APPLICATION
Prior running docker-compose command please build backend .jar package using command: ```mvn package ```

To run application use docker-compose.yml from docker folder, please use in terminal:
```docker-compose up```

Application will run on ports:
- frontend: localhost:3000
- backend: localhost:8080
- postgresql database: localhost:5432