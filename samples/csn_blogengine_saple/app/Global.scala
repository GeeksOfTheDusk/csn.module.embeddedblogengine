import play.api._
import models._

object Global extends GlobalSettings {
  override def onStart(app: Application) {
    if(Blogentry.all.isEmpty) {
      val entry = Blogentry(creatorID = 1, creatorST = "frogurth", title = "Test blogentry", content="""
      This is a test entry.
      """, commentsAllowed = true)
      Blogentry.create(entry)

      val comment = Blogcomment(authorID = 2, authorST = "god", content = "Nice entry.", belongsTo = 1)
      Blogcomment.create(comment)
    }
  }
}
