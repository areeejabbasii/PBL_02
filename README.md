# Online Examination System

### A Professional Desktop Application for Online Examinations

**Built with Java Swing • Professional UI Design • Clean White Theme**

---

## Overview

A comprehensive desktop application for conducting online examinations, built as a university project. The system features a **clean, professional white theme interface** with university-appropriate design elements, perfect for academic presentations and demonstrations.

### Key Features

- **User Authentication**: Secure login and registration with clean, professional forms
- **Professional Interface**: Clean white theme with university-appropriate design elements
- **Smart Exam System**: Timer-based exams with question bookmarking and intuitive navigation
- **Performance Analytics**: Track your progress with detailed statistics and modern data visualization
- **Quick Practice Mode**: Sample question sessions for exam preparation
- **Question Categories**: Organized by subject with clear difficulty indicators
- **Result Tracking**: Comprehensive scoring with professional result displays
- **Cross-Platform**: Works on Windows, macOS, and Linux with consistent appearance

---

## Quick Start

**Database setup (first time):** see [SQL_SERVER_CONNECTION_GUIDE.md](SQL_SERVER_CONNECTION_GUIDE.md)

### Windows Users

```cmd
cd c:\Users\HP\OneDrive\Desktop\oop
run.bat
```

### Linux/Mac Users

```bash
# Navigate to project directory
cd /path/to/oop

# Create bin directory
mkdir -p bin

# Compile all Java files
javac -cp "src" -d "bin" src/main/Main.java src/gui/*.java src/model/*.java src/service/*.java src/database/*.java src/utils/*.java

# Run the application
java -cp "bin" main.Main
```

---

## System Requirements

| Component | Requirement |
|-----------|-------------|
| **Java JDK** | Version 8 or higher |
| **Operating System** | Windows/Linux/macOS |
| **Memory** | Minimum 512MB RAM |
| **Storage** | 50MB free space |

---

## Project Structure

```
OnlineExamSystem/
├── src/
│   ├── main/
│   │   └── Main.java                 # Application entry point
│   ├── gui/                          # Presentation Layer (Professional White Theme)
│   │   ├── LoginFrame.java           # Authentication UI (Clean blue/white design)
│   │   ├── DashboardFrame.java       # Main dashboard (Professional cards with icons)
│   │   ├── ExamFrame.java            # Exam interface (Clean timer + navigation)
│   │   ├── ResultFrame.java          # Results display (Professional analytics)
│   │   └── RegisterFrame.java        # User registration (Clean form design)
│   ├── model/                        # Data Models (Domain Layer)
│   │   ├── User.java                 # User entity (Authentication)
│   │   ├── Question.java             # Question entity (Exam content)
│   │   └── Result.java               # Result entity (Performance tracking)
│   ├── database/                     # Data Access Layer
│   │   ├── DBConnection.java         # SQL Server connection
│   │   └── QueryManager.java         # Database operations (CRUD)
│   ├── service/                      # Business Logic Layer
│   │   ├── LoginService.java         # Authentication logic
│   │   ├── ExamService.java          # Exam management & scoring
│   │   └── ResultService.java        # Result processing & analytics
│   └── utils/                        # Utility Classes
│       ├── TimerUtil.java            # Timer functionality
│       └── ValidationUtil.java       # Input validation & sanitization
├── lib/                              # SQL Server JDBC driver (see lib/README.txt)
├── scripts/enable-sql-server.ps1     # One-time SQL Server TCP setup
├── bin/                              # Compiled classes (auto-generated)
├── database.sql                      # Database schema & sample questions
├── run.bat                           # Compile and run (Windows)
├── SQL_SERVER_CONNECTION_GUIDE.md    # Step-by-step DB connection guide
└── README.md
```

---

## Professional UI Design System

### Clean White Theme Color Palette

| **Backgrounds** | **Professional Colors** | **Text Colors** | **Accent Colors** |
|-----------------|-------------------------|-----------------|-------------------|
| Primary: `#FFFFFF` | Primary Blue: `#2563EB` | Primary: `#111827` | Purple: `#A855F7` |
| Secondary: `#F9FAFB` | Success Green: `#22C55E` | Secondary: `#6B7280` | Cyan: `#06B6D4` |
| Cards: `#FFFFFF` | Warning Orange: `#F97316` | Muted: `#9CA3AF` | Yellow: `#EAB308` |
| Borders: `#E5E7EB` | Error Red: `#EF4444` | Links: `#2563EB` | Pink: `#EC4899` |

### Professional Button Design

| Button Type | Color | Usage | Design Philosophy |
|-------------|-------|-------|-------------------|
| **Primary Action** | Professional Blue | Sign In, Submit | Trust & Authority |
| **Success Action** | Clean Green | Create Account, Confirm | Growth & Success |
| **Secondary Action** | White with Blue Border | Back, Cancel | Subtle & Clean |
| **Warning Action** | Orange Accent | Important Actions | Attention & Care |

---

## Authentication & Test Data

### Ready-to-Use Credentials

| Role | Username | Password | Description |
|------|----------|----------|-------------|
| **Administrator** | `admin` | `admin123` | Full system access & management |
| **Test Student** | `test` | `test123` | Standard exam-taking privileges |
| **Demo User** | `demo` | `demo123` | Demonstration purposes |

### Sample Question Bank

The database includes **5 carefully crafted questions** covering diverse topics:

| Category | Topic | Difficulty | Coverage |
|----------|-------|------------|----------|
| **Geography** | World Capitals | Beginner | General Knowledge |
| **Programming** | Basic Concepts | Intermediate | Computer Science |
| **Java** | Language Specifics | Intermediate | Technical Skills |
| **Mathematics** | Arithmetic | Beginner | Quantitative Reasoning |

---

## Features

### Professional Design Elements
- **Clean White Background**: Professional appearance suitable for university presentations
- **Modern Typography**: Segoe UI font family for excellent readability and modern look
- **Professional Color Palette**: University-appropriate blues, greens, and subtle accent colors
- **Consistent Branding**: Unified design language across all application screens

### Enhanced User Experience
- **Intuitive Navigation**: Clear, easy-to-understand interface elements
- **Hover Effects**: Interactive feedback for better user engagement
- **Professional Forms**: Clean input fields with proper validation and feedback
- **Modern Buttons**: Well-designed buttons with appropriate sizing and spacing

### Professional Results Display
- **Clean Result Cards**: Modern, card-based result presentation
- **Color-Coded Performance**: Visual indicators for different score ranges
- **Professional Tables**: Clean, well-organized data presentation
- **University-Appropriate Design**: Perfect for academic demonstrations

---

## License

This project is open source and available under the MIT License.

---

<div align="center">

**Built with care for the Academic Community**

*Perfect for University Presentations • Professional Design • Easy to Demonstrate*

</div>