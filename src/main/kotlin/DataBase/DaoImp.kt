package DataBase

import Model.Entity
import java.io.FileInputStream
import java.io.IOException
import java.lang.RuntimeException
import java.sql.DriverManager
import java.util.LinkedList
import java.util.Properties

class DaoImp : Dao {
    private lateinit var HOST: String
    private lateinit var USER: String
    private lateinit var PASSWORD: String


    init {
        try {
            FileInputStream("config.properties").use { inputstream->
                val properties = Properties()
                properties.load(inputstream)
                HOST = properties.getProperty("host")
                USER = properties.getProperty("user")
                PASSWORD = properties.getProperty("password")

            }

        }catch (e : IOException){

            throw RuntimeException(e)
        }
    }

    override fun insertUser(entites: List<Entity>): Boolean {
        DriverManager.getConnection(HOST,USER,PASSWORD).use { connection ->
            val insertUserToDataBase = connection.prepareStatement("INSERT IGNORE INTO data(name, username) VALUES(?, ?)")
            insertUserToDataBase.setString(1,entites[0].name)
            insertUserToDataBase.setString(2,entites[0].username)

            val executeUpdate = insertUserToDataBase.executeUpdate()
            if (executeUpdate == 0){
                return false
            }

        }
        return true

    }

    override fun AllUser(): List<Entity> {
        val entities = LinkedList<Entity>()
        DriverManager.getConnection(HOST,USER,PASSWORD).use { connection ->
            val select = "SELECT * FROM data"
            val prepareStatement = connection.prepareStatement(select)
            val executeQuery = prepareStatement.executeQuery()
            while (executeQuery.next()){
                val name = executeQuery.getString("name")
                val username = executeQuery.getString("username")
                entities.add(Entity(name,username))
            }

        }
        return entities
    }

    override fun changestatus(username: String, status: Int): Boolean {
        DriverManager.getConnection(HOST,USER,PASSWORD).use { connection ->
            val update = "UPDATE data set status = ? WHERE username = ?"
            val prepareStatement = connection.prepareStatement(update)
            prepareStatement.setInt(1,status)
            prepareStatement.setInt(2,status)



            var executeUpdate = prepareStatement.executeUpdate()

            if (executeUpdate>0){
                return true
            }
        }
        return false
    }

    override fun selectSpecificUser(username: String): Int {
        var status = 0
        DriverManager.getConnection(HOST,USER,PASSWORD).use { connection ->
            val select = "SELECT * FROM data WHERE username = ?"
            val prepareStatement = connection.prepareStatement(select)
            prepareStatement.setString(1,username)

            val executeQuery = prepareStatement.executeQuery()

            while (executeQuery.next()){

               status = executeQuery.getInt("status")
            }

        }
        return status
    }


}