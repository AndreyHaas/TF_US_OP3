# SQL Injection – Erklärung mit Statement & PreparedStatement

## 1. Was ist SQL Injection?

**SQL Injection** ist eine der häufigsten und gefährlichsten Sicherheitslücken in Webanwendungen. Dabei wird ein bösartiger SQL-Code in eine Eingabemaske (z. B. Login-Formular, Suchfeld) eingeschleust, um die Datenbank zu manipulieren oder vertrauliche Daten auszulesen.

**Ziel des Angreifers:**
- Umgehung der Authentifizierung (z. B. ohne Passwort einloggen)
- Auslesen von Benutzerdaten
- Löschen oder Ändern von Datenbankinhalten
- Ausführung von Systembefehlen

---

## 2. Beispiel für eine verwundbare Abfrage (Statement)

Angenommen, wir haben folgende Methode, die Benutzerdaten anhand von `username` und `password` abfragt:

```java
String username = request.getParameter("username");
String password = request.getParameter("password");

String sql = "SELECT * FROM users WHERE username = '" + username + "' AND password = '" + password + "'";

Statement stmt = connection.createStatement();
ResultSet rs = stmt.executeQuery(sql);
```

### Angriffsszenario

Ein Angreifer gibt im Feld `username` folgenden Wert ein:

```sql
' OR '1'='1
```

Das Passwort lässt er leer oder gibt ebenfalls `' OR '1'='1` ein.

Die resultierende SQL-Abfrage lautet:

```sql
SELECT * FROM users WHERE username = '' OR '1'='1' AND password = '' OR '1'='1'
```

Da `'1'='1'` immer **wahr** ist, werden alle Benutzer zurückgegeben. Der Angreifer kann sich so ohne gültige Anmeldedaten einloggen – möglicherweise als erster Benutzer (z. B. Admin).

### Weitere gefährliche Eingaben

```sql
'; DROP TABLE users; --
```

Diese Eingabe würde die gesamte Tabelle `users` löschen.

---

## 3. Lösung: PreparedStatement

**PreparedStatement** ist ein sicherer Mechanismus in Java, um SQL-Abfragen mit Parametern zu erstellen. Die Parameter werden **getrennt vom SQL-Befehl** an die Datenbank gesendet und automatisch maskiert (escapen). Dadurch wird verhindert, dass Benutzereingaben als SQL-Code interpretiert werden.

### Sicheres Beispiel mit PreparedStatement

```java
String username = request.getParameter("username");
String password = request.getParameter("password");

String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

PreparedStatement pstmt = connection.prepareStatement(sql);
pstmt.setString(1, username);
pstmt.setString(2, password);

ResultSet rs = pstmt.executeQuery();
```

Die Platzhalter `?` werden durch die Werte aus `username` und `password` ersetzt – aber **sicher**, ohne dass die Eingabe den SQL-Befehl verändern kann.

---

## 4. Zusammenfassung der Unterschiede

| Merkmal | Statement | PreparedStatement |
|---------|-----------|---------------------|
| **Sicherheit** | Anfällig für SQL Injection | Sicher gegen SQL Injection |
| **Parameter** | Werte werden direkt in SQL eingefügt | Werte werden durch Platzhalter (`?`) übergeben |
| **Lesbarkeit** | Unübersichtlich bei vielen Parametern | Übersichtlich und strukturiert |
| **Performance** | Wird bei jeder Ausführung neu kompiliert | Kann mehrfach verwendet werden (Precompiling) |
| **Empfehlung** | Nicht für benutzergesteuerte Abfragen verwenden | Immer bevorzugen, besonders bei Benutzereingaben |

---

## 5. Fazit

> **Verwenden Sie grundsätzlich PreparedStatement, wenn Sie Benutzereingaben in SQL-Abfragen einbauen.**

- **PreparedStatement** schützt vor SQL Injection.
- Es sorgt für sauberen und wartbaren Code.
- Es kann die Performance verbessern, besonders bei wiederholten Abfragen.

**Statement** sollte nur dann verwendet werden, wenn gar keine Benutzereingaben verarbeitet werden (z. B. feste SQL-Befehle wie `SHOW TABLES`).

---

## 6. Zusätzliche Maßnahmen

Zusätzlich zu `PreparedStatement` sollten Sie folgende Prinzipien beachten:

- **Eingabevalidierung** (z. B. Whitelist für erlaubte Zeichen)
- **Prinzip der geringsten Privilegien** (Datenbankbenutzer mit minimalen Rechten)
- **Verschlüsselung sensibler Daten**
- **Regelmäßige Sicherheitsüberprüfungen (Penetrationstests)**