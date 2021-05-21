package com.urlshortener.core.exceptions

class EntityModificationException(message: String) : RuntimeException(message)
class EntityNotFoundException(message: String) : RuntimeException(message)
class ShortNameAlreadyExistsException(name: String) : RuntimeException("url with shortname $name already exists")
