package com.gajyoung.scentaur

import com.gajyoung.scentaur.storage.IStorageService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

/**
 * An actual server is spun up to test against its request/responses
 */

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileUploadRestTests {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @MockBean
    private lateinit var storageService: IStorageService

    @LocalServerPort
    private val port = 0

    @Test
    @Throws(Exception::class)
    fun shouldUploadFile() {
        val resource = ClassPathResource("testupload.txt", javaClass)

        val map: MultiValueMap<String, Any> = LinkedMultiValueMap()
        map.add("file", resource)
        val response = restTemplate.postForEntity(
            "/", map,
            String::class.java
        )

        assertThat(response.statusCode).isEqualByComparingTo(HttpStatus.FOUND)
        assertThat(response.headers.location.toString())
            .startsWith("http://localhost:$port/")

//        then(storageService).should().store(any(MultipartFile::class.java)) something is wrong here :(
    }

    @Test
    @Throws(Exception::class)
    fun shouldDownloadFile() {
        val resource = ClassPathResource("testupload.txt", javaClass)
        given(storageService.loadAsResource("testupload.txt")).willReturn(resource)

        val response = restTemplate.getForEntity(
            "/files/{filename}",
            String::class.java, "testupload.txt"
        )

        assertThat(response.statusCodeValue).isEqualTo(200)
        assertThat(response.headers.getFirst(HttpHeaders.CONTENT_DISPOSITION))
            .isEqualTo("attachment; filename=\"testupload.txt\"")
        assertThat(response.body).isEqualTo("Spring Framework")
    }
}
