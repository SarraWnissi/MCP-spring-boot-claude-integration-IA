# 🤖 Serveur MCP Spring Boot — Intégration Claude Desktop

![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![Spring AI](https://img.shields.io/badge/Spring%20AI-MCP-blue)
![Claude](https://img.shields.io/badge/Claude-Desktop-purple)
![License](https://img.shields.io/badge/License-MIT-lightgrey)

> **Serveur MCP (Model Context Protocol)** implémenté avec **Spring Boot** et **Spring AI** afin de permettre à **Claude Desktop** d’accéder à des outils métier personnalisés et d’interagir avec un système d’information local.

---

## 📑 Sommaire

* [Présentation](#-présentation)
* [Concepts MCP](#-concepts-mcp)
* [Architecture](#-architecture)
* [Prérequis](#-prérequis)
* [Installation & Build](#-installation--build)
* [Configuration Claude Desktop](#-configuration-claude-desktop)
* [Utilisation](#-utilisation)
* [Exemples de requêtes](#-exemples-de-requêtes)
* [Auteur](#-auteur)
* [Licence & Support](#-licence--support)

---

## 🎯 Présentation

Ce projet démontre comment créer un **serveur MCP en Java** capable d’exposer des **tools** utilisables par un agent IA (ici **Claude Desktop**).

### ✨ Objectifs

* Exposer des fonctionnalités métier via MCP
* Permettre à Claude Desktop d’interroger des données locales
* Montrer une intégration propre avec **Spring AI**

### 🧠 Cas d’usage implémenté

Gestion des cours du **Professeur Mohamed Youssfi** à travers deux tools :

* ✅ `get_all_courses` : retourner la liste complète des cours
* 🔍 `get_course_by_title` : rechercher un cours par son titre

Claude peut ensuite :

* Lister les cours disponibles
* Rechercher un cours précis
* Générer des fichiers (TXT, HTML, etc.)
* Créer des rapports formatés

---

## 🔌 Concepts MCP

### Qu’est-ce que le Model Context Protocol (MCP) ?

Le **MCP** est un protocole standardisé permettant aux **LLMs** (Claude, GPT, etc.) de communiquer avec des **outils externes** (APIs, fichiers, bases de données…).

### 🚧 Problème

Les modèles IA n’ont pas accès aux données privées ou locales.

### ✅ Solution MCP

MCP agit comme un **pont** entre :

* 🧠 Le raisonnement du LLM
* ⚙️ Les capacités d’action du système d’information

### 🔗 Schéma simplifié

```
┌────────────────────┐
│   Claude Desktop   │  ← Client MCP
│   (Agent IA)       │
└─────────┬──────────┘
          │ MCP
┌─────────▼──────────┐
│   Serveur MCP      │  ← Spring Boot
│   (ce projet)      │
└─────────┬──────────┘
          │
┌─────────▼──────────┐
│ Tools / Services   │
│ - Courses          │
│ - Fichiers         │
│ - APIs             │
└────────────────────┘
```

---

## 🏗️ Architecture

### 🛠️ Technologies

* **Java 21**
* **Spring Boot 3.x**
* **Spring AI (MCP)**
* **Maven**
* **Claude Desktop**

### 📂 Structure du projet

```
mcp-spring-boot-claude-integration/
├── src/main/java/org/ms/mymcpserver/
│   ├── MyMcpServerApplication.java
│   ├── model/
│   │   └── Course.java
│   └── services/
│       └── CourseService.java
├── src/main/resources/
│   └── application.properties
├── pom.xml
└── README.md
```

---

## 📋 Prérequis

* ☕ **Java 21+**
* 📦 **Maven 3.6+**
* 🖥️ **Claude Desktop**
* 💻 IDE recommandé : **IntelliJ IDEA**

---

## ⚙️ Installation & Build

### 1️⃣ Cloner le projet

```bash
git clone https://github.com/votre-username/mcp-spring-boot-claude-integration.git
cd mcp-spring-boot-claude-integration
```

### 2️⃣ Compiler le projet

```bash
mvn clean package -DskipTests
```

➡️ Le JAR sera généré dans :

```
target/my-mcp-server-0.0.1-SNAPSHOT.jar
```

### 3️⃣ Lancer le serveur manuellement

#### Windows (PowerShell)

```powershell
$env:JAVA_HOME="C:\Program Files\Java\jdk-21"
$env:PATH="$env:JAVA_HOME\bin;" + $env:PATH
java -jar target\my-mcp-server-0.0.1-SNAPSHOT.jar
```

#### Linux / macOS

```bash
export JAVA_HOME=/path/to/jdk-21
java -jar target/my-mcp-server-0.0.1-SNAPSHOT.jar
```

⛔ Arrêt : `Ctrl + C`

---

## ⚙️ Configuration Claude Desktop

### 📍 Emplacement du fichier

* **Windows**

```
C:\Users\VotreNom\AppData\Roaming\Claude\claude_desktop_config.json
```

* **macOS**

```
~/Library/Application Support/Claude/claude_desktop_config.json
```

* **Linux**

```
~/.config/Claude/claude_desktop_config.json
```

### 🧩 Exemple de configuration MCP

```json
{
  "mcpServers": {
    "filesystem": {
      "command": "cmd",
      "args": [
        "/c",
        "npx",
        "-y",
        "@modelcontextprotocol/server-filesystem",
        "C:/Users/chemin/Downloads",
        "C:/Users/chemin/Desktop"
      ]
    },
    "courseServer": {
      "command": "C:\\Program Files\\Java\\jdk-21\\bin\\java.exe",
      "args": [
        "-Dspring.ai.mcp.server.stdio=true",
        "-jar",
        "C:\\Users\\chemin\\IdeaProjects\\my-mcp-server\\target\\my-mcp-server-0.0.1-SNAPSHOT.jar"
      ]
    }
  }
}
```

⚠️ **Adapter les chemins selon votre machine**

---

## 🚀 Utilisation

### 1️⃣ Démarrer Claude Desktop

Redémarrer Claude Desktop après configuration.

### 2️⃣ Vérifier la connexion

* Paramètres → Développeur
* Vérifier que `courseServer` est actif

---

## 💬 Exemples de requêtes

### 📚 Lister tous les cours

```
Donne-moi tous les cours du professeur Mohamed Youssfi
```

### 🔎 Rechercher un cours

```
Donne-moi le cours sur Flutter
```

### 📝 Créer un fichier

```
Enregistre la liste des cours dans le fichier Test.txt sur le bureau
```

Claude utilisera :

* `courseServer` → récupération des données
* `filesystem` → création du fichier

---

## 👩‍💻 Auteur

Sarra Wnissi
ingenieure en informatique — Spring Boot, IA & Microservices

GitHub : https://github.com/SarraWnissi

LinkedIn : https://www.linkedin.com/in/sarra-ounissi-1881b8247/

## ⭐ Licence & Support

Ce projet est sous licence **MIT**.

Si ce projet t’a aidé ou inspiré, n’hésite pas à lui donner une ⭐ sur GitHub !
