# library-microservices

<details>

<summary>? Resilience4j Nedir ?</summary>

[`https://umitsamimi.medium.com/circuit-breaker-resilience4j-7e1082610c52`](https://umitsamimi.medium.com/circuit-breaker-resilience4j-7e1082610c52)`-> Cok iyi anlatiyor`

## ? Resilience4j Nedir ?

* Bilindi?i ?zere, arka-plan (back-end) servislerinin giderek karma??kla?mas? ve tek par?a halinde s?rd?r?lebilirli?inin zorla?mas?n?n sonucunda, mikroservis mimarisi kullan?larak arka-plan servislerinin birbirleriyle ileti?im halinde olan, nispeten daha k???k servisler halinde d?zenlenmesi olduk?a pop?ler hale gelmi?tir.
* Bu servisler, birbirleriyle kapal? bir a? ?zerinde, ?o?unlukla HTTP protokol?n? kullanarak haberle?mektedirler.
* Lakin, birbirleriyle HTTP ?zerinden haberle?en servisler, baz? ek problemleri de beraberinde getirebilirler.
* Projemden ?rnek verirsem user-service servisi, kendisine gelen istekleri kar??lamak ?zere department-service servisiyle ileti?ime ge?iyor olsun.
* department-service servisinde olu?abilecek bir sistem hatas?, servisin yeni bir s?r?m?n?n sunucuya y?klenmesi veya yeni s?r?mde ??kabilecek istikrar sorunlar? gibi bir ?ok nedenden ?t?r?, department-service servisine giden isteklerin zamanl? bir bi?imde yan?tlanamad???n? ve baz? ?a?r?larda uygun bir cevap nesnesi yerine sunucu hatalar?n?n d?nd?r?ld???n? d???n?n.
* Bu durumda, department-service servisinin d?nd?rd??? hata user-service servisine de s??rayacakt?r.
* Ard?ndan, s?z konusu hata department-service servisine ?a?r? yap?lan katmandan itibaren ?st katmanlara (servis, denetici (controller)vs.) f?rlat?lacak ve user-service servisine ?a?r? ger?ekle?tiren servisin de uygun bir yan?t alamamas?na neden olacakt?r.
* Bu ?ekilde olu?an bir hata yay?l?m zinciri, son kullan?c?n?n s?z konusu web uygulamas?n? arzu etti?i bir bi?imde kullanamamas?yla sonu?lanacakt?r.
* Bu durumlar ne gibi y?ntemlerle giderilebilir.

### ? Retry

* Beklenmedik bir yan?t?n - ya da yan?t al?namamas?n?n - iste?i tekrar g?ndererek d?zeltilebilece?ini varsayd???m?zda, yeniden deneme kal?b?n? kullanmak yard?mc? olabilir. Bu, i?lem ba?ar?s?z olarak i?aretlenmeden ?nce ba?ar?s?z isteklerin yap?land?r?labilir say?da yeniden denendi?i ?ok basit bir modeldir.
* A?a??daki durumlarda yeniden denemeler yararl? olabilir:
* Paket kayb? gibi ge?ici a? sorunlar?.
* Hedef hizmetin dahili hatalar?, ?rne?in bir veritaban? kesintisinden kaynaklanan.
* Hedef hizmete y?nelik ?ok say?da talep nedeniyle yan?t al?namamas? veya yava? yan?t al?nmas?.

### ? Fallback

* Geri d?n?? kal?b?, hizmetinizin ba?ka bir hizmete yap?lan ba?ar?s?z bir istek durumunda y?r?tmeye devam etmesini sa?lar. Eksik bir yan?t nedeniyle hesaplamay? iptal etmek yerine, bir geri d?n?? de?eri doldururuz.

### ? Timeout

* Zaman a??m? modeli olduk?a basittir ve bir?ok HTTP istemcisinin yap?land?r?lm?? varsay?lan bir zaman a??m? vard?r. Ama?, yan?tlar i?in s?n?rs?z bekleme s?relerinden ka??nmak ve b?ylece zaman a??m? i?inde yan?t al?namayan her iste?i ba?ar?s?z olarak de?erlendirmektir.

### ? Circuit breaker

* Circuit Breakers deseni, ad?ndan anla??laca?? ?zere elektronik devrelerdeki, devre kesici ?alt cihazlar gibi kurgulanan bir y?ntemdir.
* Devre kesiciler, elektronik devreyi korumak i?in sistemde meydana gelen bir aksakl?k durumunda (y?k ak?m?n? veya k?sa devre ak?mlar?) y?k ge?i?ini durdururlar.
* Circuit Breakers deseni uyguland???nda, servisler aras?nda haberle?meyi kapsayacak ?ekilde in?aa edilir.
* Servisler aras?ndaki ileti?imi (Event, Message, Http, vb.) izler ve haberle?medeki meydana gelen hatalar? takip eder.
* Request yap?lan bir API ucunun, http 500 hata kodu d?nmesi veya f?rlat?lan bir event’in handle edilememesi bu hata duruma ?rnek olarak g?sterilebilir.
* Sistemde meydana gelen hata durumu belirli bir e?ik de?erini ge?ti?inde ise Circuit Breakers a??k duruma ge?er ve haberle?meyi keser, daha ?nce belirlenen hata mesajlar?n? d?nd?r?r.
* Bir s?re bekledikten sonra devre yar? a??k duruma ge?er. Bu durumda bir iste?in ge?mesine izin verir ve ba?ar?s?z olmas? durumunda a??k duruma veya ba?ar?l? olmas? durumunda kapal? duruma geri d?ner.
* Circuit Breakers a??k durumdayken haberle?me trafi?ini izlemeye devam eder ve istek yap?lan servis veya f?rlat?lan bir event ba?ar?l? sonu?lar d?nmeye ba?lam??sa kapal? duruma ge?er.
* Circuit Breakers’?n ?? durumu vard?r. Bu durumlar: A??k (Open), Kapal? (Closed) ve Yar?-A??k (Half-Open).

#### Closed

* Sigorta tamamen kapal?d?r. B?t?n ?a?r?lar?n yap?lmas?na izin verilir ve hatal? ?a?r?lar kurtarma metoduna y?nlendirilebilir (fallback). Hatal? ?a?r?lar?n say?s?n?n (veya oran?n?n) belirli bir say?n?n ?st?nde olmas? takdirinde, sigorta, a??k konuma getirilir.

#### Open

* Sigorta aktif konumdad?r ve ?a?r?lar?n tamam?n? reddetmektedir. Reddedilen ?a?r?lar, mikroservis i?erisinde yer alan bir kurtarma metoduna y?nlendirilerek ?a?r?n?n sorunsuz bir bi?imde sonu?lanmas? sa?lanabilir.

#### Half-Open

* Sigortan?n a??k konuma ge?mesinden belirli bir s?re sonra, sigorta, kendini yar? a??k konuma getirir. Bu durumda belirli say?da (veya oranda) ?a?r?n?n ger?ekle?tirilmesine izin verilir. E?er hatal? ?a?r?lar?n oran? (veya say?s?) belirli bir say?n?n ?zerinde olursa, tekrardan a??k konuma ge?ilir; aksi takdirde sigorta tamamen kapat?l?r.

</details>

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