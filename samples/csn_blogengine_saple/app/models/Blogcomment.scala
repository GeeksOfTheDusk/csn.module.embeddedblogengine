package models

import java.util.Date
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._

case class Blogcomment(id: Long = 0,
                       authorID: Long,
                       authorST: String,
                       content: String,
                       creationTime: Date = new Date,
                       belongsTo: Long)

object Blogcomment {
  def entry = {
    get[Long]("id") ~ get[Long]("authorID") ~ get[String]("authorST") ~ get[String]("content") ~
      get[Date]("creation_time") ~ get[Long]("belongs_to") map {
      case id ~ author ~ nick ~ content ~ date ~ to =>
        Blogcomment(id, author, nick, content, date, to)
    }
  }

  def all = DB.withConnection {
    implicit c =>
      SQL("select * from Blogcomment").as(entry *)
  }

  def findAllByBlogEntry(id: Long) = DB.withConnection {
    implicit c =>
      SQL("select * from Blogcomment where belongs_to = {id}").on('id -> id).as(entry *)
  }

  def create(comment: Blogcomment) {
    DB.withConnection {
      implicit c =>
        SQL(
          """
          insert into Blogcomment
          (authorID, authorST, content, creation_time, belongs_to)
          values ({author}, {nick}, {content}, {time}, {to});
          """)
          .on('author -> comment.authorID, 'nick -> comment.authorST, 'content -> comment.content,
          'time -> comment.creationTime, 'to -> comment.belongsTo)
          .executeUpdate()
    }
  }

  def delete(id: Long) {
    DB.withConnection {
      implicit c =>
        SQL("delete from Blogcomment where id = {id}")
          .on('id -> id)
          .executeUpdate()
    }
  }
}
