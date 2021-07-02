# Author
## DennisKingsman
# Resources
[developer.ibm.com/components/ibm-mq](https://developer.ibm.com/components/ibm-mq/tutorials/mq-jms-application-development-with-spring-boot/)  
[lectures](https://www.youtube.com/playlist?list=PLZ_8oifs5twMmwxjHsy1CFzXceI1s96M8)  
[ibm-mq-configuration](https://github.com/ibm-messaging/mq-container/blob/master/docs/developer-config.md#details-of-the-default-configuration)  
### to enter console by default use:
admin  
passw0rd  
[mq-console](https://localhost:9443/ibmmq/console/login.html)
# Recommended to study
[baeldung.com/spring-jms](https://www.baeldung.com/spring-jms)  
[spring.io/guides/gs/messaging-jms](https://spring.io/guides/gs/messaging-jms/)  
# Test
## Test mq request by postman
use `post` http://localhost:8080/ibm/mq/ and send json like
```
    {
         "message": "Hello2",
         "identifier": "id1"
    }
```
check `DEV.QUEUE.1` using mq-console  
use `get` http://localhost:8080/ibm/mq/?correlationId=id1 to test correlationId
