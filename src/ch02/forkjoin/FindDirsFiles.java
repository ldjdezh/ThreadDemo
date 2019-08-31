package ch02.forkjoin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class FindDirsFiles extends RecursiveAction {

    private File path;

    public FindDirsFiles(File path) {
        this.path = path;
    }

    @Override
    protected void compute() {
        List<FindDirsFiles> subTasks = new ArrayList<>();
        File[] files = path.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    subTasks.add(new FindDirsFiles(file));
                } else {
                    if (file.getAbsolutePath().endsWith("txt")) {
                        System.out.println("文件: " + file.getAbsolutePath());
                    }
                }
            }

            if (!subTasks.isEmpty()) {
                for (FindDirsFiles subTask : invokeAll(subTasks)) {
                    subTask.join();
                }
            }
        }
    }

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        FindDirsFiles task = new FindDirsFiles(new File("F:/"));
        pool.execute(task);

        task.join();
    }
}
