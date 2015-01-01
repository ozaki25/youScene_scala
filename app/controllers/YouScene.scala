package controllers

import play.api._
import play.api.mvc._

import models._

object YouScene extends Controller {
  def currentUser(request: RequestHeader) = Users.findByUsername(username(request))

  def username(request: RequestHeader) = request.session.get("username")
}
