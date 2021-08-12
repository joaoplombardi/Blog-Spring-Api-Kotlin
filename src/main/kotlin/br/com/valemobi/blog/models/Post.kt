package br.com.valemobi.blog.models

import java.time.LocalDate
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "TB_POST")
data class Post(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
        @Column(name = "tit_post", length = 20, nullable = false)
        val title: String,
        @Column(name = "ds_post", length = 50, nullable = false)
        val description: String,
        @Column(name = "body_post", length = 1000, nullable = false)
        val body: String,
        @Column(name = "dt_create_post", nullable = false)
        val createdAt: LocalDate,
        @Column(name = "dt_update_post", nullable = false)
        val updateAt: LocalDate,
)
