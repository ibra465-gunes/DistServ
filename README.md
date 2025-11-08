Harika İbrahim! İşte DistServ projesinin ilk hali (`v0.1`) için doğrudan GitHub’a kopyalanabilir, sade ve profesyonel bir `README.md` dosyası. Proje tanımının hemen altına yapılış tarihi de eklendi ✅

---

```markdown
# DistServ

Java tabanlı temel bir dağıtık sunucu sistemi. İstemci mesajlarını işler, sunucular arası senkronizasyon ve durum paylaşımı sağlar.

**Yapılış Tarihi:** Ocak 2024

---

## 🧩 Sistem Bileşenleri

- **Client.java**  
  Kullanıcıdan mesaj gönderir. Mesajlar sırayla Server1, Server2 ve Server3’e iletilir.

- **Server1.java**, **Server2.java**, **Server3.java**  
  Her biri kendi portunda çalışır. Gelen mesajları işler, `Abone` nesnesini diğer sunuculara göndererek durumu senkronize eder.  
  Ayrıca `PingThread` ile diğer sunuculara periyodik bağlantı kontrolü yapar.

- **Abone.java**  
  Tüm taraflar arasında taşınan ortak veri modelidir. Abonelik ve giriş durumu ile son güncelleme zamanını içerir.

---

## 🔧 Teknik Detaylar

| Sunucu   | Port | Pinglediği Sunucular         |
|----------|------|------------------------------|
| Server1  | 5001 | Server2 (5002), Server3 (5003) |
| Server2  | 5002 | Server1 (5001), Server3 (5003) |
| Server3  | 5003 | Server1 (5001), Server2 (5002) |

- Veri iletimi: `ObjectOutputStream` ile `Abone` nesnesi gönderilir  
- Ping kontrolü: Her 10 saniyede bir diğer sunuculara bağlantı denenir  
- Hata yönetimi: `System.out.println()` ile konsola yazılır  
- Zaman kontrolü: `EpochMiliSeconds` ile güncellik karşılaştırması yapılır

---

## 📦 Mesaj Protokolü

| Mesaj       | Açıklama               |
|-------------|------------------------|
| `ABONOL`    | Abone olma isteği      |
| `ABONIPTAL` | Abonelik iptali        |
| `GIRIS`     | Giriş bildirimi        |
| `CIKIS`     | Çıkış bildirimi        |
| `50 HATA`   | İşlem geçersiz         |
| `55 TAMM`   | İşlem başarılı         |
| `99 HATA`   | Hatalı durum bildirimi |

---

## 📁 Dosya Yapısı

```
src/
├── Client.java
├── Server1.java
├── Server2.java
├── Server3.java
└── Abone.java
```

---

## 🏁 Başlatmak için

Her sunucuyu ayrı terminalde başlat:

```bash
javac Server1.java
java Server1

javac Server2.java
java Server2

javac Server3.java
java Server3

javac Client.java
java Client
```

---

## 📌 Versiyonlar

- `v0.1` → Ocak 2024: Temel sunucu yapısı, istemci mesajları, Abone nesnesi, PingThread ile sunucular arası bağlantı kontrolü

---

## 📬 Katkı ve Gelişim

Bu proje, sunucular arası veri senkronizasyonu ve sistem davranışı analizine yönelik olarak geliştirilmektedir. İlerleyen versiyonlarda log mimarisi, katmanlı yapı ve çok istemcili sistem gibi özellikler eklenecektir.
