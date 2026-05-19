# 🎯 AI-Powered Career Skill Gap Analysis Platform

A full-stack web application that analyzes users' current skills against their career goals and provides AI-powered personalized learning roadmaps.

## 🌐 Live Demo
- **Frontend**: [skillgap-ai.netlify.app]
- **Backend API**: [https://skillgap-backend-nhir.onrender.com](https://skillgap-backend-nhir.onrender.com)

## 🚀 Technologies Used

### Backend
- **Spring Boot 3.2.0** - REST API Framework
- **MySQL 8.x** - Database
- **Spring Security + JWT** - Authentication & Authorization
- **JPA/Hibernate** - ORM
- **BCrypt** - Password Encryption
- **Maven** - Build Tool

### Frontend
- **React 19** with Hooks
- **React Router** - Routing
- **Axios** - HTTP Client
- **Vite** - Build Tool
- **Modern CSS** - Premium UI Design

### AI Engine
- Custom skill gap analysis algorithm
- Priority-based learning recommendation system
- Prerequisite-aware roadmap generation

## 📁 Project Structure

```
project/
├── career-skill-gap-backend/     # Spring Boot Backend
│   ├── src/main/java/com/skillgap/
│   │   ├── config/               # Security, CORS, Data Init
│   │   ├── controller/           # REST Controllers
│   │   ├── dto/                  # Data Transfer Objects
│   │   ├── entity/               # JPA Entities
│   │   ├── repository/           # Database Repositories
│   │   ├── security/             # JWT Security
│   │   └── service/              # Business Logic
│   └── src/main/resources/
│       └── application.properties
│
└── career-skill-gap-frontend/    # React Frontend
    ├── src/
    │   ├── components/
    │   │   ├── auth/             # Login, Register
    │   │   ├── dashboard/        # Dashboard, ProfileSetup
    │   │   ├── analysis/         # SkillGap, Roadmap
    │   │   └── common/           # Navbar
    │   ├──  context/              # Auth Context
    │   ├── services/             # API Services
    │   └── utils/                # Axios Config
    └── package.json
```

## ⚙️ Prerequisites

Before you begin, ensure you have installed:

- **Java 17** or higher
- **Node.js 18+** and npm
- **MySQL 8.x**
- **Maven** (or use Spring Boot Maven wrapper)

## 🔧 Setup Instructions

### 1. Database Setup

```sql
-- Create database (application creates it automatically, but you can create manually)
CREATE DATABASE career_skill_gap_db;

-- Default credentials in application.properties:
-- username: root
-- password: root
-- Update these in src/main/resources/application.properties if needed
```

### 2. Backend Setup

```bash
cd career-skill-gap-backend

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run

# Backend will start on http://localhost:8080
# Database tables will be created automatically
# Seed data will be loaded on first run
```

### 3. Frontend Setup

```bash
cd career-skill-gap-frontend

# Install dependencies
npm install

# Start development server
npm run dev

# Frontend will start on http://localhost:3000
```

## 🎮 Usage

### Demo Account
- **Email**: `admin@skillgap.com`
- **Password**: `admin123`

### User Flow

1. **Register/Login**
   - Create a new account or use demo account
   - JWT token is stored for authenticated requests

2. **Profile Setup**
   - Select target career role (e.g., "Backend Developer")
   - Choose study hours per week
   - Select current skills and their levels (Beginner/Intermediate/Advanced)

3. **Skill Gap Analysis**
   - AI analyzes gaps between current and required skills
   - Shows missing skills with priority scores
   - Displays skills that need improvement
   - Calculates overall match percentage

4. **Learning Roadmap**
   - Generates personalized learning path
   - Organizes skills into prerequisite-aware phases
   - Provides learning resources (courses, YouTube, articles)
   - Estimates time to complete each phase

## 🧠 AI Algorithm

### Gap Score Formula
```
Gap Score = (Required Level - Current Level) × Importance Weight

Importance Weights:
- CRITICAL: 4
- HIGH: 3
- MEDIUM: 2
- LOW: 1

Skill Levels:
- BEGINNER: 1
- INTERMEDIATE: 2
- ADVANCED: 3
```

### Roadmap Generation
1. **Phase 1**: Foundation - Critical skills with no prerequisites
2. **Phase 2**: Core Skills - High importance skills
3. **Phase 3**: Advanced - Enhancement and optional skills

## 📊 Database Schema

### Key Tables
- `users` - Authentication data
- `user_profiles` - Career goals and current skills
- `skills` - Master skill database
- `career_roles` - Job positions
- `role_skill_mappings` - Required skills for each role
- `learning_resources` - Courses and tutorials
- `skill_gap_analyses` - Analysis results

## 🔑 API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login

### Profile
- `GET /api/profile` - Get user profile
- `POST /api/profile` - Create/update profile

### Analysis
- `GET /api/analysis/skill-gap` - Get skill gap analysis
- `GET /api/analysis/roadmap` - Get learning roadmap

### Public
- `GET /api/public/skills` - List all skills
- `GET /api/public/roles` - List all career roles

### Admin (Requires ROLE_ADMIN)
- `GET/POST/PUT/DELETE /api/admin/skills` - Manage skills
- `GET/POST/PUT/DELETE /api/admin/roles` - Manage career roles

## 🎨 Features

### User Features
✅ JWT-based authentication  
✅ Profile management with skill tracking  
✅ AI-powered skill gap analysis  
✅ Personalized learning roadmaps  
✅ Resource recommendations (courses, YouTube, etc.)  
✅ Progress tracking  
✅ Responsive modern UI  

### Admin Features
✅ Skill database management  
✅ Career role management  
✅ Role-skill mapping configuration  
✅ Learning resource curation  

### AI Features
✅ Gap score calculation with weighted importance  
✅ Prerequisite-aware learning order  
✅ Study time-based timeline estimation  
✅ Multi-phase roadmap generation  

## 🎯 Seed Data

The application includes  seed data:
- Admin user (admin@skillgap.com)
- 15+ skills (Java, Python, React, Docker, AWS, etc.)
- 3 career roles (Backend Dev, Frontend Dev, Full Stack Dev)
- Skill mappings with prerequisites
- Learning resources from Udemy, YouTube, freeCodeCamp

## 🔒 Security

- Passwords encrypted with BCrypt
- JWT token authentication
- Role-based access control (USER/ADMIN)
- Protected API endpoints
- CORS configuration for frontend

## 🚧 Troubleshooting

### Backend Issues
- **Port 8080 already in use**: Change port in application.properties  
  ```properties
  server.port=8081
  ```

- **MySQL connection failed**: Verify MySQL is running and credentials are correct

- **Tables not created**: Check `spring.jpa.hibernate.ddl-auto=update` in application.properties

### Frontend Issues
- **API connection failed**: Ensure backend is running on port 8080

- **CORS errors**: Verify CORS configuration in CorsConfig.java

- **Module not found**: Run `npm install` again

## 🌟 Future Enhancements

- Machine Learning integration with scikit-learn
- Progress tracking and skill completion
- Certificate integration
- Social features (skill endorsements)
- Mobile app (React Native)
- Analytics dashboard for admin
- Email notifications
- Calendar integration for study planning

## 📝 License

This project is for educational purposes.

## 👥 Contributors

Built as a comprehensive full-stack demonstration project.

---

**Happy Learning! 🚀**
