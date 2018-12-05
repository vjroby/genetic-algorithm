package ro.robertgabriel.geneticalgorithm;

public class Application {
    public static void main(String[] args) {
        var solution = "yruihfbjc4312rde";

        var find = Generate.findSolution(10000, solution);
        find = find == null ? "NONE" : find;
        System.out.println("Find: " + find);
    }
}
