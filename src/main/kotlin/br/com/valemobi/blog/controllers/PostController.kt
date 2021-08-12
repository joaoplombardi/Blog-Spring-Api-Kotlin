package br.com.valemobi.blog.controllers

import br.com.valemobi.blog.models.Post
import br.com.valemobi.blog.repositories.PostRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/posts")
class PostController {

    @Autowired
    lateinit var repository: PostRepository

    @GetMapping
    fun listAll(): List<Post> = repository.findAll()

    @GetMapping("/{date}")
    fun listByDate(@PathVariable("date") dateString: String): List<Post> {
        val date = LocalDate.parse(dateString)
        return repository.findByCreationDate(date)
    }

    @GetMapping("/{dateOne}/{dateTwo}")
    fun listByDateGap(@PathVariable("dateOne") dateOneString: String,
                      @PathVariable("dateTwo") dateTwoString: String): List<Post>{
        val dateOne = LocalDate.parse(dateOneString)
        val dateTwo = LocalDate.parse(dateTwoString)
        return repository.findByCreatedAtBetween(dateOne, dateTwo)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody post: Post): Post{
        return repository.save(post)
    }


    @PutMapping("/{id}")
    fun updatePost(@PathVariable("id") id:Long, @RequestBody post: Post) : ResponseEntity<Post>{
        return repository.findById(id).map {
            val postUpdated = it.copy(
                    title = post.title,
                    description = post.description,
                    body = post.body,
                    createdAt = it.createdAt,
                    updateAt = LocalDate.now()
            )
            val update = repository.save(postUpdated)
            ResponseEntity.ok(update)
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable("id") id: Long): ResponseEntity<Void> {
        return repository.findById(id).map {
            repository.delete(it)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
    }

}
