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

  def entry = Action {
    Ok(html.auth.entry(signupForm))
  }
  
  def signup = Action { implicit request =>
    signupForm.bindFromRequest.fold(
      errors => {
        BadRequest(html.auth.entry(errors))
      },
      form => {
        Users.create(form)
        Redirect(routes.AuthController.signin)
      }
    )
  }

  def signin = Action {
    Ok(html.auth.signin(signinForm))
  }

  def authenticate = Action { implicit request =>
    signinForm.bindFromRequest.fold(
      errors => {
        BadRequest(html.auth.signin(errors))
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
}
