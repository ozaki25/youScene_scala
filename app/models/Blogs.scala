package models

import java.util.Date

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

case class Blogs(
  id: Long,
  title: String,
  body: String,
  createdDate: Option[Date],
  updatedDate: Option[Date]
)

object Blogs {
  val blog = {
    get[Long]("blogs.id") ~
    get[String]("blogs.title") ~
    get[String]("blogs.body") ~
    get[Option[Date]]("blogs.created_date") ~
    get[Option[Date]]("blogs.updated_date") map {
      case id~title~body~createdDate~updatedDate => Blogs(
        id, title, body, createdDate, updatedDate
      )
    }
  }

  def all(): List[Blogs] = DB.withConnection { implicit connection =>
    SQL("select * from blogs").as(blog *)
  }

  def findById(id: Long): Blogs = {
    DB.withConnection { implicit connection =>
      SQL("select * from blogs where id = {id}").on(
        'id -> id
      ).as(Blogs.blog.single)
    }
  }

  def create(form: (String,String)) {
    DB.withConnection { implicit connection =>
      SQL("insert into blogs(title,body) values ({title},{body})").on(
        'title -> form._1, 'body -> form._2
      ).executeUpdate()
    }
  }

  def update(form: (String,String), id: Long) {
    DB.withConnection { implicit connection =>
      SQL("update blogs set title = {title}, body = {body} where id = {id}").on(
        'title -> form._1, 'body -> form._2, 'id -> id
      ).executeUpdate()
    }
  }
}
