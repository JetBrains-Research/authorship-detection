package com.lz.game.game3.common;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * User: Teaey
 * Date: 13-8-30
 */
public class Test
{
    public static void main(String[] args) throws IOException
    {
        List<Integer> listCreate = new ArrayList<>();
        List<Integer> listLogin = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\37-int-teaey\\Desktop\\test_15.txt")));
        String line = null;
        while ((line = reader.readLine()) != null)
        {
            listCreate.add(Integer.valueOf(line));
        }
        reader.close();
        reader = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\37-int-teaey\\Desktop\\test_16.txt")));
        line = null;
        while ((line = reader.readLine()) != null)
        {
            listLogin.add(Integer.valueOf(line));
        }
        reader.close();
        int size = 0;
        for (Integer each : listCreate)
        {
            if (!listLogin.contains(each))
            {
                size ++;
            }
        }
        System.out.println(size);
    }
}
