package models

import java.util.Date
import play.api.db.DB

case class Blogentry(id: Long = 0,
                     belogsTo: Long,
                     title: String,
                     content: String,
                     commentsAllowed: Boolean,
                     creationTime: Date = new Date)

object Blogentry {
  def entry = {
    get[Long]("id") ~ get[Long]("belogs_to") ~ get[String]("title") ~ get[String]("content") ~ get[Date]("creation_time") ~
      get[Boolean]("allow_commnets") map {
      case id~belongsTo~title~content~date~comments => Blogentry(id, belogsTo, title, content, comments, date)
    }
  }

  def all = DB.withConnection { implicit c =>
    SQL("select * from Blogentry").as(enty *)
  }

  def create(entry: Blogentry) {
    DB.withConnection { implicit c =>
      SQL(
        """
        insert into Blogentry
        (belogs_to, title, content, creation_time, allow_commnets)
        values ( {to}, {title}, {content}, {time}, {comments});
        """)
        .on('to -> entry.belogsTo, 'title -> entry.title, 'content -> entry.content,
        'time -> entry.creationTime, 'comments -> entry.commentsAllowed)
        .executeUpdate()
    }
  }

  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from Blogentry where id = {id}")
        .on('id -> id)
        .executeUpdate()
    }
  }
}
