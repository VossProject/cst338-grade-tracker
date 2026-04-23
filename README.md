# Grade Tracker

A desktop grade tracking app where students can view their grades, teachers can manage assignments and enter scores, and admins can manage users and courses. Built with JavaFX, SQLite, and AtlantaFX for UI.

## Team

- Olga Bradford - teal-lune
- Harvey Duran - harv-d
- Robert Mozzetti - rmozzetti
- Mikey Voss - VossProject

## Tech

- Java 25 / JavaFX 24
- AtlantaFX (Dracula theme)
- Gradle 9.4.1
- SQLite (JDBC)
- JUnit 5 (unit tests)
- Checkstyle (Google style, 4-space indent)
- Zen Quotes API (Quote of the Day widget on the dashboard, called with Java's built-in `HttpClient`)

### CI

GitHub Actions runs Checkstyle and the test suite on every pull request to `main`. Config: `.github/workflows/ci.yml`.

## Build and Run

Requires Java 25. The Gradle wrapper handles everything else.

```bash
./gradlew run             # launch the app
./gradlew build           # compile, test, and run Checkstyle
./gradlew test            # unit tests only
./gradlew checkstyleMain  # style check on main sources
```

### Workflow

On first launch, the app creates an SQLite database file (`otterware_grade_tracker.db`) in your home folder: `~` on Mac and Linux, or `C:\Users\<your_username>` on Windows. You can browse the database directly with [DB Browser for SQLite](https://sqlitebrowser.org/).

The app seeds the `roles` table with:
- roleId: 1, roleName: "Admin"
- roleId: 2, roleName: "Teacher"
- roleId: 3, roleName: "Student"

Next, it creates a default admin user:

- Username: `Admin`
- Password: `Admin123`

Log in as the admin to set up the rest of the data.

1. Register users. Create other admins, teachers, and students from the admin dashboard.
2. Create classes. Each class takes a name, description, and an assigned teacher. There can be only 1 teacher per class, so make sure there is an available teacher, otherwise the dropdown will not populate.
3. Enroll students into their classes.

Log out and log back in as a teacher to see the classes assigned to them, create assignments, and enter grades.

Log in as a student to see their classes, assignments, and any grades that have been entered.

## ERD

<img width="1110" height="673" alt="new-erd" src="https://github.com/user-attachments/assets/723b81b6-9e00-4cdc-97c3-78bdf08e77e7" />

## Scenes
### Login
<img width="1616" height="858" alt="login" src="https://github.com/user-attachments/assets/f2756905-dbf8-4efd-83e7-27037c30407f" />

### Dashboard
<img width="1616" height="858" alt="dashboard" src="https://github.com/user-attachments/assets/175c7a4e-c778-42e4-9701-cf0f64644316" />

### Users (Available to Admins only)
<img width="1616" height="858" alt="users" src="https://github.com/user-attachments/assets/3e5a7101-ed04-4c8a-a9e7-393143c35bea" />

### Create User (Available to Admins only)
<img width="577" height="487" alt="create_user" src="https://github.com/user-attachments/assets/60dac0f0-7623-470d-806c-1db1a1b3e5ae" />

### All Classes (Available to Admins only)
<img width="1616" height="858" alt="admin-classes" src="https://github.com/user-attachments/assets/748934fb-df4c-4d78-aef3-9b32e851e428" />

### Create Class (Available to Admins only)
<img width="577" height="810" alt="create-class" src="https://github.com/user-attachments/assets/7af02188-af14-4758-a58a-1157ce3c36cc" />

### Classes (Teachers)
<img width="1616" height="858" alt="teacher-classes" src="https://github.com/user-attachments/assets/eaa09cf3-d5a4-4a19-8e36-33cc9f8f876e" />

### Create Assignment (Teachers)
<img width="577" height="542" alt="create-assignment" src="https://github.com/user-attachments/assets/ff2de73d-a838-41e3-ac98-61fef310ae25" />

### View Assignments (Teachers)
<img width="1616" height="858" alt="teacher-assignments" src="https://github.com/user-attachments/assets/ec1dd099-900c-4fa4-81e8-ea9db8c6e780" />

### Grade Assignments (Teachers)
<img width="577" height="800" alt="grade-assignment" src="https://github.com/user-attachments/assets/df4cfa7e-4ca5-48c0-89a0-d3bb6bfbfd23" />

### Classes (Students)
<img width="1616" height="858" alt="student-classes" src="https://github.com/user-attachments/assets/105e75be-dff6-4791-9286-9d8455ce2e63" />

### View Assignments (Students)
<img width="1616" height="858" alt="student-assignments" src="https://github.com/user-attachments/assets/58d00af2-ca34-4708-b61a-607d42669adf" />













