package com.college.hotlittlepigs

import org.springframework.stereotype.Service

@Service
class KotlinService {
    fun test(): Map<String, String> = mapOf(Pair("a", "b"))
}