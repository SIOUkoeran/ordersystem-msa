# msa-webflux-demo (study)

## based on webflux
---
3 MicroServcies + (...)

reactiveMongoDB + Spring Cloud + Eureka + Spring Security +rabbitmq + kafka

(resilience4j, docker)

---
shopping mall 

          -> config service ----spring Cloud-----------> rabbitMQ 
discovery -> gateway( loadbalancer + auth filter + ) -->  catalog -service : catalog server(webflux)   ---> kafka (reactive) ----> mongo DB (reactive)
             
                                                     -->  order -service : order server(webflux)     

                                                     -->  user -service : user server(webflux)   
