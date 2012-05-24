package controllers

import models._
import play.api.data.Form
import play.api.templates.Html
import play.api.mvc.{AnyContent, Action, Controller}
import java.util.Date
import java.text.SimpleDateFormat

object BlogEngine extends Controller {
  def formatDate(date: Date) = {
    new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(date)
  }

  var commentFormToHtml: (Form[String], Blogentry, play.api.mvc.Request[AnyContent]) => Html = null

  var entryFormToHtml: (Form[(String, String, String, Boolean)], play.api.mvc.Request[AnyContent]) => Html = null

  def showEntry(id: Long) = Action { implicit request =>
    if(request.session.get("user") != None) {
      val entry = Blogentry.findByID(id)
      Ok(commentFormToHtml(BlogForms.commentForm, entry, request))
    } else {
      Redirect("/")
    }
  }

  def newComment(id: Long) = Action { implicit request =>
    val entry = Blogentry.findByID(id)
    BlogForms.commentForm.bindFromRequest.fold(
      errorForm => BadRequest(commentFormToHtml(errorForm, entry, request)),
      content => {
        val comment = Blogcomment( content = content,
                                   authorID = request.session.get("id").get.toLong,
                                   authorST = request.session.get("userST").get, belongsTo = id)
        Blogcomment.create(comment)
        Redirect("/blog/entry/"+entry.id)
      }
    )
  }

  def newEntry = Action { implicit request =>
    Ok(entryFormToHtml(BlogForms.entryForm, request))
  }

  def createEntry = Action { implicit request =>
    BlogForms.entryForm.bindFromRequest.fold(
      errorForm => BadRequest(entryFormToHtml(errorForm, request)),
      values => {
        val authorID = request.session.get("id").get.toLong
        val authorST = request.session.get("userST").get
        val entry = Blogentry(title = values._1, content = values._2, creatorID = authorID,
          creatorST = authorST, privacy = values._3.toInt, commentsAllowed = values._4)
        Blogentry.create(entry)
        Redirect("/")
      }
    )
  }
}
