# Instructions for coding agents

Read this file before changing the project. Review the relevant sections of [README.md](README.md) and check the working tree before editing. Preserve existing user changes and keep your edits scoped to the request.

## Project layout

- This is a Java 21 Maven project using Spring Boot, Spring Security, and Oracle.
- Application code is under `src/main/java/io/github/saeidkeneshli/server`. Runtime defaults are in `src/main/resources/application.yml`.
- When changing authentication, review the relevant controller, service, and security filter together. Login accepts HTTP Basic credentials and protected requests use JWT bearer tokens. Update the README when setup or API behavior changes.

## Local development and verification

- Use the README to configure a development Oracle schema and supply credentials and the JWT signing key through environment variables. Keep actual credentials, keys, and tokens out of committed files and command output.
- The `Users` entity expects an Oracle identity column. Check the entity mapping before changing schema SQL. The root `create.sql` uses a different schema, and `document/DB.sql` starts by dropping the users table. Use the README setup for a fresh development schema.
- Run `mvn clean verify` for code changes when Maven and JDK 21 are available. For changes that affect registration or login, verify those flows against a development Oracle schema when available. Report any checks you could not run.
