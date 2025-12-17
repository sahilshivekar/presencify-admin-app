Presencify

A secure, multi-modal attendance tracking application for educational institutions.

Presencify is designed to eliminate proxy attendance, device sharing, and location spoofing by ensuring the genuine physical presence of students. The project is being migrated from a native Android application to Kotlin Multiplatform (KMP) and Compose Multiplatform (CMP) to target Android, iOS, and Desktop from a single codebase.

🎯 The Problem

Traditional attendance systems are often unreliable, inefficient, and highly vulnerable to cheating. They lack the integration of robust security measures with a user-friendly experience, making it easy for students to mark attendance for their absent friends.

✨ The Solution

Presencify offers a robust and secure system with three distinct attendance-taking methods, providing faculty and administrators with reliable data while ensuring a seamless experience for students.

🚀 Core Features

🔒 Secure Attendance Methods

Dynamic QR + Liveness Detection (Primary)

Students scan a dynamic, time-sensitive QR code.

On-device, offline liveness detection with TensorFlow Lite verifies the student is a real person.

Face recognition is performed against a stored profile.

The user must complete three random challenges (e.g., look left, look up) to prevent spoofing with images or videos.

Teacher-Initiated Group Photos

The teacher captures a single group photo of the class.

An ML model detects and identifies all students present in the image.

Works offline and syncs when a connection is available.

Manual Attendance

A fallback method for teachers to manually mark attendance for each student.

Fully supported offline.

👤 User Roles

Role

Responsibilities

Admin

Manages the entire institutional structure, including semesters, courses, teachers, students, timetables, and divisions. Has full access to all attendance records.

Teacher

Manages personal profiles, class schedules (including cancellations and additions), and views student lists and attendance data.

Student

Marks attendance using one of the available methods and manages their own profile.

⚙️ Additional Features

Offline Support: Group photo and manual attendance are fully functional without an internet connection.

Real-time Notifications: Automatically notifies students about class changes via Firebase Cloud Messaging (FCM).

🛠️ Tech Stack & Architecture

This project is a modern, multiplatform application built with a focus on clean architecture, maintainability, and a shared business logic core.

📱 Client (Kotlin Multiplatform)

Component

Technology

Role

Framework

Kotlin Multiplatform (KMP) & Compose Multiplatform (CMP)

Single codebase targeting Android, iOS, and Desktop UI/Logic.

Architecture

Clean Architecture

Enforcing separation of Data, Domain, and Presentation layers.

DI

Koin

Dependency Injection.

Networking

Ktor

HTTP client with Bearer token auth and refresh logic.

Async

Kotlin Coroutines & Flow

Asynchronous programming for reactive data streams.

Local DB

Room for KMP (with SQLDelight planned)

Local data persistence.

Storage

Jetpack DataStore

Secure storage for auth tokens and user preferences.

💻 Backend

Component

Technology

Role

Runtime

Node.js with Express.js

Server runtime and web framework.

Database

PostgreSQL with Sequelize ORM

Relational database management.

Authentication

JWT (JSON Web Tokens)

Secure stateless user authentication.

Real-time

Socket.IO

Real-time communication for features like live attendance.

Notifications

Firebase Cloud Messaging (FCM)

Pushing class change notifications.

🏗️ Project Structure (KMP App)

The application follows a clean, feature-based, multi-layered architecture.

Core Modules (Located under core/)

These modules provide shared functionality across all features:

core/data: Contains shared networking utilities (HttpClientExt, HttpClientFactory), local storage (DataStoreFactory), and base data structures.

core/domain: Holds shared domain logic, including the Result and DataError wrappers, and validation patterns.

core/presentation: Includes UiText for abstracting UI strings and mappers to convert domain errors into user-facing messages.

core/di: Manages Koin dependency injection setup, with expect/actual modules for platform-specific bindings.

Feature Modules

Each feature (e.g., auth, attendance, profile) is organized into three distinct layers:

Data Layer (feature/data)

Repository Implementation: The single source of truth, coordinating between network and local data sources.

Network: Ktor data source implementations and DTOs (Data Transfer Objects) that map directly to API responses.

Mappers: Extension functions to convert DTOs and database Entities into clean domain models.

Database: Room DAOs, Entities, TypeConverters, and platform-specific database builders.

Domain Layer (feature/domain)

Repository Interface: The contract that the presentation layer depends on.

Models: Pure, immutable Kotlin data classes representing core business objects.

Use Cases: Encapsulate complex business logic that involves multiple repositories (used sparingly to avoid boilerplate).

Presentation Layer (feature/presentation)

ViewModel: Manages UI state, handles user actions, and communicates with the domain layer.

UI State: A data class representing everything needed to render the screen.

UI Action: A sealed interface defining all possible user interactions.

Composables:

ScreenRoot: Injects the ViewModel, collects state, and handles navigation.

Screen: A stateless Composable that renders the UI based on the state and delegates actions.

🤝 How to Contribute

We welcome contributions! Please adhere to the architectural patterns and coding standards established in the project.

Fork the repository.

Create a new branch for your feature or bug fix (git checkout -b feature/your-feature-name).

Ensure your code follows the established patterns:

Follow the Data-Domain-Presentation layer separation.

Use the Result wrapper for error handling.

Implement DI using Koin modules.

Write clean, testable, and maintainable code.

Submit a pull request with a clear description of your changes.
