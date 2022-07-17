package com.gajyoung.scentaur

import com.gajyoung.scentaur.storage.IStorageService
import com.gajyoung.scentaur.storage.StorageFileNotFoundException
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.header
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.nio.file.Paths
import java.util.stream.Stream

/**
 * Note: when you want to test server-side use MockMvc. Does not require servlet container
 */
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FileUploadTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var storageService: IStorageService

    @Test
    @Throws(Exception::class)
    fun shouldListAllFiles() {
        given(storageService.loadAll())
            .willReturn(Stream.of(Paths.get("first.txt"), Paths.get("second.txt")))
        mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(
            model().attribute(
                "files",
                Matchers.contains("http://localhost/files/first.txt", "http://localhost/files/second.txt")
            )
        )
    }

    @Test
    @Throws(Exception::class)
    fun shouldSaveUploadedFile() {
        val multipartFile = MockMultipartFile(
            "file", "test.txt", "text/plain",
            "Spring Framework".toByteArray()
        )

        mockMvc.perform(multipart("/").file(multipartFile))
            .andExpect(status().isFound)
            .andExpect(header().string("Location", "/"))

        then(storageService).should().store(multipartFile)
    }

    @Test
    @Throws(Exception::class)
    fun should404WhenMissingFile() {
        given(storageService.loadAsResource("test.txt"))
            .willThrow(StorageFileNotFoundException::class.java)

        mockMvc.perform(get("/files/test.txt")).andExpect(status().isNotFound)
    }
}
