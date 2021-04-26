package com.college.hotlittlepigs

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("test")
@RestController
class KotlinResource {
    @Autowired
    lateinit var kotlinService: KotlinService

    @GetMapping
    fun test(): Map<String, String> = kotlinService.test()
}