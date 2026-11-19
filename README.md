# 🎵 MusicWave

A modern, attractive desktop music player built with **Java Swing** and **SQLite**,
featuring secure user authentication and a sleek dark-themed interface.

![Java](https://img.shields.io/badge/Java-17+-orange?logo=java)
![Swing](https://img.shields.io/badge/GUI-Swing-blue)
![SQLite](https://img.shields.io/badge/Database-SQLite-green)
![License](https://img.shields.io/badge/License-MIT-yellow)

---

## ✨ Features

- 🔐 **Secure Authentication** — Sign Up / Sign In with SHA-256 password hashing
- 💾 **SQLite Backend** — Zero-config persistent user storage
- 🎨 **Modern Dark UI** — Gradient buttons, rounded inputs, premium look
- 🎧 **Music Playback** — Load a folder and play `.wav` files
- 🚀 **Smooth Navigation** — Login ↔ Sign-up transitions

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| Language | Java 17+ |
| GUI | Java Swing |
| Database | SQLite (via `sqlite-jdbc`) |
| Security | SHA-256 password hashing |

## ⚙️ Getting Started

### Prerequisites
- Java JDK 17 or higher
- IntelliJ IDEA / Eclipse (any Java IDE)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/YOUR_USERNAME/MusicWave.git
   cd MusicWave
   ```

2. **Add the SQLite JDBC driver**
   - Download `sqlite-jdbc-3.45.0.0.jar` from
     [xerial/sqlite-jdbc releases](https://github.com/xerial/sqlite-jdbc/releases)
   - Place it in the `lib/` folder
   - Add it to your IDE's classpath (Project Structure → Libraries)

3. **Run the app**
   - Open the project in your IDE
   - Run `Main.java`
   - The database `musicapp.db` is created automatically on first launch 🎉

---

## 📂 Project Structure

```
MusicApp/
├── src/
│   ├── Main.java
│   ├── db/DatabaseHelper.java
│   ├── ui/
│   │   ├── LoginFrame.java
│   │   ├── SignupFrame.java
│   │   └── MusicPlayerFrame.java
│   └── utils/PasswordUtil.java
├── lib/sqlite-jdbc-3.45.0.0.jar
└── README.md
```
## 🗺️ Roadmap

- [x] User sign up / sign in
- [x] Dark themed UI
- [ ] MP3 playback support (JLayer)
- [ ] Album art & playlist saving
- [ ] Volume slider & seek bar

## 👤 Author
- GitHub: https://github.com/Sruthikumari26
- Email: sruthikumari92@gmail.com

⭐ If you like this project, please give it a star!
