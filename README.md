# Info-Loker-Job-Seeker
Proyek scaffold Android (Kotlin + Jetpack Compose) untuk menampilkan informasi lowongan kerja remote menggunakan Remotive API.

Asumsi yang saya pakai
- Teknologi: Kotlin + Jetpack Compose (native Android)
- Sumber data: Remotive public API (https://remotive.io/api/remote-jobs)
- Fitur awal: daftar pekerjaan, detail pekerjaan, pencarian sederhana
- Bahasa UI: Indonesia (label sederhana)

Catatan cepat
- Project ini berisi scaffolding minimal yang bisa dibuka di Android Studio.
- Anda memerlukan Android SDK (API 33+), JDK 11+, dan Gradle untuk membangun app.
- Untuk build dari command line (jika SDK tersedia):

```bash
./gradlew assembleDebug
```

Apa yang ditambahkan
- File Gradle dasar dan modul `app`
- Compose-based `MainActivity` dan screen sederhana
- Retrofit client untuk Remotive API
- Repository dan model data sederhana

Langkah berikutnya (direkomendasikan)
- Tambah ViewModel + Hilt/Dagger untuk arsitektur bersih
- Tambah Room untuk caching dan penyimpanan favorites
- Tambah test unit/UI
- Poles UI/branding

Jika Anda mau, saya bisa:
- Mencoba build (kemungkinan gagal jika SDK tidak tersedia di container) dan melaporkan hasil
- Menambahkan persistence (Room) untuk offline/favorites
- Menambahkan ViewModel + arsitektur yang lebih rapi

Beritahu saya langkah mana yang mau Anda lanjutkan.
