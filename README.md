# msa-webflux-demo (study)

## based on webflux
---


reactiveMongoDB + Spring Cloud + Eureka + Spring Security +rabbitmq + kafka

(resilience4j, docker)

---
small 5 micro service 

flow

Discovery-service -> Config service--> RabbitMQ -> Spring security -> Gateway(load balancer + auth filter) 


-> Order-service + Catalog-service + Order-service (webFlux) -> Kafka -> MongoDB
