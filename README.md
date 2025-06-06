# 🎧 Shazam Clone in Java

A command-line Shazam clone built using Java, Spring Boot, and MongoDB. The application allows users to import audio files, extract their fingerprints, and match them against audio captured from a microphone using Fast Fourier Transform (FFT) and Locality Sensitive Hashing (LSH).

---

## 🚀 Features

🎙️ Record live audio from microphone

🎼 Import `.wav` audio files and extract unique fingerprints

🧠 Match audio clips to known songs using fingerprint hashes

📦 Persist fingerprints and metadata in MongoDB

📁 Batch import a folder of songs

🔍 Fuzzy matching with LSH for robustness

---

## 🗂️ Project Structure

shazam-clone/
├── src/
│ └── main/
│ ├── java/
│ │ └── com/shazamclone/audiomatch/
│ │ ├── AudioRecorder.java
│ │ ├── FFTUtil.java
│ │ ├── FingerprintService.java
│ │ ├── Application.java
│ │ ├── model/
│ │ │ ├── DataPoint.java
│ │ │ └── SongMetadata.java
│ │ └── repository/
│ │ ├── FingerprintRepository.java
│ │ └── SongRepository.java
│ └── resources/
│ └── application.properties
├── pom.xml
└── README.md


---

## ⚙️ Requirements

* Java 17+

* Maven

* MongoDB (local or cloud, default URI: `mongodb://localhost:27017`)

* FFmpeg (for advanced audio format conversion if needed)

---

## 🔧 Setup Instructions

1\. Clone the Repository

```bash
git clone https://github.com/your-username/shazam-clone.git
cd shazam-clone

2. Start MongoDB
# Local MongoDB
mongod

3. Configure MongoDB URI (if needed)
# src/main/resources/application.properties
spring.data.mongodb.uri=mongodb://localhost:27017/audiomatch

4. Build the Project
mvn clean install

5. Run the Application
mvn spring-boot:run

---

🎤 CLI Usage
Import Audio Files
java -jar target/shazamclone.jar import

Record and Match Live Audio
# Start microphone recording
java -jar target/shazamclone.jar 

---
🧠 How It Works
---


Fingerprinting: Each song is divided into chunks and analyzed using FFT.

Peak Picking: For each chunk, dominant frequencies are selected.

Hashing: A unique hash is generated from frequency peaks (LSH-style).

Storage: Hashes and metadata are saved to MongoDB.

Matching: New audio clips are fingerprinted and hashes matched from DB.


🛠 Tech Stack
Java 17

Spring Boot 3

MongoDB

Maven

JTransforms (for FFT)


📜 License
MIT License. Feel free to use, modify, and distribute.


🤝 Contributing
Pull requests and suggestions are welcome. Fork this repo and open a PR!

📬 Contact
Created by Ayush Sharma — feel free to reach out!

