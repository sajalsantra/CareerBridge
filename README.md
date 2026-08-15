# CareerBridge

### Connecting the right people with the right opportunities.

CareerBridge is a modern job-search platform designed to make the hiring process simpler, smarter, and more meaningful for both **Job Seekers** and **Recruiters**.

Instead of treating a resume as just a document, CareerBridge aims to build a complete professional profile that represents a person's **skills, education, experience, preferences, and career goals**.
 
---

## 🌟 Our Vision

Finding a job should not be just about searching through thousands of job posts.

It should be about finding the **right opportunity for the right person**.

Our vision with CareerBridge is to create a platform where:

- Job Seekers can build a strong professional identity.
- Recruiters can discover relevant candidates more efficiently.
- Skills and experience matter more than just keywords.
- Job discovery becomes personalized and meaningful.
- The complete hiring journey can happen in one place.
### Our long-term vision

> **Build a career platform that connects people and opportunities based on who they are, what they can do, and where they want to go.**
 
---

## 💡 What is CareerBridge?

CareerBridge is a full-stack Job Search Portal currently being developed with a focus on building a secure and scalable backend first.

The platform brings together two major sides of the hiring ecosystem:

```
                 CareerBridge
                      │
          ┌───────────┴───────────┐
          │                       │
      Job Seekers             Recruiters
          │                       │
          ▼                       ▼
   Build Profile              Find Talent
   Add Skills                 Post Jobs
   Add Education              Manage Jobs
   Add Experience             Review Applicants
   Find Jobs                  Hire Candidates
   Apply for Jobs
```
 
---

## 👨‍💻 What We Do

CareerBridge helps **Job Seekers** create a complete professional profile rather than relying only on a traditional resume.

A Job Seeker can build their profile with:

**👤 Professional Profile**

- Name
- Contact information
- Professional headline
- Professional summary
- Location
- Current job
- Current company
- Experience
- Expected salary
- Notice period
- Preferred location
- Job type preference
- Work mode preference
  **🛠️ Skills**

Job Seekers can add their technical and professional skills along with:

- Skill name
- Proficiency level
- Years of experience
  
**🎓 Education**

Job Seekers can maintain their educational background including:

- Degree
- Field of study
- Institution
- Location
- Start date
- End date
- Grade
- Description
  
**💼 Future Career Information**

The platform will also support:

- Work experience
- Resume management
- Job applications
- Saved jobs
- Application tracking
- Personalized job discovery
---

## 🏢 For Recruiters

CareerBridge is also being designed to make candidate discovery easier for Recruiters.

Recruiters will be able to:

- Create a recruiter profile
- Post job opportunities
- Manage job postings
- Search for candidates
- Discover candidates based on skills
- Review applications
- Manage applicants
- Move candidates through the hiring process
  The goal is to help Recruiters spend less time searching and more time finding **relevant candidates**.

---

## 🔐 Security First

CareerBridge is being built with security as a core part of the architecture.

The backend uses:

- Spring Security
- JWT Authentication
- Password Encryption
- Role-Based Authorization
- Protected REST APIs
- User-specific resource access
- Centralized exception handling
### User Roles

```
JOB_SEEKER
    │
    ├── Manage Profile
    ├── Manage Skills
    ├── Manage Education
    ├── Search Jobs
    └── Apply for Jobs
 
 
RECRUITER
    │
    ├── Manage Profile
    ├── Create Jobs
    ├── Manage Jobs
    └── Manage Applications
 
 
ADMIN
    │
    ├── Manage Users
    ├── Manage Roles
    ├── Manage Jobs
    └── Manage Platform
```
 
---

## 🏗️ How CareerBridge Works

CareerBridge follows a layered architecture to keep the system maintainable and scalable.

```
                    Frontend
                      │
                      ▼
                REST API Layer
                      │
                      ▼
                 Controller
                      │
                      ▼
                   Service
                      │
                      ▼
                 Repository
                      │
                      ▼
                JPA / Hibernate
                      │
                      ▼
                  Database
```

The backend is being developed using a clean separation of responsibilities so that new features can be added without affecting existing functionality.
 
---

## 🚧 What We Are Building Now

