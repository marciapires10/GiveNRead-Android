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
    public final String book_title;

    @ColumnInfo(name = "author")
    public final String author;

    @NonNull
    @ColumnInfo(name = "isbn")
    public final String isbn;

    @ColumnInfo(name = "image")
    public final String image;

    @NonNull
    @ColumnInfo(name = "type")
    public final String type;

    public Book(@NonNull String book_title, String author, @NonNull String isbn, String image, @NonNull String type){
        this.book_title = book_title;
        this.author = author;
        this.isbn = isbn;
        this.image = image;
        this.type = type;
    }

    @NonNull
    public String getBook_title() {
        return book_title;
    }

    public String getAuthor() {
        return author;
    }

    public String getImage() {
        return image;
    }

    @NonNull
    public String getIsbn() {
        return isbn;
    }

    @NonNull
    public String getType() {
        return type;
    }

    @NonNull
    @Override
    public String toString(){
        return "[Book -> " + getBook_title() + ", " + getAuthor() + ", " + getIsbn() + ", " + getImage() + ", " + getType() + "]";
    }
}
