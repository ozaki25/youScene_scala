package controllers

import play.api._
import play.api.mvc._

import views.html._

object BlogsController extends Controller {
  
  def index = Action {
    Ok(blogs.index("Your new application is ready."))
  }
  
  def create = Action {
    Ok(blogs.create("new blogs"))
  }
}
