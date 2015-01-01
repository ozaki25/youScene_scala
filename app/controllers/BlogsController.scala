package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import models._
import views._

object BlogsController extends Controller {
  val blogForm = Form(
    tuple(
      "title" -> nonEmptyText,
      "body" ->  nonEmptyText
    )
  )

  def index = Action {
    val blogs = Blogs.all()
    Ok(html.blogs.index(blogs))
  }
  
  def show(id: Long) = Action {
    val blog = Blogs.findById(id)
    Ok(html.blogs.show(blog))
  }

  def newBlog = Action {
    Ok(html.blogs.newBlog(blogForm))
  }

  def create = Action { implicit request =>
    blogForm.bindFromRequest.fold(
      errors => {
        BadRequest(html.blogs.newBlog(errors))
      },
      form => {
        Blogs.create(form)
        Redirect(routes.BlogsController.show(1))
      }
    )
  }

  def edit(id: Long) = Action {
    val blog = Blogs.findById(id)
    Ok(html.blogs.edit(id, blogForm.fill(blog.title,blog.body)))
  }

  def update(id: Long) = Action { implicit request =>
    blogForm.bindFromRequest.fold(
      error => {
        BadRequest(html.blogs.edit(id, error))
      },
      form => {
        Blogs.update(form, id)
        Redirect(routes.BlogsController.show(id))
      }
    )
  }
}
