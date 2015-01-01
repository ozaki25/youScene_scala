package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class Users(
  id: Long,
  username: String,
  name: String,
  sectionName: String
)

object Users {
  val user = {
    get[Long]("users.id") ~
    get[String]("users.username") ~
    get[String]("users.name") ~
    get[String]("users.sectionName") map {
      case id~username~name~sectionName => Users(
         id, username, name, sectionName
      )
    }
  }

  def findByUsername(username: String): Users = {
    DB.withConnection { implicit connection =>
      SQL("select * from users where username = {username}").on(
        'username -> username
      ).as(Users.user.single)
    }
  }

  def create(form: (String, String, String)) {
    DB.withConnection { implicit connection =>
      SQL("insert into users(username,name,sectionName) values ({username},{name},{sectionName})").on(
        'username -> form._1, 'name -> form._2, 'sectionName -> form._3
      ).executeUpdate()
    }
  }
}
