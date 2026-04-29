# KimeSeminarApp 🎓

**KimeSeminarApp** adalah aplikasi manajemen pendaftaran seminar berbasis Android yang dirancang untuk memudahkan mahasiswa dan peserta umum dalam mencari, menjadwalkan, dan mendaftar ke berbagai seminar akademik maupun profesional. Aplikasi ini mengintegrasikan database cloud **Supabase** dengan penyimpanan lokal **Room Database** untuk memastikan pengalaman pengguna yang cepat dan reliabel, bahkan dalam kondisi offline.

## 🚀 Fitur Utama

-   **Otentikasi Pengguna**: Login dan Register aman menggunakan Supabase Auth.
-   **Jadwal Seminar Terkini**: Menampilkan daftar seminar terbaru yang diambil langsung dari server cloud.
-   **Sinkronisasi Offline (Room Database)**: Data seminar dan riwayat pendaftaran disimpan secara lokal, memungkinkan pengguna melihat jadwal meskipun tanpa koneksi internet.
-   **Form Pendaftaran Dinamis**: Pendaftaran seminar yang mudah dengan validasi data real-time (Nama, Email, No. HP, Jenis Kelamin).
-   **Riwayat Pendaftaran**: Pantau semua seminar yang telah diikuti dalam satu tempat.
-   **Detail Seminar Mendalam**: Informasi lengkap mengenai pembicara, waktu, lokasi (fisik/Zoom), kuota peserta, ringkasan, dan materi pembahasan.
-   **Fitur Berbagi (Share)**: Bagikan informasi seminar ke teman atau media sosial langsung dari aplikasi.
-   **Pencarian Seminar**: Temukan seminar yang relevan dengan cepat menggunakan fitur filter pencarian.
-   **Tiket Digital & QR Code**: (Dalam Pengembangan) Kemudahan check-in seminar menggunakan kode QR.

## 🔄 Alur Aplikasi

1.  **Splash Screen**: Menampilkan logo aplikasi saat startup.
2.  **Login/Register**: Pengguna masuk ke akun atau membuat akun baru.
3.  **Dashboard (Home)**: Menampilkan sambutan personal, info pendaftaran terakhir, dan beberapa seminar populer.
4.  **Jadwal Seminar**: Menjelajahi daftar lengkap seminar yang tersedia. Data disinkronkan otomatis dari Supabase ke Room DB lokal.
5.  **Detail & Daftar**: Membaca detail seminar. Jika tertarik, pengguna menekan tombol "Daftar" untuk mengisi form.
6.  **Konfirmasi & Hasil**: Setelah submit, muncul dialog konfirmasi. Jika setuju, data disimpan ke cloud dan lokal, lalu dialihkan ke halaman sukses.
7.  **Riwayat**: Pengguna dapat melihat daftar pendaftaran sukses di tab "Riwayat".
8.  **Profil**: Mengelola informasi akun dan melakukan logout.

## 🏗️ Struktur Proyek

```text
KimeSeminarApp/
├── app/
│   ├── src/main/kotlin/com/kime/seminar/      # Logika Bisnis & UI (Kotlin)
│   │   ├── database/                          # Room Persistence & Repository
│   │   ├── ...                                # Fragments, Activities, Adapters
│   ├── src/main/res/                          # Resource (Layout, Drawable, Value)
│   │   ├── layout/                            # Desain Antarmuka (XML)
│   │   ├── navigation/                        # Navigasi Jetpack (nav_graph.xml)
│   └── build.gradle                           # Konfigurasi Dependensi & Android
├── README.md                                  # Dokumentasi Proyek
└── settings.gradle                            # Konfigurasi Module
```

## 🛠️ Teknologi yang Digunakan

-   **Bahasa**: Kotlin
-   **UI Framework**: Jetpack Compose / View Binding & Material Components
-   **Navigasi**: Jetpack Navigation Component
-   **Database Cloud**: [Supabase](https://supabase.com/) (Postgrest, Auth, Storage)
-   **Database Lokal**: Room Persistence Library
-   **Networking**: Ktor Client & Kotlinx Serialization
-   **Image Loading**: Coil
-   **Utils**: ViewBinding, Coroutines, Lifecycle (LifecycleScope)

## 📺 Demo Video
Link YouTube: [Coming Soon] ⏳

---

## 👨‍💻 Informasi Pengembang

-   **Nama**: Ahmad Kurnia
-   **NIM**: 24552011297
-   **Kelas**: TIF 24 RP CNS D
-   **Program Studi**: Teknik Informatika
-   **Universitas**: Universitas Teknologi Bandung
-   **Semester**: 4

---
© 2024 Ahmad Kurnia - KimeSeminarApp. All Rights Reserved.
