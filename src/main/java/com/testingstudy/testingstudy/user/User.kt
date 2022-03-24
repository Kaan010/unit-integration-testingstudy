package com.testingstudy.testingstudy.user

import javax.persistence.*

@Entity

class User @JvmOverloads constructor(
        @Id
        @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
        @GeneratedValue(generator = "user_sequence", strategy = GenerationType.SEQUENCE)
        val id: Long? = 0,
        val firstName: String,
        val email: String,
        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        var gender: Gender
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (id != other.id) return false
        if (firstName != other.firstName) return false
        if (email != other.email) return false
        if (gender != other.gender) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + firstName.hashCode()
        result = 31 * result + email.hashCode()
        result = 31 * result + gender.hashCode()
        return result
    }
}

enum class Gender {
    MALE,
    FEMALE,
    OTHER

}