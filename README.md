# 📱 KimeSeminarApp

Aplikasi mobile Android untuk pendaftaran dan manajemen seminar yang dibangun dengan **Kotlin** dan **Android Studio**.

## 🎯 Deskripsi Singkat

KimeSeminarApp adalah aplikasi pendaftaran seminar yang memungkinkan pengguna untuk:
- Membuat akun dan login secara aman
- Melihat daftar seminar yang tersedia
- Mengisi form pendaftaran untuk mengikuti seminar
- Melihat hasil pendaftaran seminar

Aplikasi ini menggunakan **SharedPreferences** untuk penyimpanan data lokal di perangkat pengguna.

---

## ✨ Fitur Utama

| Fitur | Deskripsi |
|-------|-----------|
| **🔐 Login & Registrasi** | Sistem autentikasi dengan validasi email dan password real-time |
| **📋 Session Management** | Pengguna tetap login meskipun aplikasi ditutup |
| **📊 Dashboard Seminar** | Menampilkan daftar seminar yang tersedia |
| **📝 Form Pendaftaran** | Formulir interaktif untuk mendaftar seminar |
| **✅ Hasil Pendaftaran** | Menampilkan status dan hasil pendaftaran |

---

## 📋 Informasi Login & Akun

**Penting**: Aplikasi ini menggunakan **SharedPreferences** untuk penyimpanan lokal. **Tidak ada akun default** pada instalasi pertama.

### 🔑 Cara Pertama Kali Menggunakan:

#### 1️⃣ Daftar Akun Baru
```
1. Buka aplikasi → Layar Login
2. Klik teks "Belum punya akun? Daftar di sini"
3. Isi data:
   - Nama: Nama lengkap Anda
   - Email: Alamat email yang valid
   - Password: Minimal 6 karakter
4. Klik tombol "Daftar"
```

#### 2️⃣ Login
```
1. Gunakan Email dan Password yang baru saja dibuat
2. Klik tombol "Login"
3. Jika berhasil, akan masuk ke Dashboard Seminar
```

---

## 🏗️ Struktur Project

```
KimeSeminarApp/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/kime/seminar/
│   │   │   │   ├── SplashActivity.kt          # Activity splash screen
│   │   │   │   ├── LoginActivity.kt           # Activity login
│   │   │   │   ├── RegisterActivity.kt        # Activity registrasi
│   │   │   │   ├── HomeActivity.kt            # Dashboard seminar
│   │   │   │   ├── FormActivity.kt            # Form pendaftaran
│   │   │   │   ├── ResultActivity.kt          # Hasil pendaftaran
│   │   │   │   └── SessionManager.kt          # Manager session & penyimpanan
│   │   │   ├── res/                           # Resources (layouts, drawables, strings)
│   │   │   └── AndroidManifest.xml            # Manifest aplikasi
│   │   └── test/                              # Unit tests
│   ├── build.gradle                           # Gradle configuration
│   └── proguard-rules.pro                     # ProGuard rules
├── build.gradle                               # Root gradle
├── gradle.properties                          # Gradle properties
├── settings.gradle                            # Gradle settings
└── README.md                                  # File ini
```

---

## 🛠️ Stack Teknologi

| Komponen | Versi |
|----------|-------|
| **Android Min SDK** | 26 (Android 8.0 Oreo) |
| **Android Target SDK** | 34 (Android 14) |
| **Bahasa Pemrograman** | Kotlin |
| **Build System** | Gradle |
| **Java Version** | 17 |

### 📦 Dependencies Utama

- **AndroidX Core**: KTX extensions
- **AndroidX AppCompat**: Backward compatibility
- **Material Design**: Google Material UI components
- **ConstraintLayout**: Layout management
- **CardView**: UI components
- **Core SplashScreen**: Android 12+ splash screen API
- **CircleImageView**: Custom view untuk gambar circular

---

## ⚙️ Spesifikasi Teknis

### Build Features
- ✅ **View Binding**: Enabled
- 🎨 **Layout Binding**: Tersedia untuk XML
- 🔧 **Code Generation**: Kotlin Annotation Processing

### Gradle Configuration
```groovy
minSdk: 26
targetSdk: 34
versionCode: 1
versionName: 1.0.0
```

### Compiler Configuration
- Java Compatibility: VERSION_17
- Kotlin JVM Target: 17

---

## 📱 Panduan Instalasi

### Requirements:
- Android Studio (versi terbaru)
- Android SDK 26 atau lebih tinggi
- Gradle 8.0 atau lebih tinggi

### Langkah Instalasi:

1. **Clone atau buka project**
   ```bash
   # Jika clone dari git
   git clone <repository-url>
   cd KimeSeminarApp
   ```

2. **Sinkronisasi Gradle**
   ```
   Android Studio → File → Sync Now
   ```

3. **Build APK**
   ```
   Android Studio → Build → Build Bundle(s)/APK(s) → Build APK(s)
   ```

4. **Run di Emulator/Device**
   ```
   Android Studio → Run (Shift + F10)
   Atau klik tombol Run ▶️
   ```

---

## 🎨 Alur Aplikasi

```
┌─────────────┐
│   Splash    │ (Loading screen)
└──────┬──────┘
       │
       ▼
┌──────────────────┐
│  Login/Register? │
└────┬─────────┬───┘
     │         │
  Login    Register
     │         │
     └────┬────┘
          ▼
    ┌────────────┐
    │ Dashboard  │ (Lihat seminar)
    └────┬───────┘
         │
         ▼
    ┌──────────┐
    │   Form   │ (Isi data pendaftaran)
    └────┬─────┘
         │
         ▼
    ┌────────────┐
    │   Result   │ (Lihat status)
    └────────────┘
```

---

## 🔒 Keamanan & Data

- **Penyimpanan Data**: SharedPreferences (local device storage)
- **Password**: Disimpan secara lokal (enkripsi basic)
- **Session**: Persistent (tersimpan sampai user logout)
- **Akses Offline**: Aplikasi bisa digunakan tanpa internet untuk fitur lokal

⚠️ **Catatan Keamanan**: Untuk production, gunakan encrypted storage dan server backend.

---

## 📝 Ringkasan Activity

| Activity | Fungsi |
|----------|--------|
| `SplashActivity` | Menampilkan splash screen saat app dibuka |
| `LoginActivity` | Form login dan link ke registrasi |
| `RegisterActivity` | Form registrasi akun baru |
| `HomeActivity` | Dashboard menampilkan daftar seminar |
| `FormActivity` | Form untuk mendaftar seminar |
| `ResultActivity` | Menampilkan hasil pendaftaran |
| `SessionManager` | Mengelola session dan penyimpanan data |

---

## 🐛 Known Issues & Limitations

- Data hanya tersimpan lokal di device
- Tidak ada sinkronisasi antar device
- Tidak ada notifikasi push
- Tidak ada fitur cloud backup

---

## 📞 Support & Kontribusi

Jika ada pertanyaan atau bug, silakan buat issue di repository ini.

---

## 📄 Lisensi

Project ini dibuat untuk tujuan edukatif.

---

**Last Updated**: April 22, 2026
**Version**: 1.0.0
