package com.aditya.springboot_kotlin.security

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class HashCodeEncoder {
    private val bCrypt = BCryptPasswordEncoder()
    fun encode (password: String): String = bCrypt.encode(password)
    fun matches(password: String, hashed: String): Boolean= bCrypt.matches(password,hashed)
}