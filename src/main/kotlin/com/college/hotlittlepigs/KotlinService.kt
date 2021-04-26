package com.college.hotlittlepigs

import org.springframework.stereotype.Service

@Service
class KotlinService {
    fun test(): Map<String, String> {
        return mapOf(Pair("a", "b"))
    }
}