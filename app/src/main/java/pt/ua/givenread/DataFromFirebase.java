package pt.ua.givenread;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class DataFromFirebase extends Application {

    private static FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance("https://givenread-android-default-rtdb.europe-west1.firebasedatabase.app/");
    private static DatabaseReference databaseReference = firebaseDatabase.getReference("BookInfo");

    private static List<List<String>> booksNotified = new ArrayList<>();
    private static List<String> notifications = new ArrayList<>();


    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public static void addDatatoFirebase(String book_title, ArrayList<String> authors, String isbn, String bookstop) {

        BookInfo bookToDB = new BookInfo();
        BookInfo.BookInfoFirebase bookInfoToDB;

        bookToDB.setTitle(book_title);
        bookToDB.setAuthors(authors);

        bookInfoToDB = new BookInfo.BookInfoFirebase(book_title, authors, isbn, bookstop);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.child(isbn).push().setValue(bookInfoToDB);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void removeDataFromFirebase(String isbn, String bookstop) {

        Log.d("bookstop", bookstop);

        databaseReference.child(isbn).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> booksRemoved = new ArrayList<>();
                for (DataSnapshot s : snapshot.getChildren()){
                    if(s.child("bookstop").getValue().toString().equals(bookstop) && !booksRemoved.contains(s.child("book_title").getValue().toString())){
                        s.getRef().removeValue();
                        booksRemoved.add(s.child("book_title").getValue().toString());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static void getAllDataFromFirebase(BookViewModel viewModel, Context applicationContext)
    {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                List<Book> booksToRead = null;
                try {
                    booksToRead = viewModel.getBooksToReadList();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (DataSnapshot snap : snapshot.getChildren()){
                    for (DataSnapshot s : snap.getChildren()){
                        for (Book book : booksToRead){
                            List<String> bookWithBookstop = new ArrayList<>();
                            bookWithBookstop.add(s.child("book_title").getValue().toString());
                            bookWithBookstop.add(s.child("bookstop").getValue().toString());
                            if(s.child("book_title").getValue().toString().equals(book.book_title) && !booksNotified.contains(bookWithBookstop)){
                                booksNotified.add(bookWithBookstop);
                                String notification_body = "The book " + book.book_title + " is at " + s.child("bookstop").getValue().toString();
                                BookAdapter.sendNotification("You have a book match!", notification_body, applicationContext);
                                notifications.add(notification_body);
                            }
                        }

                    }
                }

                Log.d("notifications", notifications.toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public static List<String> getNotifications() {
        return notifications;
    }
}
