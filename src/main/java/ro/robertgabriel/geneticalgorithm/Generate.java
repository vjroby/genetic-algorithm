package ro.robertgabriel.geneticalgorithm;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Generate {

    private static String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private static final double BEST_FIT_PERCENT = 0.3d;
    private static final double NON_FIT_PERCENT = 0.2d;

    private static final Random random = new Random();

    public static Character randomLetter() {
        return chars.charAt(random.nextInt(chars.length() - 1));
    }

    public static String chromosome(int size) {
        var sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(randomLetter());
        }
        return sb.toString();
    }

    public static double compareChromosome(String chromo, String solution) {
        if (chromo.length() != solution.length()) return 0;
        int l = chromo.length() - 1;
        int[] result = new int[chromo.length()];

        while (l >= 0) {
            if (Character.compare(chromo.charAt(l), solution.charAt(l)) == 0) {
                result[l] = 1;
            }
            l--;
        }
        return (double)Arrays.stream(result).sum() / (chromo.length());
    }

    public static List<Chromosome> scoredSorted(List<String> chromosomes, String solution) {
        return chromosomes.stream()
                .map(c -> new Chromosome(c, compareChromosome(c, solution)))
                .sorted()
                .collect(Collectors.toList());
    }

    public static List<Chromosome> selectChromosomes(List<String> chromosomes, String solution) {
        int limitBest = (int) (chromosomes.size() * BEST_FIT_PERCENT);
        int rest = (int) (chromosomes.size() * NON_FIT_PERCENT);
        var all = scoredSorted(chromosomes, solution);
        var firstFit = all.subList(0, limitBest);

        List<Chromosome> notFit = all.subList(limitBest, all.size() - 1);
        Collections.shuffle(notFit);
        firstFit.addAll(notFit.subList(0, rest));

        return firstFit;
    }

    public static String createChild(String parent1, String parent2) {
        if (parent1.length() != parent2.length()) throw new RuntimeException("Parents size don't match");
        int endIndexP1 = parent1.length() / 2;
        if (parent1.length() % 2 != 0) {
            return parent1.substring(0, endIndexP1+1) +
                    parent2.substring((parent2.length() / 2) + 1, parent2.length());
        }
        return parent1.substring(0, endIndexP1) +
                parent2.substring(parent2.length() / 2, parent2.length());
    }

    public static String randomMutate(String chrom) {
        int r = random.nextInt(chrom.length() - 1);
        Character newC = randomLetter();
        char[] chars = chrom.toCharArray();
        chars[r] = newC;
        return String.valueOf(chars);
    }

    public static List<String> generateNewPopulation(List<String> oldPopulation, String solution) {
        List<String> selectedChromosome = selectChromosomes(oldPopulation, solution)
                .stream()
                .peek(c -> System.out.println(String.format("Score: %s",String.valueOf(c.getScore()))))
                .map(Chromosome::getValue)
                .collect(Collectors.toList());

        List<String> children = new ArrayList<>();

        while (children.size() < selectedChromosome.size()) {
            String parent1 = selectedChromosome.get(random.nextInt(selectedChromosome.size() - 1));
            String parent2 = selectedChromosome.get(random.nextInt(selectedChromosome.size() - 1));
            String child = createChild(parent1, parent2);
            child = randomMutate(child);
            children.add(child);
        }
        selectedChromosome.addAll(children);
        return selectedChromosome;
    }

    public static List<String> createPopulation(int populationSize, int chromSize) {
        return Stream.iterate(0, i -> i + 1)
                .limit(populationSize)
                .map(c -> Generate.chromosome(chromSize))
                .collect(Collectors.toList());
    }

    public static String findSolution(int size, String solution) {
        List<String> population = createPopulation(size, solution.length());
        String answer = null;
        int i = 0;

        while (answer == null) {
            System.out.println("Iteration: "+(++i));
            population = generateNewPopulation(population, solution);

            for (String entity : population) {
                if (entity.equals(solution)) {
                    answer = entity;
                    break;
                }
            }
        }
        return answer;
    }
}
