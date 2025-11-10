<div align="center">

# ⚡ MarketFlow

### *Simulation/Game of a Chain of Water Powerplants*

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.java.com/)
[![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-Not_Specified-lightgrey?style=for-the-badge)](https://github.com/luggers-z/marketflow)

![Last Updated](https://img.shields.io/github/last-commit/luggers-z/marketflow?style=flat-square&color=blue)
![Repo Size](https://img.shields.io/github/repo-size/luggers-z/marketflow?style=flat-square&color=green)
![Forks](https://img.shields.io/github/forks/luggers-z/marketflow?style=flat-square&color=orange)

---

### Project Statistics

<table>
<tr>
<td align="center">
<img src="https://img.shields.io/badge/Total_Commits-70+-blue?style=for-the-badge" alt="Commits"/>
<br><strong>Commits</strong>
</td>
<td align="center">
<img src="https://img.shields.io/badge/Created-April_2025-green?style=for-the-badge" alt="Created"/>
<br><strong>Project Age</strong>
</td>
<td align="center">
<img src="https://img.shields.io/badge/Watchers-1-yellow?style=for-the-badge" alt="Watchers"/>
<br><strong>Watchers</strong>
</td>
<td align="center">
<img src="https://img.shields.io/badge/Forks-1-orange?style=for-the-badge" alt="Forks"/>
<br><strong>Forks</strong>
</td>
</tr>
</table>

### Language Distribution


<table>
<tr>
<td align="center" width="50%">
<h3>☕ Java</h3>
<alt="Java 89.1%"/>
<br><sub><strong>1498 lines</strong></sub>
</td>
<td align="center" width="50%">
<h3>🎨 <br/>CSS</h3>
<alt="CSS 10.9%"/>
<br><sub><strong>231 lines</strong></sub>
</td>
</tr>
</table>

</div>

---

## About MarketFlow

**MarketFlow** is an interactive simulation and gaming platform that models the complex dynamics of a hydroelectric powerplant chain. Experience the intricate balance between water management, energy production, and market economics in a realistic simulation environment.

###  Key Features

-  **Multi-Plant Management** - Control and optimize a chain of interconnected water powerplants
-  **Realistic Water Flow Dynamics** - Simulate realistic water flow physics and reservoir management
-  **Energy Production Simulation** - Generate power based on water flow rates and plant efficiency
-  **Market Economics** - Navigate dynamic energy market prices and demand
-  **Interactive Gameplay** - Engage with an intuitive interface for strategic decision-making

---

## Getting Started

### Prerequisites

- **Java Development Kit (JDK)** 25
  - Download from [Eclipse Temurin](https://adoptium.net/) or [Oracle](https://www.oracle.com/java/technologies/downloads/)
- **Apache Maven** 3.6+
- Your favorite IDE (IntelliJ IDEA recommended)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/luggers-z/marketflow.git
   cd marketflow
   ```

2. **Run directly**
   ```bash
   mvn javafx:run
   ```

3. **Build a fat JAR (executable JAR with all dependencies)**
   
   Using the build script (recommended):
   ```bash
   ./build-jar.sh
   ```
   
   Or manually with Maven:
   ```bash
   mvn clean package
   ```
   
   This creates `target/marketflow-1.0.0-SNAPSHOT.jar` which can be run with:
   ```bash
   java -jar target/marketflow-1.0.0-SNAPSHOT.jar
   ```
   
   **Cross-Platform Support:** The fat JAR includes JavaFX native libraries for Windows, macOS, and Linux, so you can share the same JAR file with friends on any platform!
   
   **Requirements:** 
   - Building requires Java 25. The build script will automatically detect and use Java 25 if available.
   - Running the JAR requires Java 25 installed on the target system.

4. **Optional: Build a jlink image**
   ```bash
   mvn javafx:jlink
   ```
---

##  Project Structure

```
marketflow/
├── 📂 src/               # Source code directory
│   ├── 📂 main/          # Main application code
│   └── 📂 resources/     # Application resources & assets
├── 📄 pom.xml            # Maven configuration
├── 📄 README.md          # This file
└── 📄 .gitignore         # Git ignore rules
```

---

##  Built With

<div align="center">

| Technology | Purpose |
|:----------:|:-------:|
| ![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=openjdk&logoColor=white) | Core Programming Language |
| ![Maven](https://img.shields.io/badge/Maven-C71A36?style=flat&logo=apache-maven&logoColor=white) | Build & Dependency Management |
| ![CSS](https://img.shields.io/badge/CSS3-1572B6?style=flat&logo=css3&logoColor=white) | UI Styling |

</div>

---

##  Repository Insights

<div align="center">


###  Recent Activity

The project has seen **consistent development** with regular commits and updates throughout 2025.
<br/>As it is a W-Seminar project, there are no more Updates coming in the forseeable Future


</div>

---

##  License

This project currently does not have a specified license. Please contact the repository owner for usage permissions.

---

##  Author

<div align="center">

**Lukas Zellner**

[![GitHub](https://img.shields.io/badge/GitHub-luggers--z-181717?style=for-the-badge&logo=github)](https://github.com/luggers-z)

</div>

---

## Contact & Support

Have questions or suggestions? Feel free to:

-  [Open an Issue](https://github.com/luggers-z/marketflow/issues)
-  Start a [Discussion](https://github.com/luggers-z/marketflow/discussions)
-  Star this repository if you find it helpful!

---

<div align="center">


**Made with Love and no AI**

*Harnessing the power of water, one Powerplant at a time* 💧⚡

</div>
