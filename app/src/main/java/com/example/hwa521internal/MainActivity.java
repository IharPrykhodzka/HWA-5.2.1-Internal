package com.example.hwa521internal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private EditText mLogin;
    private EditText mPassword;
    Button btnLogin;
    Button btnRegistration;
    private static final String LOG = "myLogs";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLogin = findViewById(R.id.login);
        mPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistration = findViewById(R.id.btnRegistration);
        final String fileLogin = mLogin.getText().toString().trim();
        final String filePassword = mPassword.getText().toString().trim();

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fileContents = fileLogin + "\n" + filePassword;
                FileOutputStream outputStream = null;
                Log.d(LOG, "Нажал на кнопку регестрации");

                try {
                    if (!filePassword.equals("")) {
                        outputStream = openFileOutput(fileLogin, Context.MODE_PRIVATE);
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                        BufferedWriter bw = new BufferedWriter(outputStreamWriter);
                        bw.write(fileContents);

//                        outputStream.write(fileContents.getBytes());

                        Toast.makeText(MainActivity.this, fileLogin + " успешно зарегистрирован", Toast.LENGTH_LONG).show();
                        Log.d(LOG, "Создание и запись логина и пароля");
                    } else {
                        Toast.makeText(MainActivity.this, "Убедитесь, что вы заполнели все строки", Toast.LENGTH_LONG).show();
                        Log.d(LOG, "Пустой пароль");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Убедитесь, что вы заполнели все строки", Toast.LENGTH_LONG).show();
                    Log.d(LOG, "Ошибка создания и записи логина и пароля");
                } finally {
                    if (outputStream != null) {
                        try {
                            outputStream.close();
                            Log.d(LOG, "Закрыли поток outputStream");
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d(LOG, "Ошибка закрытие патока outputStream");
                        }
                    }
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FileInputStream fileInputStream = null;
                StringBuilder result = null;
                BufferedReader reader = null;
                ArrayList<String> mLoginPassword = null;
                Log.d(LOG, "Нажал на кнопку логин");

                try {
                    fileInputStream = openFileInput(fileLogin);
                    result = new StringBuilder();
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
                    reader = new BufferedReader(inputStreamReader);
                    String line = reader.readLine();
                    mLoginPassword = new ArrayList<>();
                    Log.d(LOG, "Пробуем прочитать учётную запись");

                    while (line != null) {
                        result.append(line);
                        mLoginPassword.add(result.toString().trim());
                        line = reader.readLine();
                        Log.d(LOG, "Пробуем записать учетку в массив");
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Такой учётной записи не существует. Вы можете зарегистрироваться.", Toast.LENGTH_LONG).show();
                    Log.d(LOG, "Ошибка, гдето null");
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Такая учётная запись уже существует.", Toast.LENGTH_LONG).show();
                    Log.d(LOG, "Ошибка создания");
                } finally {

                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                            Log.d(LOG, "Закрыли поток fileInputStream");
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d(LOG, "Ошибка закрытие патока fileInputStream");
                        }
                    }

                    if (reader != null) {
                        try {
                            reader.close();
                            Log.d(LOG, "Закрыли поток reader");
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d(LOG, "Ошибка закрытие патока reader");
                        }
                    }
                }


                assert mLoginPassword != null;
                if (mLoginPassword.get(0).equals(mLogin.getText().toString().trim()) &&
                        mLoginPassword.get(1).equals(mPassword.getText().toString().trim())) {
                    Toast.makeText(MainActivity.this, "Добро пожаловать : " + mLogin, Toast.LENGTH_LONG).show();
                }

                else {
                    Toast.makeText(MainActivity.this, "Вы ввели неправельно пароль!!!" + mLogin, Toast.LENGTH_LONG).show();
                }

            }
        });
    }


}