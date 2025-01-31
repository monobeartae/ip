import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import tasks.Task;

public class SaveHandler {
    private final String SAVE_FILE_NAME = "./data/monobot_tasks.txt";


    public void SaveTasks(ArrayList<Task> tasks) {
        File saveFile = new File(SAVE_FILE_NAME);
        try {
            if (!saveFile.exists()) 
                saveFile.createNewFile();
            FileWriter fw = new FileWriter(saveFile);
            String saveString = "";
            for (Task t : tasks) {
                saveString += t.EncodeTask();
            }
            fw.write(saveString);
            fw.close();
        } catch (IOException e) {
            System.out.print(e.getMessage());
        }
    }
    
    public ArrayList<Task> LoadTasks() {
        File saveFile = new File(SAVE_FILE_NAME);
        Scanner sc = null;
        try {
            sc = new Scanner(saveFile);
        } catch (FileNotFoundException e) {
            return new ArrayList<Task>();
        }
        ArrayList<Task> tasks = new ArrayList<>();
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            tasks.add(Task.DecodeTask(line));
        }
        sc.close();
        return tasks;
    }


}
