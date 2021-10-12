# msa-webflux-demo (study)

## based on webflux
---
3 MicroServcies + (...)

reactiveMongoDB + Spring Cloud + Eureka + Spring Security +rabbitmq + kafka

(resilience4j, docker)

---
shopping mall 

           
discovery -> gateway( loadbalancer + auth filter + ) --->  catalog -service : catalog server(webflux)   ---> kafka (reactive) ----> mongo DB (reactive)
                                                        -> config service -------- rabbitMQ ---------->  
                                                                              --->  order -service : order server(webflux)     
          -> spring security---------------------------->         
                                                                              --->  user -service : user server(webflux)   
