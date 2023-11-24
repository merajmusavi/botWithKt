package service

import Model.Entity

interface UserManagingService {
    fun insertUser(entites: List<Entity>): Boolean
    fun AllUser(): List<Entity>
    fun changestatus(username: String, status: Int): Boolean
    fun selectSpecificUser(username: String): Int

}