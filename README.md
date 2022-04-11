# NEWBANK

### Dependencies

The following dependencies should be in your path:
- The following need to be added to your Library/Path:
- JUnit (for testing)
- Sqlite (database)
- https://mvnrepository.com/artifact/com.google.zxing/javase/3.3.0 (for password encryption)
- https://mvnrepository.com/artifact/com.google.zxing/core (for password encryption)
- https://mvnrepository.com/artifact/commons-codec/commons-codec/1.15 (for the authenticator)
- https://mvnrepository.com/artifact/de.taimos/totp

### Running the app:

To run the app, you need to run the server and the client entry files which are `newbank/server/NewBankServer` and `newbank/client/ExampleClient` respectively.

### Database

The app uses a SQLite database for data persistence. A db.sqlite file will be created if it doesn't exist already.

### Resetting the database

Run `newbank/server/database/Thanos.java` to reset your database
