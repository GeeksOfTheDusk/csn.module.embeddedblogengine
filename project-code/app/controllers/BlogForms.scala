package controllers

import play.api.data._
import play.api.data.Forms._


object BlogForms {
  val commentForm = Form(
    "content" -> nonEmptyText
  )

  val entryForm = Form(
    tuple(
      "title" -> nonEmptyText,
      "content" -> nonEmptyText,
      "privacy" -> nonEmptyText,
      "commentsAllowed" -> boolean
    )
  )
}
