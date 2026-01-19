<div align="center">

# 🎫 ZeroAttente

### *Say Goodbye to Waiting in Line*

A modern queue management application enabling organizations to issue digital tickets and clients to monitor their wait time remotely.

[![Angular](https://img.shields.io/badge/Angular-20-DD0031? style=for-the-badge&logo=angular&logoColor=white)](https://angular.dev/)
[![Java](https://img.shields.io/badge/Java-Spring%20Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/)
[![TypeScript](https://img.shields.io/badge/TypeScript-5.x-3178C6?style=for-the-badge&logo=typescript&logoColor=white)](https://www.typescriptlang.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow? style=for-the-badge)](LICENSE)

[Features](#-features) • [Tech Stack](#-tech-stack) • [Getting Started](#-getting-started) • [Screenshots](#-screenshots) • [Contributing](#-contributing)

---

</div>

## ✨ Features

<table>
<tr>
<td width="50%">

### 🏢 For Organizations
- **Digital Ticket Issuance** — Generate and manage queue tickets digitally
- **Real-time Dashboard** — Monitor queue status and analytics
- **Organization Profiles** — Customize your organization's presence
- **Admin Controls** — Full control over queue management

</td>
<td width="50%">

### 👥 For Clients
- **Remote Monitoring** — Track your wait time from anywhere
- **Digital Tickets** — Receive and manage tickets on your device
- **Notifications** — Get updates when your turn is approaching
- **Multiple Organizations** — Access queues from various organizations

</td>
</tr>
</table>

### 🔐 Security & Reliability
- Secure authentication for both admins and clients
- Protected API endpoints with credential interceptors
- Industry-standard data privacy measures

---

## 🛠 Tech Stack

<div align="center">

| Frontend | Backend | Styling |
|: --------:|:-------:|: -------:|
| ![Angular](https://img.shields.io/badge/Angular-DD0031?style=flat-square&logo=angular&logoColor=white) | ![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white) | ![TailwindCSS](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=flat-square&logo=tailwind-css&logoColor=white) |
| ![TypeScript](https://img.shields.io/badge/TypeScript-3178C6?style=flat-square&logo=typescript&logoColor=white) | ![Spring](https://img.shields.io/badge/Spring_Boot-6DB33F?style=flat-square&logo=spring&logoColor=white) | ![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=flat-square&logo=css3&logoColor=white) |

</div>

---

## 🚀 Getting Started

### Prerequisites

- **Node.js** (v18 or higher)
- **Angular CLI** (v20+)
- **Java JDK** (v17 or higher)
- **Maven** or **Gradle**

### Installation

#### 1️⃣ Clone the repository

```bash
git clone https://github.com/fadielhuichet/ZeroAttente.git
cd ZeroAttente
```

#### 2️⃣ Backend Setup

```bash
cd GetTicket
./mvnw spring-boot:run
```

The backend server will start on `http://localhost:8080`

#### 3️⃣ Frontend Setup

```bash
cd GetTicketFront
npm install
ng serve
```

The frontend will be available at `http://localhost:4200`

---

## 📁 Project Structure

```
ZeroAttente/
├── 📂 GetTicket/              # Java Spring Boot Backend
│   ├── src/
│   │   ├── main/
│   │   └── test/
│   └── pom.xml
│
├── 📂 GetTicketFront/         # Angular Frontend
│   ├── src/
│   │   ├── app/
│   │   │   ├── admin/         # Admin dashboard & profile
│   │   │   ├── auth/          # Login & signup components
│   │   │   ├── client/        # Client ticket & profile views
│   │   │   ├── home/          # Landing page
│   │   │   ├── organisations/ # Organization listings & details
│   │   │   ├── models/        # TypeScript interfaces
│   │   │   ├── services/      # API services
│   │   │   └── shared/        # Header, footer components
│   │   └── assets/
│   └── package.json
│
└── README.md
```

---

## 🎯 Use Cases

| Scenario | Solution |
|----------|----------|
| 🏥 **Healthcare** | Patients can wait comfortably and receive notifications when their turn approaches |
| 🏦 **Banking** | Customers can browse while monitoring their queue position remotely |
| 🍽️ **Restaurants** | Diners can explore the area instead of waiting at the venue |
| 🏛️ **Government Services** | Citizens can manage their time efficiently during administrative visits |

---

## 🤝 Contributing

Contributions are what make the open source community amazing! Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.

---

## 📬 Contact

**Fadiel Huichet** — [@fadielhuichet](https://github.com/fadielhuichet)

Project Link: [https://github.com/fadielhuichet/ZeroAttente](https://github.com/fadielhuichet/ZeroAttente)

---

<div align="center">

### ⭐ Star this repo if you find it useful!

Made with ❤️ for a queue-free world

</div>
