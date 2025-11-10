# How to Run MarketFlow JAR

## Quick Start

1. **Make sure you have Java 25 installed**
   - Download from: https://adoptium.net/ or https://www.oracle.com/java/technologies/downloads/
   - Verify installation: Open terminal/command prompt and run `java -version`
   - You should see "java version 25" or similar

2. **Run the application**
   - Double-click the `marketflow-1.0.0-SNAPSHOT.jar` file, OR
   - Open terminal/command prompt in the folder containing the JAR and run:
     ```bash
     java -jar marketflow-1.0.0-SNAPSHOT.jar
     ```

## Supported Platforms

This JAR works on:
- ✅ Windows (7, 8, 10, 11)
- ✅ macOS (10.15+)
- ✅ Linux (Ubuntu, Fedora, etc.)

## Troubleshooting

### "Java not found" or "java command not found"
- Java is not installed or not in your PATH
- Install Java 25 from the links above

### "Unsupported class file major version"
- You have an older version of Java installed
- Update to Java 25

### Application won't start
- Make sure you have a graphical desktop environment
- On Linux, make sure you have X11 or Wayland

### Other issues
- Check that the JAR file was downloaded completely (~92MB)
- Try running from command line to see error messages

## What's Included

The JAR file contains:
- All application code
- All JavaFX libraries
- All required dependencies
- Native libraries for Windows, macOS, and Linux

This is a **single-file application** - you don't need to install anything except Java 25!
