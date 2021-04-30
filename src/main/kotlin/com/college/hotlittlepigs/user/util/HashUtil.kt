package com.college.hotlittlepigs.user.util

import java.security.MessageDigest

fun String.toSecureHash(): String {
    return MessageDigest.getInstance("SHA-256")
        .digest(this.toByteArray())
        .fold("") { str, it -> str + "%02x".format(it) }
}


// TODO This will be obviuously deleted
//  I just created it because user service was not migrated to kotlin yet,
//  So it cannot use .toSecureHash on String objects
fun toSecureHash___temporary(text: String): String {
    return MessageDigest.getInstance("SHA-256")
        .digest(text.toByteArray())
        .fold("") { str, it -> str + "%02x".format(it) }
}