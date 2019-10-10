package org.signcollector.server

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.core.env.Environment


fun String.white() = "\u001b[37m${this}\u001b[0m"
fun String.cyan() = "\u001b[36m${this}\u001b[0m"
fun String.green() = "\u001b[32m${this}\u001b[0m"
fun String.bgBlack() = "\u001b[40m${this}\u001b[0m"


@Configuration
class DatabaseSetup {
	private val logger = LoggerFactory.getLogger(DatabaseSetup::class.java)

	@Autowired
	lateinit var env: Environment

	@Bean
	@Profile("dev")
	fun cleanMigrateStrategy(): FlywayMigrationStrategy {

		return FlywayMigrationStrategy { flyway ->

			if(env.getProperty("CLEAN_DB") != null){
				logger.info("--- CLEANING DB ---".white().bgBlack())
				flyway.clean()
			}
			flyway.migrate()
		}
	}

}
