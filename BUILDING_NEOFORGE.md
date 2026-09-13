# Terra for NeoForge 1.21.1

This branch turns Terra 6.5.0's unfinished NeoForge experiment into a native
NeoForge platform using Terra's shared dynamic-registry lifecycle.

## Build on Windows 11

1. Install a Java 21 JDK (Temurin 21 is suitable).
2. Open PowerShell in the repository directory.
3. Confirm `java -version` reports Java 21.
4. Run:

   ```powershell
   .\gradlew.bat :platforms:neoforge:build --stacktrace
   ```

The distributable file is written to `platforms\neoforge\build\libs` and is
named `Terra-neoforge-6.5.0-BETA+<commit>.jar`. Do not use the `sources`,
`javadoc`, `dev`, or `shadow` JAR variants.

## Build with GitHub Actions

Push the branch as `neoforge-1.21.1`, open the repository's **Actions** tab,
select **Build Terra NeoForge 1.21.1**, and choose **Run workflow**. The
finished JAR is attached to the workflow run as `terra-neoforge-1.21.1`.

## Initial test environment

- Minecraft 1.21.1
- Java 21
- NeoForge 21.1.56 or newer
- No Sinytra Connector, Fabric API, Forgified Fabric API, or Fabric Terra JAR
- A fresh test world before using the mod with an existing save

This remains an experimental port until both an integrated-server world and a
dedicated server/client connection have completed world creation and terrain
generation tests.
