package br.com.valemobi.blog.repositories

import br.com.valemobi.blog.models.Post
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.LocalDate

interface PostRepository: JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p WHERE p.createdAt = :date")
    fun findByCreationDate(@Param("date")date: LocalDate): List<Post>

    fun findByCreatedAtBetween(dateOne: LocalDate, dateTwo: LocalDate): List<Post>
}