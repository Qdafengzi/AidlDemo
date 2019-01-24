package com.future.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tony
 * @date 2019/1/24
 */
public class AIDLService extends Service {

    private final static String TAG= "AIDLService";

    private List<Book> bookList = new ArrayList<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return sub;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Book book1 = new Book("book1");
        Book book2 = new Book("book2");
        Book book3 = new Book("book3");
        Book book4 = new Book("book4");
        Book book5 = new Book("book5");
        Book book6 = new Book("book6");
        bookList.add(book1);
        bookList.add(book2);
        bookList.add(book3);
        bookList.add(book4);
        bookList.add(book5);
        bookList.add(book6);
    }


    private BookController.Stub sub = new BookController.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void addBookInOut(Book book) throws RemoteException {
            if (book != null) {
                book.setName("服务器改了新书的名字 InOut-------");
                bookList.add(book);
            }
        }

        @Override
        public void addBookIn(Book book) throws RemoteException {
            if (book != null) {
                book.setName("服务器改了新书的名字 In------");
                bookList.add(book);
            }
        }

        @Override
        public void addBookOut(Book book) throws RemoteException {
            if (book != null) {
                book.setName("服务器改了新书的名字 Out-------");
                bookList.add(book);
            }
        }
    };
}
