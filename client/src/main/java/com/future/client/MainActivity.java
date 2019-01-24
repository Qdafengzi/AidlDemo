package com.future.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.future.aidldemo.Book;
import com.future.aidldemo.BookController;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Button btn_get;
    private Button btn_In;
    private Button btn_Out;
    private Button btn_Out_In;
    private List<Book> mBookList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_get = findViewById(R.id.btn_get);
        btn_In = findViewById(R.id.btn_In);
        btn_Out = findViewById(R.id.btn_Out);
        btn_Out_In = findViewById(R.id.btn_Out_In);


        Intent intent = new Intent();
        intent.setAction("com.future.aidldemo.action");
        intent.setPackage("com.future.aidldemo");
        bindService(intent, conn, Context.BIND_AUTO_CREATE);


        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bindService) {
                    try {
                        mBookList = mBookController.getBookList();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                log();
            }
        });
        btn_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bindService) {
                    try {
                        Book book = new Book("这是一本新书：In");
                        mBookController.addBookIn(book);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        btn_Out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bindService) {
                    try {
                        Book book = new Book("这是一本新书：Out");
                        mBookController.addBookIn(book);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        btn_Out_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bindService) {
                    try {
                        Book book = new Book("这是一本新书：InOut");
                        mBookController.addBookIn(book);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }



    private void log() {
        for (Book book : mBookList) {
            Log.e(TAG, book.toString());
        }
    }


    BookController mBookController;
    boolean bindService;

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBookController = BookController.Stub.asInterface(service);
            bindService = true;
            Log.d(TAG, "绑定服务成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bindService = false;
            Log.d(TAG, "绑定服务失败");
        }
    };
}
