package com.example.compulinkapp.classes;

import android.content.Context;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileHelper {
    /**
     * The name of the file to be created, written to and read from
     * for the list on the home screen
     */
    public static final String FILENAME = "listinfo.dat";

    /**
     * Write data to the file when the user adds items to the list
     *
     * @param items Arraylist of items to be added
     * @param ctx   Context required
     */
    public static void writeData(ArrayList<String> items, Context ctx)
    {
        try
        {
            FileOutputStream fos = ctx.openFileOutput(FILENAME, ctx.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(items);
            oos.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Reads data stored in the file to populate the list when the app starts again
     * @param ctx Context required
     * @return Arraylist of items in the file
     */
    public static ArrayList<String> readData(Context ctx)
    {
        ArrayList<String> itemsList = null;

        try {
            FileInputStream fis = ctx.openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            itemsList = (ArrayList<String>) ois.readObject();
        } catch (FileNotFoundException e)
        {
            itemsList = new ArrayList<String>();

            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return itemsList;
    }

    /**
     * Checks if the file is empty or if it has items
     * Used to decide if the notification to the technician is necessary
     * @param ctx Context required
     * @return Boolean based on condition outcome
     */
    public static boolean hasItemsCheck(Context ctx)
    {
        FileInputStream fis = null;
        try
        {
            fis = ctx.openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);

            if(ois.readObject() != null)
            {
                return true;
            }
            else return false;
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
