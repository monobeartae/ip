package app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import app.exceptions.MonoBotException;
import app.tasks.Task;


/**
 * Class to handle saving and loading tasks data for MonoBot
 */
public class SaveHandler {
    private final String SAVE_FILE_NAME = "./src/main/java/app/data/monobot_tasks.txt";

    /**
     * Saves tasks in specified format in specified file location
     * @param tasks Tasks to save
     */
    public void saveTasks(ArrayList<Task> tasks) throws MonoBotException {
        File saveFile = new File(SAVE_FILE_NAME);
        try {
            if (!saveFile.exists()) {
                saveFile.createNewFile();
            }
            FileWriter fw = new FileWriter(saveFile);
            String saveString = "";
            for (Task t : tasks) {
                saveString += t.encodeTask();
            }
            fw.write(saveString);
            fw.close();
        } catch (IOException e) {
            throw new MonoBotException(e.getMessage());
        }
    }

    /**
     * Loads tasks from specified save file location
     * @return Decoded Tasks
     */
    public ArrayList<Task> loadTasks() {
        File saveFile = new File(SAVE_FILE_NAME);
        Scanner sc = null;
        try {
            sc = new Scanner(saveFile);
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        }
        ArrayList<Task> tasks = new ArrayList<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            tasks.add(Task.decodeTask(line));
        }
        sc.close();
        return tasks;
    }


}
