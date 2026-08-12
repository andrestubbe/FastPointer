# FastPointer Compilation Guide

## Requirements
- **JDK 17+** (with `--add-exports=java.base/jdk.internal.misc=ALL-UNNAMED`)
- **Apache Maven 3.8+**
- **MSVC / Visual Studio 2022** (Build Tools with C++17 support)

## Building
```cmd
mvn clean package -DskipTests
```

## Running Demo
```cmd
run-demo.bat
```
