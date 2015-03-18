package com.beginningandroid.Data;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by LingChen on 15/3/12.
 */
public class ToFile {
    public static void save(Context context, String txt) {
        String data = "Data to save";
        FileOutputStream outputStream = null;
        BufferedWriter bufferedWriter = null;
        try {
            outputStream = context.openFileOutput(data, Context.MODE_PRIVATE);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            bufferedWriter.write(txt);
        } catch (Exception ex) {
        } finally {
            try {
                if (bufferedWriter != null)
                    bufferedWriter.close();
            } catch (Exception ex) {
            }
        }
    }

    public static String load(Context context) {
        String data = "Data to save";

        StringBuilder txt = new StringBuilder();

        FileInputStream inputStream = null;
        BufferedReader bufferedReader = null;

        try {
            inputStream = context.openFileInput(data);
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine())!= null){
                txt.append(line);
            }
        } catch (Exception ex) {
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();
            } catch (Exception ex) {
            }
        }

        return txt.toString();
    }
}
