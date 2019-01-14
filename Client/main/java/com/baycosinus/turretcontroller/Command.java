package com.baycosinus.turretcontroller;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Baycosinus on 18.03.2018.
 */

public class Command extends AsyncTask<String,Void,Void> {
    private Exception exception;
    public String HOST = "192.168.1.25";
    public int PORT = 9000;
    public static boolean status = false;
    public static String response = "";
    @Override
    protected Void doInBackground(String... params)
    {
        HOST = MainActivity.HOST;
        PORT = MainActivity.PORT;
        try {
            Socket writeSocket = new Socket(HOST,PORT);
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(writeSocket.getOutputStream()));
            writer.print(params[0]);
            writer.flush();
            /*try {

                BufferedReader reader = new BufferedReader(new InputStreamReader(writeSocket.getInputStream()));
                int charsRead = 0;
                char[] buffer = new char[256];
                while ((charsRead = reader.read(buffer)) != -1) {
                    response += new String(buffer).substring(0, charsRead);
                }
            }
            catch (IOException ie)
            {
                Log.e("Exception",ie.getMessage());
            }*/
            status = true;
        } catch (Exception e) {
            this.exception = e;
            status = false;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
    }
}
