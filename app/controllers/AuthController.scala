package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import models._
import views._

object AuthController extends Controller {
  val signupForm = Form(
    tuple(
      "username" -> nonEmptyText,
      "name" -> nonEmptyText,
      "sectionName" ->  nonEmptyText
    )
  )

  val signinForm = Form(
    tuple(
      "username" -> nonEmptyText,
      "password" -> text
    )
  )

  def entry = Action { request =>
    val user = currentUser(request)
    Ok(html.auth.entry(user, signupForm))
  }
  
  def signup = Action { implicit request =>
    val user = currentUser(request)
    signupForm.bindFromRequest.fold(
      errors => {
        BadRequest(html.auth.entry(user, errors))
      },
      form => {
        Users.create(form)
        Redirect(routes.AuthController.signin)
      }
    )
  }

  def signin = Action { request =>
    val user = currentUser(request)
    Ok(html.auth.signin(user, signinForm))
  }

  def authenticate = Action { implicit request =>
    val user = currentUser(request)
    signinForm.bindFromRequest.fold(
      errors => {
        BadRequest(html.auth.signin(user, errors))
      },
      form => {
        val user = Users.findByUsername(form._1)

        Redirect(routes.BlogsController.index).withSession(
          "username" -> user.username,
          "name" -> user.name,
          "sectionName" -> user.sectionName
        )
      }
    )
  }

  def logout = Action {
    Redirect(routes.AuthController.signin).withNewSession
  }

  def currentUser(request: RequestHeader) = Users.findByUsername(username(request))
  def username(request: RequestHeader) = request.session.get("username")
}
