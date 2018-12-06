package io.example.sample.repository

data class RepositoryResponse<out T>(val data: T?, val error: Boolean)