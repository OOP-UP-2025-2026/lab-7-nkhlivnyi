package ua.opnu;

import java.util.*;
import java.util.function.*;

public class Main {

    public static Predicate<Integer> isPrime = n -> {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++)
            if (n % i == 0) return false;
        return true;
    };

    static class Student {
        private String name;
        private String group;
        private int[] marks;

        public Student(String name, String group, int[] marks) {
            this.name = name;
            this.group = group;
            this.marks = marks;
        }

        public String getName() { return name; }
        public String getGroup() { return group; }
        public int[] getMarks() { return marks; }
    }

    public static List<Student> filter(Student[] arr, Predicate<Student> p) {
        List<Student> res = new ArrayList<>();
        for (Student s : arr)
            if (p.test(s)) res.add(s);
        return res;
    }

    public static List<Student> filterTwo(Student[] arr, Predicate<Student> p1, Predicate<Student> p2) {
        List<Student> res = new ArrayList<>();
        for (Student s : arr)
            if (p1.test(s) && p2.test(s)) res.add(s);
        return res;
    }

    @FunctionalInterface
    interface StudentConsumer {
        void accept(Student s);
    }

    public static <T> void applyIf(T[] arr, Predicate<T> p, Consumer<T> c) {
        for (T el : arr)
            if (p.test(el)) c.accept(el);
    }

    public static Function<Integer, Integer> pow2 = n -> (int) Math.pow(2, n);

    public static String[] stringify(int[] arr, Function<Integer, String> f) {
        String[] res = new String[arr.length];
        for (int i = 0; i < arr.length; i++)
            res[i] = f.apply(arr[i]);
        return res;
    }

    public static Function<Integer, String> numToWord = n -> {
        String[] w = {"нуль","один","два","три","чотири","п'ять","шість","сім","вісім","дев'ять"};
        return w[n];
    };

    public static void main(String[] args) {

        System.out.println(isPrime.test(7));
        System.out.println(isPrime.test(21));

        Student[] students = {
                new Student("Антов Іванов", "УП-21", new int[]{90, 80, 75}),
                new Student("Олександр Шевченко", "УП-21", new int[]{40, 70, 55}),
                new Student("Марія Гончар", "УП-22", new int[]{100, 95, 90})
        };

        Predicate<Student> hasDebts = s -> {
            for (int m : s.getMarks())
                if (m < 60) return true;
            return false;
        };

        filter(students, hasDebts).forEach(s -> System.out.println(s.getName()));

        Predicate<Student> goodAvg = s -> {
            int sum = 0;
            for (int m : s.getMarks()) sum += m;
            return sum / s.getMarks().length > 80;
        };

        Predicate<Student> noDebts = s -> {
            for (int m : s.getMarks())
                if (m < 60) return false;
            return true;
        };

        filterTwo(students, goodAvg, noDebts)
                .forEach(s -> System.out.println(s.getName()));

        StudentConsumer printName = s -> {
            String[] p = s.getName().split(" ");
            System.out.println(p[0] + " " + p[1]);
        };

        for (Student s : students) printName.accept(s);

        Integer[] nums = {1,2,3,4,5,6};
        Predicate<Integer> isEven = n -> n % 2 == 0;
        Consumer<Integer> print = n -> System.out.println("num = " + n);

        applyIf(nums, isEven, print);

        for (int i = 0; i < 10; i++)
            System.out.println(pow2.apply(i));

        int[] a = {0,1,2,3,4,5,6,7,8,9};
        String[] result = stringify(a, numToWord);

        for (String s : result)
            System.out.println(s);
    }
}
