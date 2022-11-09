package com.example.demo

import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.annotation.Id
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.coRouter

@SpringBootApplication
class NativeReactiveApplication {

	@Bean
	fun loadDummyDataOnDB(customerRepository: CustomerRepository) = ApplicationRunner {
		runBlocking {
			val customers = flowOf("Maria", "John", "Mario")
				.map { Customer(null, it) }

			customerRepository.saveAll(customers)
				.collect { println(it) }
		}
	}

	@Bean
	fun http(customerRepository: CustomerRepository) = coRouter {
		GET("/customers") {
			ServerResponse.ok().bodyAndAwait(customerRepository.findAll())
		}
	}
}

fun main(args: Array<String>) {
	runApplication<NativeReactiveApplication>(*args)
}

interface CustomerRepository : CoroutineCrudRepository<Customer, Int>
data class Customer(@Id val id: Int?, val name: String)