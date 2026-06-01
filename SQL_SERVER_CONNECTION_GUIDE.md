# Step-by-Step Guide: Connect Online Exam System to SQL Server

This guide shows **exactly** how to connect this Java project to **Microsoft SQL Server** (Express edition `SQLEXPRESS`), the same way we fixed it on your PC.

**Project folder example:**

```text
c:\Users\HP\OneDrive\Desktop\oop
```

**Time needed:** about 30–45 minutes (first time)

---

## What you are connecting

```text
Java App (Swing)
    ↓  JDBC driver (mssql-jdbc JAR in lib/)
    ↓  TCP port 1433
SQL Server Express (SQLEXPRESS)
    ↓
Database: OnlineExamSystem
    Tables: users, questions, results, ...
```

**Important:** The Java app does **not** use the same connection as SSMS “Windows Authentication” on `localhost\SQLEXPRESS` unless you also enable **TCP** and **SQL login (`sa`)** (or change the code to Windows auth). That is why we run the setup script below.

---

## Before you start (requirements)

| Requirement | Check |
|-------------|--------|
| Windows 10/11 | ✓ |
| Java JDK 8+ installed | Run `java -version` in CMD |
| SQL Server Express installed | Service: **SQL Server (SQLEXPRESS)** |
| SQL Server Management Studio (SSMS) | Optional but recommended |
| Project folder `oop` with `src`, `lib`, `database.sql` | ✓ |

---

## Step 1 — Install SQL Server Express (if not installed)

1. Download **SQL Server Express** from Microsoft.
2. During setup, choose instance name: **`SQLEXPRESS`** (default).
3. Install **SQL Server Management Studio (SSMS)** as well.

**Verify SQL Server is running:**

1. Press `Win + R`, type `services.msc`, Enter.
2. Find **SQL Server (SQLEXPRESS)**.
3. Status should be **Running**. If not, right-click → **Start**.

---

## Step 2 — Create the database and tables

### 2.1 Open SSMS

1. Open **SQL Server Management Studio**.
2. **Connect to Server:**
   - Server name: `localhost\SQLEXPRESS`
   - Authentication: **Windows Authentication**
3. Click **Connect**.

### 2.2 Run the database script

1. In SSMS: **File → Open → File**.
2. Open:

   ```text
   c:\Users\HP\OneDrive\Desktop\oop\database.sql
   ```

3. Press **F5** (Execute).

**What this script does:**

- Creates database **`OnlineExamSystem`**
- Creates tables: `users`, `questions`, `results`, etc.
- Inserts **10 sample questions** (at the end of the file)

### 2.3 Confirm database exists

1. In **Object Explorer**, expand **Databases**.
2. You should see **`OnlineExamSystem`**.

### 2.4 If database already existed but has no questions

In SSMS, open `database.sql`, select only the **`INSERT INTO questions`** lines at the bottom of the file, and run them (**F5**).

Or run in a new query:

```sql
USE OnlineExamSystem;
SELECT COUNT(*) FROM questions;
```

If count is **0**, copy the `INSERT INTO questions ...` block from `database.sql` and execute it.

**Verify in SSMS (New Query):**

```sql
USE OnlineExamSystem;
SELECT COUNT(*) AS question_count FROM questions;
```

Expected: **10** or more (not **0**).

---

## Step 3 — Enable TCP and SQL login for Java (required)

Java JDBC connects over **TCP port 1433**. On many PCs, SQLEXPRESS has TCP **disabled** by default. The app will **timeout** until you fix this.

### 3.1 Open PowerShell as Administrator

1. Press **Windows key**.
2. Type **PowerShell**.
3. Right-click **Windows PowerShell** → **Run as administrator**.

### 3.2 Go to project folder

```powershell
cd c:\Users\HP\OneDrive\Desktop\oop
```

*(Change path if your project is elsewhere.)*

### 3.3 Run the setup script (no “Y” prompt)

```powershell
powershell -ExecutionPolicy Bypass -File .\scripts\enable-sql-server.ps1
```

**What this script does (automatically):**

| Action | Why |
|--------|-----|
| Enables **TCP/IP** on port **1433** | Java needs TCP |
| Enables **mixed mode** authentication | Allows `sa` SQL login |
| Starts **SQL Server Browser** | Helps some tools find instance |
| Restarts **SQLEXPRESS** | Applies settings |
| Sets `sa` password to **`Admin123`** | Matches `DBConnection.java` |

### 3.4 Success message

You should see:

```text
SUCCESS: SQL Server is ready for the Java app.
Done. Run the app with: .\run.bat
```

If you see errors, see **Troubleshooting** at the end of this file.

### 3.5 Test connection from command line

Still in PowerShell:

```powershell
sqlcmd -S "localhost,1433" -U sa -P Admin123 -d OnlineExamSystem -Q "SELECT COUNT(*) FROM questions"
```

- If you get a number (e.g. `10`) → database + login + TCP are OK.
- If **timeout** → run Step 3 again as Administrator.
- If **login failed** → run Step 3 again or check password in Step 4.

---

## Step 4 — Configure connection settings in Java (optional)

Connection settings are in:

```text
src\database\DBConnection.java
```

Default values (used on your machine):

| Setting | Value |
|---------|--------|
| Server | `localhost` |
| Port | `1433` |
| Database | `OnlineExamSystem` |
| Username | `sa` |
| Password | `Admin123` |

**JDBC URL built by the app:**

```text
jdbc:sqlserver://localhost:1433;databaseName=OnlineExamSystem;user=sa;password=Admin123;encrypt=false;trustServerCertificate=true;loginTimeout=15
```