CareerBridge is an **actively developing project**.

Our development approach is incremental — building the foundation first and then adding features one by one.

**✅ Currently Implemented**

```
Authentication
    ├── Registration
    ├── Login
    ├── Username / Email Authentication
    ├── Password Encryption
    └── JWT Authentication
 
Job Seeker
    ├── Profile
    ├── Skills
    └── Education
```

**🔨 Currently Developing**

```
Job Seeker
    ├── Work Experience
    ├── Resume Management
    ├── Job Search
    ├── Job Filtering
    ├── Job Applications
    └── Application Tracking
```

**🔮 Coming Later**

```
Recruiter
    ├── Recruiter Profile
    ├── Job Posting
    ├── Candidate Search
    └── Application Management
 
Admin
    ├── User Management
    ├── Job Management
    └── Platform Management
 
Smart Features
    ├── Skill Matching
    ├── Personalized Job Recommendations
    └── Candidate-Job Matching
```
 
---

## 🧰 Technology

CareerBridge is being built with modern web technologies.

**Backend**
- Java
- Spring Boot
- Spring Security
- JWT
- Spring Data JPA
- Hibernate
- REST API
  
**Frontend**
- Angular
- TypeScript
- HTML5
- CSS3

**Database**
- MySQL
  
**Tools**
- Git
- GitHub
- IntelliJ IDEA
- MySQL Workbench
- Postman
- Maven
---

## 🚀 How to Use CareerBridge

CareerBridge will have two primary types of users.

**For Job Seekers**

A typical journey will look like:

```
Register
   ↓
Login
   ↓
Create Profile
   ↓
Add Skills
   ↓
Add Education
   ↓
Add Experience
   ↓
Search Jobs
   ↓
Find Suitable Opportunity
   ↓
Apply
   ↓
Track Application
```

**For Recruiters**

```
Register
   ↓
Create Recruiter Profile
   ↓
Post Job
   ↓
Receive Applications
   ↓
Discover Candidates
   ↓
Review Applications
   ↓
Select Candidate
```
 
---

## 🧑‍💻 How Developers Can Use This Project

CareerBridge is also being developed as a practical full-stack project for learning and experimentation.

You can use it to understand:

- Spring Boot REST API development
- Spring Security
- JWT authentication
- Role-based authorization
- JPA entity relationships
- Hibernate
- MySQL database design
- Layered architecture
- Exception handling
- API development and testing
- Angular integration
- Full-stack application development
  You can clone the project, explore the architecture, run the backend locally, test APIs through Postman, and contribute new features.

---

## 📥 Getting Started

Clone the repository:

```bash
git clone https://github.com/<your-username>/CareerBridge.git
```

Move into the project:

```bash
cd CareerBridge
```

Configure your database in:

```
src/main/resources/application.properties
```

Then start the Spring Boot application.

Once the backend is running, APIs can be tested using Postman.
 
---

## 🤝 Contributing

CareerBridge is being built step by step, and contributions are welcome.

If you want to contribute:

1. Fork the repository.
2. Create a feature branch.
```bash
   git checkout -b feature/your-feature
```

3. Implement your changes.
4. Test your changes.
5. Commit your changes.
```bash
   git commit -m "Add your feature"
```

6. Push your branch.
```bash
   git push origin feature/your-feature
```

7. Open a Pull Request.
---

## 📌 Project Status

**CareerBridge is currently under active development.**

The backend foundation, authentication, Job Seeker Profile, Skills, and Education modules are being built first.

More features will be introduced progressively as the project evolves.
 
---

## ❤️ Why CareerBridge?

Because getting a job should not feel like searching for a needle in a haystack.

CareerBridge aims to create a bridge between:

```
              Skills
                +
             Education
                +
            Experience
                +
          Career Goals
                │
                ▼
          ┌─────────────┐
          │ CareerBridge│
          └─────────────┘
                │
                ▼
        The Right Opportunity
```

### CareerBridge — Build your profile. Discover opportunities. Build your career.
 
---

## 👨‍💻 Author

**Sajal Santra**

Built with Java, Spring Boot, Angular, and a passion for building practical software solutions.