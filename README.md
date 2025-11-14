İşte senin v1 mimarini yansıtan, v0.1 stiline sadık ama v1’in teknik gelişmişliğini net şekilde anlatan yeni README taslağın 👇

---

```markdown
# 🌐 DistServ v1.0 – Dağıtık Sunucu Tabanlı İstek Senkronizasyon Sistemi (Java Console)

Bu sürüm, sistemin mimari evrimini tamamlayarak modülerlik, tip güvenliği, thread senkronizasyonu ve loglama gibi temel yapı taşlarını kazandırır. İstemci istekleri artık daha güvenli, izlenebilir ve test edilebilir şekilde işlenmektedir.

> 📅 Sürüm tarihi: Kasım 2025

---

## 🧠 Teknik Açıklama

- **Modüler Komut İşleme**: `CommandProcessor` sınıfı ile komutlar ayrı metotlara bölündü, iş mantığı IO’dan ayrıştırıldı.
- **Enum Kullanımı**: `CommandType` enum ile komutlar tip güvenli hale getirildi, switch-case ile sadeleştirildi.
- **Thread Senkronizasyonu**: `ReentrantLock` ile `Abone` nesnesi eşzamanlı erişime karşı korundu.
- **Loglama**: `HealthLogger` ile hata ve bilgi logları ayrıştırıldı, sunucu ID’si ile etiketlendi.
- **Veri Yayımı**: `ServerHandler.Send(...)` ile güncel `Abone` nesnesi diğer sunuculara iletildi.

---

## 🎬 Senaryo Akışı

- Client, `ABONOL` isteği gönderir → Server1 işler, diğer sunuculara iletir.
- Server2, gelen `Abone` nesnesini kontrol eder → zaman damgası eskiyse güncellemeyi reddeder.
- Server3, `PingThread` ile Server1’e bağlantı kurar → bağlantı başarılıysa log basılır.
- Client, `CIKIS` isteği gönderir → tüm sunucular durumu günceller ve loglar.

---

## ⚙️ Sunucu ve Thread Yapısı

| Yapı               | Açıklama                                                  |
|--------------------|-----------------------------------------------------------|
| `CommandProcessor` | Komutları işler, yanıt üretir (`55 TAMM`, `50 HATA`)      |
| `ClientHandler`    | Komutları yönlendirir, IO işlemlerini yönetir             |
| `PingThread`       | Diğer sunuculara periyodik bağlantı kontrolü yapar        |
| `Abone`            | Ortak veri modeli, tüm sunucular arasında taşınır         |
| `ServerX`          | İstekleri işler, `Abone` nesnesini diğer sunuculara iletir |
| `Client`           | Kullanıcıdan işlem isteği alır, sunuculara sırayla gönderir |

---

## 📸 Konsol Çıktısı

> Her sunucu kendi portunda çalışır ve gelen istekleri konsola yazdırır. Ping işlemleri, hata durumları ve loglar konsolda görünür.

---

## 🗂️ Proje Yapısı

```
/src
  └── Client.java             # İstek gönderici
  └── Server1.java            # Sunucu 1
  └── Server2.java            # Sunucu 2
  └── Server3.java            # Sunucu 3
  └── Abone.java              # Ortak veri modeli
  └── CommandProcessor.java   # Komut işleyici
  └── ClientHandler.java      # Sunucu tarafı yönlendirici
  └── PingThread.java         # Sunucular arası bağlantı kontrolü
  └── HealthLogger.java       # Loglama altyapısı
  └── CommandType.java        # Enum komut tanımları
  └── Version.java            # Sürüm bilgisi
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

- `v0.1` → Ocak 2024: Temel sunucu yapısı, istemci istekleri, Abone nesnesi, PingThread ile bağlantı kontrolü
- `v1.0` → Kasım 2025: Modüler mimari, enum geçişi, switch-case, thread senkronizasyonu, loglama, v0.1 hataları düzeltildi

---

## 📄 Lisans

MIT Lisansı – Dilediğiniz gibi kullanabilir, geliştirebilir ve paylaşabilirsiniz.