**If you change the `sa` password in SQL Server**, update `PASSWORD` in `DBConnection.java` to match, then recompile (Step 6).

---

## Step 5 — Add the JDBC driver (JAR file)

The Java app needs Microsoft’s JDBC driver to talk to SQL Server.

### 5.1 Check if JAR exists

Folder:

```text
c:\Users\HP\OneDrive\Desktop\oop\lib\
```

File required:

```text
mssql-jdbc-12.8.1.jre11.jar
```

### 5.2 Download if missing

1. Open:  
   https://repo1.maven.org/maven2/com/microsoft/sqlserver/mssql-jdbc/12.8.1.jre11/mssql-jdbc-12.8.1.jre11.jar  
2. Save the file into the **`lib`** folder.

See also: `lib\README.txt`

**Why this matters:** Without this JAR you get:

```text
ClassNotFoundException: com.microsoft.sqlserver.jdbc.SQLServerDriver
```

---

## Step 6 — Compile and run the application

### Option A — Using `run.bat` (recommended)

1. Open **Command Prompt** or **PowerShell** (normal user is fine).
2. Run:

```powershell
cd c:\Users\HP\OneDrive\Desktop\oop
.\run.bat
```

`run.bat` will:

1. Check for JDBC JAR in `lib\`
2. Compile all Java files with classpath `lib\*;src`
3. Run: `java -cp "bin;lib\*" main.Main`

### Option B — Manual commands

```cmd
cd c:\Users\HP\OneDrive\Desktop\oop
mkdir bin
javac -encoding UTF-8 -cp "lib\*;src" -d bin src\main\Main.java src\database\*.java src\gui\*.java src\model\*.java src\service\*.java src\utils\*.java
java -cp "bin;lib\*" main.Main
```

### Do NOT open `run.bat` inside Cursor as a file

If Cursor shows **“Unable to open run.bat”**, run it from **terminal** or **double-click in File Explorer** — that is not a database error.

---

## Step 7 — Confirm the app is connected

### 7.1 On startup

- If connection **works**: login window opens, **no** red database error dialog.
- If connection **fails**: dialog **“Database se connect nahi ho saka”** → repeat Step 3 and Step 5.

### 7.2 Register and login

1. Click **Register** → create a user (saved in `users` table).
2. Login with same username/password.

### 7.3 Test exam (questions from database)

1. **Take Examination**
2. You should see **question text** and options **A, B, C, D**.

If the screen is blank (only empty radio buttons), the `questions` table is empty → run **Step 2.4** (INSERT from `database.sql`).

---

## Step 8 — How the code connects (for viva / report)

| File | Role |
|------|------|
| `DBConnection.java` | Opens JDBC connection using URL, `sa`, password |
| `QueryManager.java` | Runs SQL: `SELECT`, `INSERT` on `users`, `questions`, `results` |
| `Main.java` | Calls `DBConnection.testConnection()` when app starts |
| `ExamService.java` | Loads questions via `QueryManager.getAllQuestions()` |

**Example flow — login:**

```text
LoginFrame → LoginService → QueryManager.validateUser()
    → DBConnection.getConnection()
    → SELECT * FROM users WHERE username = ? AND password = ?
```

---

## Quick checklist (print this)

- [ ] SQL Server (SQLEXPRESS) service **Running**
- [ ] `database.sql` executed in SSMS
- [ ] `SELECT COUNT(*) FROM questions` ≥ 1
- [ ] `enable-sql-server.ps1` run **as Administrator** → **SUCCESS**
- [ ] `sqlcmd -S localhost,1433 -U sa -P Admin123` works
- [ ] `lib\mssql-jdbc-12.8.1.jre11.jar` exists
- [ ] App started with `.\run.bat`
- [ ] Login works, exam shows questions

---

## Troubleshooting

### Error: `Receive timed out` / port 1434

**Cause:** TCP not enabled or wrong port.  
**Fix:** Run Step 3 as **Administrator** again.

### Error: `Login failed for user 'sa'`

**Cause:** Mixed mode off or wrong password.  
**Fix:** Run `enable-sql-server.ps1` again, or set password in SSMS and update `DBConnection.java`.

### Error: `Cannot open database "OnlineExamSystem"`

**Cause:** Database not created.  
**Fix:** Run `database.sql` in SSMS (Step 2).

### Error: `Driver not found`

**Cause:** JDBC JAR missing or wrong classpath.  
**Fix:** Step 5 + use `run.bat` (Step 6).

### Exam blank — no question text

**Cause:** `questions` table empty.  
**Fix:** Run the questions `INSERT` from `database.sql` (Step 2.4).

### SSMS connects but Java does not

**Cause:** SSMS can use **named pipes** locally; Java needs **TCP 1433** + **sa** (or code change for Windows auth).  
**Fix:** Step 3 is mandatory for this project.

---

## Related files in this project

| File | Purpose |
|------|---------|
| `SQL_SERVER_CONNECTION_GUIDE.md` | This guide (database setup) |
| `scripts\enable-sql-server.ps1` | TCP + mixed mode setup |
| `database.sql` | Schema + sample questions |
| `src\database\DBConnection.java` | Connection URL and credentials |
| `run.bat` | Compile and run with JDBC |
| `lib\README.txt` | JDBC driver download link |

---

## Summary (3 main steps)

1. **SSMS** → Run `database.sql` → database + tables + questions  
2. **Admin PowerShell** → `enable-sql-server.ps1` → TCP + `sa` for Java  
3. **Terminal** → `.\run.bat` → app uses JDBC to `localhost:1433`

After that, register a user, login, and take the exam — all data is stored in **SQL Server**.
