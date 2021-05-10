# Scaleable Micro Service Project

## Start Locale Images via Docker Compose

```
docker-compose up
```

## Test Applications

- Event: http://localhost:8080
- Ticket: http://localhost:8081
- Invoice: Background Service, hence no user interface. See console output 

## Deploy to Kubernetes

```
cd kubernetes
kubectl apply -f .
```

