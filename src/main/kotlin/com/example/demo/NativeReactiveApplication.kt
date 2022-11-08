package com.example.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class NativeReactiveApplication

fun main(args: Array<String>) {
	runApplication<NativeReactiveApplication>(*args)
}
