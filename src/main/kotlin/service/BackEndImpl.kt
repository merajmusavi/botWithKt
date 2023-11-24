package service

import DataBase.Dao
import DataBase.DaoImp
import Model.Entity
import org.telegram.telegrambots.meta.api.objects.User

class BackEndImpl(private val dao:Dao):UserManagingService {
    constructor() : this(DaoImp())
    override fun insertUser(entites: List<Entity>): Boolean {

       return dao.insertUser(entites)
    }

    override fun AllUser(): List<Entity> {
        return dao.AllUser()
    }

    override fun changestatus(username: String, status: Int): Boolean {
return dao.changestatus(username,status)
    }

    override fun selectSpecificUser(username: String): Int {
return dao.selectSpecificUser(username)
    }
}