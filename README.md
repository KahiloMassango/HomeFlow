# HomeFlow

HomeFlow is a collaborative household management app designed to solve a common problem: coordinating tasks, chores, and responsibilities among people who share a house, whether that's family members, roommates, or housemates.

Cross-platform application built using **Kotlin Multiplatform (KMP)** and **Compose Multiplatform**. It allows users to manage their home workflows efficiently, targeting **Android**, **iOS** platforms from a single shared codebase.

## 🎥 Video Demonstration

<!-- Place your video demonstration link below -->
[Watch the Demo Video Here](https://drive.google.com/file/d/1wMCc6W0F1CJspLtr_XYa8odZFkyhluqB/view?usp=sharing)

## 🛠 Technologies Used

This project leverages a modern technology stack to ensure performance, scalability, and maximum code sharing:

### Core
*   **[Kotlin](https://kotlinlang.org/)**: The primary programming language used for the entire project.
*   **[Kotlin Multiplatform](https://kotlinlang.org/docs/multiplatform.html)**: Enables sharing logic across Android, iOS, and Desktop.

### UI & UX
*   **[Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/)**: A declarative UI framework for sharing UIs across platforms.
*   **Material Design 3**: The design system used for the application's UI components.

### Architecture & State Management
*   **MVVM**: The application follows the Model-View-ViewModel architectural pattern.
*   **[Koin](https://insert-koin.io/)**: A pragmatic lightweight dependency injection framework for Kotlin.

### Backend & Data
*   **[Firebase Firestore](https://firebase.google.com/docs/firestore)**: Used as the cloud database (via `dev.gitlive:firebase-firestore`).
*   **[DataStore](https://developer.android.com/topic/libraries/architecture/datastore)**: Used for storing key-value pairs (Preferences) locally.

### Authentication
*   **Google Authentication**: Implemented using `kmauth-google` for cross-platform support.

### Libraries & Utilities
*   **[Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)**: For JSON serialization and deserialization.
*   **[Kotlinx Datetime](https://github.com/Kotlin/kotlinx-datetime)**: For Date and Time manipulation.
*   **Navigation Compose**: For handling navigation within the Compose app.

## 📂 Project Structure

*   `composeApp`: The main module containing the shared code.
    *   `commonMain`: Code shared across all platforms.
    *   `androidMain`, `iosMain`, `jvmMain`: Platform-specific code and configurations.
*   `iosApp`: The entry point for the iOS application (Xcode project).

## 🚀 Build & Run

### Android
To run the Android application:
```bash
./gradlew :composeApp:installDebug
```

### iOS
To run the iOS application, open `iosApp/iosApp.xcodeproj` in Xcode or run via Android Studio if configured.

### Desktop (JVM)
To run the desktop application (if configured):
```bash
./gradlew :composeApp:run
```
