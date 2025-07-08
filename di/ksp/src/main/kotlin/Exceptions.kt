package net.mioyi.kvertx.di.ksp

class InvalidQualifierException : Exception("Can only contain ASCII printable characters (32-126)")

class RequireNoArgConstructorException() : Exception("Bean must have a no-arg constructor")