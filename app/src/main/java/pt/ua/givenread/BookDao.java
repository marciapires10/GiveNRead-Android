package pt.ua.givenread;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Book book);


    @Query("DELETE FROM books")
    void deleteAll();

    @Delete
    void delete(Book book);

    @Query("SELECT * FROM books")
    LiveData<List<Book>> getAll();

    @Query("SELECT * FROM books WHERE type='ToGive'")
    LiveData<List<Book>> getAllToGive();

    @Query("SELECT * FROM books WHERE type='ToGive'")
    List<Book> getAllToGiveList();

    @Query("SELECT * FROM books WHERE type='ToRead'")
    LiveData<List<Book>> getAllToRead();

    @Query("SELECT * FROM books WHERE type='ToRead'")
    List<Book> getAllToReadList();

}
