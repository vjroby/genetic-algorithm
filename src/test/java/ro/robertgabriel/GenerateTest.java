package ro.robertgabriel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

@RunWith(BlockJUnit4ClassRunner.class)
public class GenerateTest {

    @Test
    public void testRandomMutate() throws Exception {
        String s = "testString";

        String m = Generate.randomMutate(s);

        assertNotSame("Strings are different", s, m);
    }

    @Test
    public void shouldCreateChild4Chars() throws Exception {
        String parent1 = "ABCD7890";
        String parent2 = "8765TYXZ";

        String child = Generate.createChild(parent1, parent2);

        assertEquals("ABCDTYXZ", child);
        assertEquals(parent2.length(), child.length());
        assertEquals(parent1.length(), child.length());
    }

    @Test
    public void shouldCreateChild5Chars() throws Exception {
        String parent1 = "ABCD78901";
        String parent2 = "8765TYXZA";

        String child = Generate.createChild(parent1, parent2);

        assertEquals("ABCD7YXZA", child);
        assertEquals(parent2.length(), child.length());
        assertEquals(parent1.length(), child.length());
    }

    @Test
    public void shouldCompareEqualChromo() throws Exception {
        String chromo = "4ufew1313kca";
        String solution = "4ufew1313kca";
        Double result = Generate.compareChromosome(chromo, solution);

        assertEquals(1d, result, 0d);
    }

    @Test
    public void shouldCompareChromo() throws Exception {
        String chromo = "238ewio13ret";
        String solution = "4ufew1813kca";
        Double result = Generate.compareChromosome(chromo, solution);

        assertEquals(0.3333333333333333d, result, 0d);
    }

    @Test
    public void shouldGenerateNewPopulation() throws Exception {
        String solution = "4ufew1813kca";
        List<String> oldPopulation = Arrays.asList("4ufew181pkzx", "4ufev1354321", "41fev1354321", "01fev1354321",
                "01fev1354321", "01fev135d321", "01fev1354b21", "01fev1354m21", "01fev13543q1", "01fev13543a1"
        );

        List<String> generated = Generate.generateNewPopulation(oldPopulation, solution);

        boolean a = generated.contains("4ufew181pkzx");
        assertTrue(a);
        assertTrue(!generated.contains("01fev13543a1"));
    }

    @Test
    public void shouldFindSolutionUnevenChars() throws Exception {
        String solution = "rrueiz3";

        String result = Generate.findSolution(1000, solution);

        assertEquals(result, solution);
    }
    @Test
    public void shouldFindSolutionEvenChars() throws Exception {
        String solution = "rueiz3";

        String result = Generate.findSolution(1000, solution);

        assertEquals(result, solution);
    }
}