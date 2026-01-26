# 🚀 Running Backend with Eclipse - Step by Step

## Perfect! Eclipse works great for Spring Boot! 

Follow these simple steps:

---

## Step 1: Import the Project

1. **Open Eclipse**

2. **Import the Maven project:**
   - Go to: `File` → `Import`
   - Expand `Maven` folder
   - Select `Existing Maven Projects`
   - Click `Next`

3. **Browse to your project:**
   - Click `Browse` button
   - Navigate to: `C:\Users\singh\OneDrive\Desktop\project\career-skill-gap-backend`
   - Click `Select Folder`

4. **Finish import:**
   - You should see `pom.xml` checked in the Projects list
   - Click `Finish`

5. **Wait for Maven dependencies to download:**
   - Bottom right corner will show progress
   - This might take 2-3 minutes (first time only)
   - Eclipse is downloading Spring Boot, MySQL driver, JWT libraries, etc.

---

## Step 2: Run the Application

1. **Find the main class:**
   - In Package Explorer (left panel), expand:
     ```
     career-skill-gap-backend
     └── src/main/java
         └── com.skillgap
             └── SkillGapApplication.java
     ```

2. **Run it:**
   - Right-click on `SkillGapApplication.java`
   - Select `Run As` → `Java Application`
   
   **OR** (Alternative):
   - Right-click on `SkillGapApplication.java`
   - Select `Run As` → `Spring Boot App` (if you have Spring Tools installed)

---

## Step 3: Verify It's Running

You'll see logs in the Console (bottom panel):

```
  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

2026-01-26 01:35:12.345  INFO --- [main] o.s.b.w.embedded.tomcat.Tomcat
Started: Tomcat started on port(s): 8080 (http)

2026-01-26 01:35:12.567  INFO --- [main] com.skillgap.SkillGapApplication
Started SkillGapApplication in 5.123 seconds (JVM running for 6.234)

Initializing database with seed data...
Database initialization completed!
```

**Look for:** `Tomcat started on port(s): 8080 (http)` ✅

---

## Step 4: Test the Backend

Open browser and go to: http://localhost:8080/api/public/skills

You should see JSON data with skills like:
```json
[
  {
    "id": 1,
    "name": "Java",
    "category": "Backend",
    "difficulty": "MEDIUM"
  },
  ...
]
```

If you see this, **backend is running perfectly!** ✅

---

## Step 5: Login to Your App

1. **Go back to:** http://localhost:3000
2. **Login with:**
   - Email: `admin@skillgap.com`
   - Password: `admin123`

3. **🎉 IT WORKS!** You'll be redirected to the dashboard!

---

## 🐛 Troubleshooting

### Problem: "Port 8080 already in use"
**Solution:**
- Stop any other application using port 8080
- OR change port in `application.properties`:
  ```properties
  server.port=8081
  ```
  Then update frontend `axiosConfig.js` to use port 8081

### Problem: "Cannot resolve dependencies"
**Solution:**
- Right-click project → `Maven` → `Update Project`
- Check "Force Update of Snapshots/Releases"
- Click OK

### Problem: "MySQL connection failed"
**Solution:**
- Make sure MySQL is running on port 3306
- Check username/password in `src/main/resources/application.properties`:
  ```properties
  spring.datasource.username=root
  spring.datasource.password=root
  ```
  Update if your MySQL has different credentials

### Problem: Eclipse doesn't have "Run As → Spring Boot App"
**Solution:**
- Use `Run As` → `Java Application` instead
- It works exactly the same way!

---

## 📁 Project Structure in Eclipse

After import, you'll see:
```
career-skill-gap-backend
├── src/main/java
│   └── com.skillgap
│       ├── config/           (Security, CORS configs)
│       ├── controller/       (REST endpoints)
│       ├── entity/           (Database models)
│       ├── repository/       (Database access)
│       ├── service/          (Business logic)
│       ├── security/         (JWT handling)
│       └── SkillGapApplication.java  ← RUN THIS!
├── src/main/resources
│   └── application.properties
└── pom.xml
```

---

## ✅ Quick Checklist

- [ ] Eclipse is open
- [ ] Import Maven project from `career-skill-gap-backend` folder
- [ ] Wait for dependencies to download (watch bottom right)
- [ ] Find `SkillGapApplication.java`
- [ ] Right-click → Run As → Java Application
- [ ] See "Tomcat started on port 8080" in console
- [ ] Test: http://localhost:8080/api/public/skills shows JSON
- [ ] Login at http://localhost:3000 works!

---

## 🎯 Expected Result

**Console Output:**
```
Started SkillGapApplication in 5.123 seconds
Tomcat started on port(s): 8080 (http)
Database initialization completed!
```

**Browser:**
- Frontend: http://localhost:3000 ✅
- Backend API: http://localhost:8080/api/public/skills ✅
- Login works! ✅
- Dashboard loads! ✅
- AI Analysis works! ✅

**Ready to use your AI-powered Career Skill Gap platform! 🚀**
