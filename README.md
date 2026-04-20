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

On first launch, the app creates an SQLite database file otterware_grade_tracker.db locally, in C:\Users\<your_username>. 
The database can be browsed directly with https://sqlitebrowser.org/

The app seeds roles database table with user roles:
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

Log out and log back in as a teacher to see the classes assigned to them, create assignments, and (partially) enter grades <-- IN PROGRESS.

Log in as a student to see their classes, assignments, and any grades that have been entered. <-- IN PROGRESS.

## Still in Progress

- The teacher grading flow isn't fully connected yet. Teachers can create assignments, but opening one up to actually enter scores still needs to be wired through. Everything else is mostly functional for Part 3.

## ERD

<img width="1068" height="745" alt="ERD" src="https://github.com/user-attachments/assets/c124e65e-c437-4429-832e-004068b461dd" />

## Mockups

New mock-ups coming soon.












