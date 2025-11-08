# 🌐 DistServ – Dağıtık Sunucu Tabanlı İstek Senkronizasyon Sistemi (Java Console)

Bu proje, Java ile geliştirilmiş temel bir dağıtık sunucu sistemidir. İstemciden gelen işlem isteklerini işler, sunucular arası veri senkronizasyonu sağlar. Her sunucu, diğer sunucularla bağlantı kurarak güncel durumu paylaşır. Konsol üzerinden çalışır, GUI içermez.

> 📅 Proje tarihi: Ocak 2024

---

## 🧠 Teknik Açıklama

- **Sunucu Yapısı**: Her biri farklı portta çalışan 3 sunucu, gelen işlem isteklerini işler ve `Abone` nesnesini diğer sunuculara iletir.
- **PingThread**: Sunucular arası bağlantı kontrolü için her sunucu, diğerlerini periyodik olarak ping’ler.
- **Abone Nesnesi**: Abonelik durumu, giriş/çıkış bilgisi ve son güncelleme zamanını içerir.
- **İstek Protokolü**:
  - `ABONOL`, `ABONIPTAL`, `GIRIS`, `CIKIS`
  - `50 HATA`, `55 TAMM`, `99 HATA`

---

## 🎬 Senaryo Akışı

- Client, `ABONOL` isteği gönderir → Server1 işler, diğer sunuculara iletir.  
- Server2, gelen `Abone` nesnesini kontrol eder → güncel değilse güncellemeyi reddeder.  
- Server3, `PingThread` ile Server1’e bağlantı kurar → bağlantı başarılıysa log basılır.  
- Client, `CIKIS` isteği gönderir → tüm sunucular durumu günceller.

---

## ⚙️ Sunucu ve Thread Yapısı

| Yapı           | Açıklama                                                  |
|----------------|-----------------------------------------------------------|
| `PingThread`   | Diğer sunuculara periyodik bağlantı kontrolü yapar        |
| `Abone`        | Ortak veri modeli, tüm sunucular arasında taşınır         |
| `ServerX`      | İstekleri işler, `Abone` nesnesini diğer sunuculara iletir |
| `Client`       | Kullanıcıdan işlem isteği alır, sunuculara sırayla gönderir |

---

## 📸 Konsol Çıktısı

> Her sunucu kendi portunda çalışır ve gelen istekleri konsola yazdırır. Ping işlemleri ve hata durumları da konsolda görünür.

---

## 🗂️ Proje Yapısı

```
/src
  └── Client.java             # İstek gönderici
  └── Server1.java            # Sunucu 1
  └── Server2.java            # Sunucu 2
  └── Server3.java            # Sunucu 3
  └── Abone.java              # Ortak veri modeli
README.md
LICENSE
```

---

## 🚀 Çalıştırma

Her sunucuyu ayrı terminalde başlat:

```bash
javac src/Server1.java
java src.Server1

javac src/Server2.java
java src.Server2

javac src/Server3.java
java src.Server3

javac src/Client.java
java src.Client
```

> Not: Java 8+ veya üzeri önerilir. Konsol üzerinden çıktı alınır, GUI bulunmamaktadır.

---

## 📌 Versiyonlar

- `v0.1` → Ocak 2024: Temel sunucu yapısı, istemci istekleri, Abone nesnesi, PingThread ile sunucular arası bağlantı kontrolü

---

## 📄 Lisans

MIT Lisansı – Dilediğiniz gibi kullanabilir, geliştirebilir ve paylaşabilirsiniz.
