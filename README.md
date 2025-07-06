# 🍽️ The Culinary Companion

[![Android](https://img.shields.iog. 3](https://img.shields.io/badge/Design-Material%203-purple.svg recipe browsing application built for WeightWatchers, showcasing the latest Android development practices and clean architecture principles.

## 📱 Screenshots

| Recipe List | Loading State | Error Handling |
|-------------|---------------|----------------|
|  |  |  |

## ✨ Features

### Core Functionality
- 📋 **Scrollable Recipe List** - Smooth infinite scrolling with optimized performance
- 🔄 **Pull-to-Refresh** - Intuitive refresh mechanism using Material 3 components
- 📄 **Pagination** - Efficient loading of recipes across multiple pages (1-5)
- 🔁 **Retry Mechanism** - Robust error handling with retry functionality
- 🎨 **Material Design 3** - Modern UI following latest design guidelines

### Technical Highlights
- 🏗️ **Clean Architecture** - Separation of concerns with clear layer boundaries
- 🔄 **MVI Pattern** - Unidirectional data flow for predictable state management
- 🚀 **Jetpack Compose** - Modern declarative UI toolkit
- 💉 **Dependency Injection** - Hilt for efficient dependency management
- 🌐 **Networking** - Retrofit with coroutines for asynchronous operations
- 🖼️ **Image Loading** - Coil with advanced caching strategies
- 🧪 **Testing Ready** - Architecture designed for comprehensive testing

## 🏛️ Architecture

This project follows **Clean Architecture** principles with **MVI (Model-View-Intent)** pattern:

```
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer                       │
│  ┌─────────────────┐  ┌─────────────────┐  ┌──────────────┐ │
│  │   Composables   │  │   ViewModels    │  │  UI States   │ │
│  └─────────────────┘  └─────────────────┘  └──────────────┘ │
└─────────────────────────────────────────────────────────────┘
                                │
┌─────────────────────────────────────────────────────────────┐
│                     Domain Layer                            │
│  ┌─────────────────┐  ┌─────────────────┐  ┌──────────────┐ │
│  │   Use Cases     │  │  Repositories   │  │   Entities   │ │
│  └─────────────────┘  └─────────────────┘  └──────────────┘ │
└─────────────────────────────────────────────────────────────┘
                                │
┌─────────────────────────────────────────────────────────────┐
│                      Data Layer                             │
│  ┌─────────────────┐  ┌─────────────────┐  ┌──────────────┐ │
│  │   API Service   │  │ Repository Impl │  │  Data Models │ │
│  └─────────────────┘  └─────────────────┘  └──────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

### Key Architectural Decisions

#### 🎯 **MVI Pattern**
- **Unidirectional Data Flow**: Actions → State → UI
- **Predictable State Management**: Single source of truth
- **Testable**: Easy to unit test state transformations

#### 🏗️ **Clean Architecture Layers**
- **Presentation**: UI components and ViewModels
- **Domain**: Business logic and use cases
- **Data**: API integration and data sources

#### 💉 **Dependency Injection**
- **Hilt**: Compile-time dependency injection
- **Modular**: Easy to swap implementations
- **Testable**: Mock dependencies for testing

## 🛠️ Tech Stack

### Core Technologies
- **Kotlin** - Modern, concise, and safe programming language
- **Jetpack Compose** - Declarative UI toolkit
- **Material 3** - Latest Material Design components
- **Coroutines & Flow** - Asynchronous programming and reactive streams

### Architecture & Patterns
- **Clean Architecture** - Separation of concerns
- **MVI Pattern** - Unidirectional data flow
- **Repository Pattern** - Data access abstraction
- **Dependency Injection** - Hilt for DI

### Networking & Data
- **Retrofit** - Type-safe HTTP client
- **OkHttp** - HTTP client with interceptors
- **Gson** - JSON serialization/deserialization
- **Coil** - Image loading with caching

### UI & UX
- **Compose Navigation** - Type-safe navigation
- **Material 3 Components** - Modern UI components
- **Custom Theming** - Consistent design system
- **Accessibility** - Screen reader support

## 📁 Project Structure

```
app/
├── src/main/java/com/example/culinarycompanion/
│   ├── data/
│   │   ├── api/                    # API service interfaces
│   │   ├── model/                  # Data models
│   │   └── repository/             # Repository implementations
│   ├── domain/
│   │   ├── repository/             # Repository interfaces
│   │   └── util/                   # Domain utilities
│   ├── presentation/
│   │   ├── components/             # Reusable UI components
│   │   ├── mvi/                    # MVI intents and states
│   │   ├── screen/                 # Screen composables
│   │   ├── theme/                  # UI theming
│   │   └── viewmodel/              # ViewModels
│   └── di/                         # Dependency injection modules
└── build.gradle.kts
```

## 🚀 Getting Started

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/mahmoudibrahimabdulfattah/CulinaryCompanion.git
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory

### Configuration

The app uses WeightWatchers API endpoints:
- Base URL: `https://api.qa.ww.com/`
- Endpoint: `/v1/guidance/interview/recipe/list`
- Pages: 1-5 (optional page parameter)

## 🔧 Key Implementation Details

### Performance Optimizations
- **LazyColumn**: Efficient list rendering
- **Image Caching**: Memory and disk caching with Coil
- **State Management**: Minimal recomposition
- **Coroutine Scoping**: Proper lifecycle management

### Error Handling
- **Network Errors**: Retry mechanisms with exponential backoff
- **HTTP Errors**: Specific error messages for different status codes
- **User Feedback**: Clear error states with actionable solutions

