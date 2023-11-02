# library-microservices



    - spring.application.name=library-service 
Bu satir, uygulamanin adini belirtir. Bu durumda, uygulamanin adi "library-service" olarak ayarlanmistir.

    - eureka.instance.instance-id=${spring.application.name}:${random.value}
Bu satir, Eureka sunucusunda bu mikro servisin benzersiz kimligini belirler. Bu durumda, mikro servisin adi ve
rastgele bir degerle birlestirilmis bir kimlik olusturulur.

    - eureka.instance.prefer-ip-address=true
Bu satir, bu mikro servisin Eureka sunucusuna kaydedilirken IP adresini tercih edecegini belirtir.

    - eureka.client.service-url.defaultZone=${EUREKA_URI:http://localhost:8761/eureka}
Bu satir, bu mikro servisin Eureka sunucusuna nasil baglanacagini belirtir. defaultZone parametresi, Eureka sunucusunun URL'sini belirtir.
Bu durumda, EUREKA_URI ortam degiskeni kullanilir, eger tanimlanmamissa http://localhost:8761/eureka kullanilir