package br.com.valemobi.blog

import br.com.valemobi.blog.models.Post
import br.com.valemobi.blog.repositories.PostRepository
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDate

@SpringBootTest
@AutoConfigureMockMvc
class BlogApplicationTests {

	@Autowired
	lateinit var mockMvc: MockMvc
	@Autowired
	lateinit var repository: PostRepository

	@Test
	fun testFindAllPost() {
		repository.save(Post(title = "test", description = "test", body = "test", createdAt = LocalDate.now(),
				updateAt = LocalDate.now()))

		mockMvc.perform(MockMvcRequestBuilders.get("/posts/"))
				.andExpect(MockMvcResultMatchers.status().isOk)
				.andDo(MockMvcResultHandlers.print())
	}

	@Test
	fun testFindAllPostByCreateDate() {
		repository.save(Post(title = "test", description = "test", body = "test", createdAt = LocalDate.now(),
				updateAt = LocalDate.now()))

		mockMvc.perform(MockMvcRequestBuilders.get("/posts/"+LocalDate.now().toString()))
				.andExpect(MockMvcResultMatchers.status().isOk)
				.andDo(MockMvcResultHandlers.print())
	}

	@Test
	fun testFindAllPostByCreateDateGap() {

		repository.save(Post(title = "test", description = "test", body = "test", createdAt = LocalDate.now(),
				updateAt = LocalDate.now()))

		mockMvc.perform(MockMvcRequestBuilders.get("/posts/${LocalDate.now().minusDays(1)
				.toString()}/${LocalDate.now().toString()}"))

				.andExpect(MockMvcResultMatchers.status().isOk)
				.andDo(MockMvcResultHandlers.print())
	}

	@Test
	fun testCreatePost() {
		val post = Post(title = "test", description = "test", body = "test", createdAt = LocalDate.now(),
				updateAt = LocalDate.now())

		val mapper: ObjectMapper = ObjectMapper()
		mapper.findAndRegisterModules()

        repository.deleteAll()

		val postJson = mapper.writeValueAsString(post)

		mockMvc.perform(MockMvcRequestBuilders.post("/posts")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(postJson))
				.andExpect(MockMvcResultMatchers.status().isCreated)
				.andDo(MockMvcResultHandlers.print())
		Assertions.assertFalse(repository.findAll().isEmpty())
	}

	@Test
	fun testUpdatePost(){
		val post = repository.save(Post(title = "test", description = "test", body = "test",
				createdAt = LocalDate.now(), updateAt = LocalDate.now()))
				.copy(description = "update description")
		val mapper: ObjectMapper = ObjectMapper()
		mapper.findAndRegisterModules()

		val postJson = mapper.writeValueAsString(post)

		mockMvc.perform(MockMvcRequestBuilders.put("/posts/${post.id}")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(postJson))
				.andExpect(MockMvcResultMatchers.status().isOk)
				.andDo(MockMvcResultHandlers.print())

		val postFindById = repository.findById(post.id!!)
        Assertions.assertTrue(postFindById.isPresent)
        Assertions.assertEquals(post.description, postFindById.get().description)
	}

	@Test
	fun testDeletePost() {
		val post = repository.save(Post(title = "test", description = "test", body = "test",
				createdAt = LocalDate.now(), updateAt = LocalDate.now()))

		mockMvc.perform(MockMvcRequestBuilders.delete("/posts/${post.id}"))
				.andExpect(MockMvcResultMatchers.status().isOk)
				.andDo(MockMvcResultHandlers.print())

		val postDeleteConfirm = repository.findById(post.id!!)
		Assertions.assertFalse(postDeleteConfirm.isPresent)
	}

}
