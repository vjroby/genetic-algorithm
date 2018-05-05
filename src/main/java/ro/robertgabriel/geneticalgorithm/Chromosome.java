package ro.robertgabriel.geneticalgorithm;

public class Chromosome implements Comparable<Chromosome> {
    private final String value;
    private final Double score;

    public Chromosome(String value, Double score) {
        this.value = value;
        this.score = score;
    }

    @Override
    public int compareTo(Chromosome o) {
        return o.score.compareTo(this.score);
    }

    public String getValue() {
        return value;
    }
    public Double getScore() {
        return score;
    }
}
