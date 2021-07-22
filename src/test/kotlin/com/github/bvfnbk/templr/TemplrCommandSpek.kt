package com.github.bvfnbk.templr

import assertk.assertThat
import assertk.assertions.isFailure
import com.github.bvfnbk.templr.api.TemplrApplication
import com.github.bvfnbk.templr.api.model.ApplicationArguments
import io.mockk.mockk
import io.mockk.verify
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.File

/**
 * @author bvfnbk
 */
object TemplrCommandSpek : Spek({
    describe("Required parameters are validated.") {
        it("The --model parameter is required.") {
            // Given
            val args = arrayOf("--template", "template.ftl", "output")
            val app = mockk<TemplrApplication>(relaxUnitFun = true)
            val command = TemplrCommand(app)

            // When/Then
            assertThat {
                command.parse(args)
            }.isFailure()
        }

        it("The --template parameter is required.") {
            // Given
            val args = arrayOf("--model", "model.json", "output")
            val app = mockk<TemplrApplication>(relaxUnitFun = true)
            val command = TemplrCommand(app)

            // When/Then
            assertThat {
                command.parse(args)
            }.isFailure()
        }
    }

    describe("The output can be directed to a file.") {
        val expected = File("output")
        val commonArguments = arrayOf("--model", "model.json", "--template", "template.ftl")
        val expectedArguments = ApplicationArguments(
            Charsets.UTF_8,
            File("model.json"),
            File("template.ftl"),
            expected,
            emptyMap()
        )

        it("Using short option") {
            // Given
            val args = commonArguments + arrayOf("-O", expected.name)
            val app = mockk<TemplrApplication>(relaxUnitFun = true)
            val command = TemplrCommand(app)

            // When
            command.parse(args)

            // Then
            verify {
                app.run(expectedArguments)
            }
        }

        it("Using long option") {
            // Given
            val args = commonArguments + arrayOf("--output", expected.name)
            val app = mockk<TemplrApplication>(relaxUnitFun = true)
            val command = TemplrCommand(app)

            // When
            command.parse(args)

            // Then
            verify {
                app.run(expectedArguments)
            }
        }
    }

    describe("The charset can be configured") {
        it("The default charset is used if nothing specified") {
            // Given
            val args = arrayOf("--model", "model.json", "--template", "template.ftl")
            val app = mockk<TemplrApplication>(relaxUnitFun = true)
            val command = TemplrCommand(app)

            // When
            command.parse(args)

            // Then
            verify {
                app.run(
                    ApplicationArguments(
                        Charsets.UTF_8,
                        File("model.json"),
                        File("template.ftl"),
                        null,
                        emptyMap()
                    )
                )
            }
        }


        describe("The specified charset is being used.") {
            val expected = Charsets.ISO_8859_1
            val commonArguments = arrayOf("--model", "model.json", "--template", "template.ftl")
            val expectedArguments = ApplicationArguments(
                Charsets.ISO_8859_1,
                File("model.json"),
                File("template.ftl"),
                null,
                emptyMap()
            )

            it("Using short option") {
                // Given
                val args = commonArguments + arrayOf("-c", expected.name())
                val app = mockk<TemplrApplication>(relaxUnitFun = true)
                val command = TemplrCommand(app)

                // When
                command.parse(args)

                // Then
                verify {
                    app.run(expectedArguments)
                }
            }

            it("Using long option") {
                // Given
                val args = commonArguments + arrayOf("--charset", expected.name())
                val app = mockk<TemplrApplication>(relaxUnitFun = true)
                val command = TemplrCommand(app)

                // When
                command.parse(args)

                // Then
                verify {
                    app.run(expectedArguments)
                }
            }
        }
    }

    describe("Properties are passed to the application") {
        it("An empty property map is passed if commandline does not contain a property") {
            // Given
            val args = arrayOf("--model", "model.json", "--template", "template.ftl")
            val app = mockk<TemplrApplication>(relaxUnitFun = true)
            val command = TemplrCommand(app)

            // When
            command.parse(args)

            // Then
            verify {
                app.run(
                    ApplicationArguments(
                        Charsets.UTF_8,
                        File("model.json"),
                        File("template.ftl"),
                        null,
                        emptyMap()
                    )
                )
            }
        }

        it("The passed properties are contained in the map passed to the application") {
            // Given
            val args = arrayOf(
                "--model",
                "model.json",
                "--template",
                "template.ftl",
                "-Dkey=value",
                "-D",
                "other=value",
            )
            val app = mockk<TemplrApplication>(relaxUnitFun = true)
            val command = TemplrCommand(app)

            // When
            command.parse(args)

            // Then
            verify {
                app.run(
                    ApplicationArguments(
                        Charsets.UTF_8,
                        File("model.json"),
                        File("template.ftl"),
                        null,
                        mapOf(
                            "key" to "value",
                            "other" to "value"
                        )
                    )
                )
            }
        }
    }
})
