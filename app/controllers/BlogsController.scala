package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import models._
import views._

object BlogsController extends YouScene {
  val blogForm = Form(
    tuple(
      "title" -> nonEmptyText,
      "body" ->  nonEmptyText
    )
  )

  def index = Action {
    val user = currentUser()
    val blogs = Blogs.all()
    Ok(html.blogs.index(user, blogs))
  }
  
  def show(id: Long) = Action {
    val user =  currentUser()
    val blog = Blogs.findById(id)
    Ok(html.blogs.show(user, blog))
  }

  def newBlog = Action {
    val user =  currentUser()
    Ok(html.blogs.newBlog(user, blogForm))
  }

  def create = Action { implicit request =>
    val user = currentUser()
    blogForm.bindFromRequest.fold(
      errors => {
        BadRequest(html.blogs.newBlog(user, errors))
      },
      form => {
        Blogs.create(form)
        Redirect(routes.BlogsController.show(1))
      }
    )
  }

  def edit(id: Long) = Action {
    val user = currentUser()
    val blog = Blogs.findById(id)
    Ok(html.blogs.edit(user, id, blogForm.fill(blog.title,blog.body)))
  }

  def update(id: Long) = Action { implicit request =>
    val user = currentUser()
    blogForm.bindFromRequest.fold(
      error => {
        BadRequest(html.blogs.edit(user, id, error))
      },
      form => {
        Blogs.update(form, id)
        Redirect(routes.BlogsController.show(id))
      }
    )
  }
}
