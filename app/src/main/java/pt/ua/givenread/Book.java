package pt.ua.givenread;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "books")
public class Book {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "book_title")
    public String book_title;

    @ColumnInfo(name = "author")
    public String author;

    @ColumnInfo(name = "image")
    public String image;

    public Book(String book_title, String author, String image){
        this.book_title = book_title;
        this.author = author;
        this.image = image;
    }

    @NonNull
    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(@NonNull String book_title) {
        this.book_title = book_title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString(){
        return "[Book -> " + getBook_title() + ", " + getAuthor() + ", " + getImage() + "]";
    }
}
