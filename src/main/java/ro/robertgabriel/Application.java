package ro.robertgabriel;

public class Application {
    public static void main(String[] args) {
        String solution = "yruihfbjc4312rde";

        String find = Generate.findSolution(10000, solution);
        find = find == null ? "NONE" : find;
        System.out.println("Find: " + find);
    }
}
