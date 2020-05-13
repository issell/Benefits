package com.issell.benefits.util

import java.security.MessageDigest

fun String.md5(): String {
    return hashString(this, "MD5")
}


// SHA256 : Hash
// AES256 : 대칭키 ==> 키를 RSA 암호화
// RSA256 : 비대칭키

fun String.sha256(): String {
    return hashString(this, "SHA-256")
}

private fun hashString(input: String, algorithm: String): String {
    return MessageDigest
        .getInstance(algorithm)
        .digest(input.toByteArray())
        .fold("", { str, it -> str + "%02x".format(it) })
}