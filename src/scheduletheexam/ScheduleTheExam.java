package scheduletheexam;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ScheduleTheExam {
    private Map<String, Set<String>> students = new HashMap<String, Set<String>>();
    private Set<String> setExams = new HashSet<String>();
    private int[][] adjMatrix;
    private Map<Integer, String> indexExams = new HashMap<Integer, String>();

    public ScheduleTheExam(String fileName) {
        loadData(fileName);
    }

    public void loadData(String fileName) {
        try {
            BufferedReader bf = new BufferedReader(new FileReader(fileName));
            String line = bf.readLine();
            while (line != null) {
                String[] data = line.split(" ");
                String student = data[0];
                String exam = data[1];
                setExams.add(exam);
                if (students.containsKey(student)) {
                    students.get(student).add(exam);
                } else {
                    Set<String> exams = new HashSet<String>();
                    exams.add(exam);
                    students.put(student, exams);
                }
                line = bf.readLine();
            }
            bf.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printData() {
        for (String student : students.keySet()) {
            System.out.print(student + ": ");
            for (String course : students.get(student)) {
                System.out.print(course + " ");
            }
            System.out.println();
        }
    }

    public void mapToAdjMatrix() {
        int numExams = setExams.size();
        this.adjMatrix = new int[numExams][numExams];
        Map<String, Integer> mapExams = new HashMap<String, Integer>();
        int index = 0;
        // đỉnh của đồ thị
        for (String exam : setExams) {
            mapExams.put(exam, index);
            indexExams.put(index, exam);
            index++;
        }

        for (String student : students.keySet()) {
            Set<String> exams = students.get(student);
            for (String exam1 : exams) {
                for (String exam2 : exams) {
                    if (!exam1.equals(exam2)) {
                        int i = mapExams.get(exam1);
                        int j = mapExams.get(exam2);
                        this.adjMatrix[i][j] = 1;
                    }
                }
            }
        }

    }

    public void printAdjMatrix() {
        for (int i = 0; i < this.adjMatrix.length; i++) {
            for (int j = 0; j < this.adjMatrix.length; j++) {
                System.out.print(this.adjMatrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void scheduletheexam() {
        // set màu ở đỉnh
        Map<String, Integer> color = new HashMap<String, Integer>();
        // Initialize color to 0
        for (String exam : setExams) {
            color.put(exam, 0);
        }
        for (int i = 0; i < adjMatrix.length; i++) {
            Set<Integer> colorIn = new HashSet<Integer>();
            for (int j = 0; j < adjMatrix.length; j++) {
                if (adjMatrix[j][i] == 1) {
                    colorIn.add(color.get(indexExams.get(j)));
                }
            }
            System.out.println(colorIn);
            for (int j = 0; j < adjMatrix.length; j++) {
                if (!colorIn.contains(j)) {
                    color.put(indexExams.get(i), j);
                    break;
                }
            }

        }
        System.out.println(color);

    }

    public static void main(String[] args) {
        ScheduleTheExam scheduleTheExam = new ScheduleTheExam("src\\scheduletheexam\\scheduletheexam.txt");
        scheduleTheExam.printData();
        scheduleTheExam.mapToAdjMatrix();
        // scheduleTheExam.printAdjMatrix();
        scheduleTheExam.scheduletheexam();

    }
}
