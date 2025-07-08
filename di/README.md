# DI

Dependency Injection.

## Features

- Supports Circular Dependencies.
- Compile time process

## API

- `inject()`
- `@Bean`

## Usage

```kotlin
class ExampleController {
    private val service: ExampleService by inject()

    fun f(): List<String> {
        return service.f() + "Controller"
    }
}
```

```kotlin
interface ExampleService {
    fun f(): List<String>
}

@Bean
class ExampleServiceImpl : ExampleService {
    private val repository: ExampleRepository by inject()

    override fun f(): List<String> {
        return repository.f() + "Service"
    }
}
```

```kotlin
interface ExampleRepository {
    fun f(): List<String>
}

@Bean
class ExampleRepositoryImpl : ExampleRepository {
    override fun f(): List<String> {
        return listOf("Repository")
    }
}
```
