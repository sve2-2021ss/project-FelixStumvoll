package com.urlshortener.core.util

inline fun <reified T : Throwable> Throwable.getException(): T? {
    var exFind = this

    while (exFind.cause != null) {
        if (exFind.cause is T) {
            return exFind.cause as T
        }

        exFind = exFind.cause!!
    }
    return null
}