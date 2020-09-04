package comppa.logic;

import comppa.domain.Constants;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

public class MaathTest {

    @Rule
    public ExpectedException thrownException = ExpectedException.none();

    @Test
    public void absReturnsZero() {
        assertEquals(0, Maath.abs(-0));
    }

    @Test
    public void absReturnsPositiveOne() {
        assertEquals(1, Maath.abs(1));
    }

    @Test public void absReturnsPositiveOneWhenNegativeArgument() {
        assertEquals(1, Maath.abs(-1));
    }

    @Test
    public void absReturnsPositiveTwo() {
        assertEquals(2, Maath.abs(2));
    }

    @Test
    public void absReturnsPositiveTwoWhenNegativeArgument() {
        assertEquals(2, Maath.abs(-2));
    }

    @Test
    public void absReturnsPositiveIntegerMaxVal() {
        assertEquals(Constants.INT_MAX_VAL, Maath.abs(Constants.INT_MAX_VAL));
    }

    @Test
    public void absReturnsPositiveIntegerMaxValWhenNegativeArgument() {
        assertEquals(Constants.INT_MAX_VAL, Maath.abs(-Constants.INT_MAX_VAL));
    }

    @Test
    public void absThrowsExceptionWhenArgumentIsIntMinVal() {
        thrownException.expect(IllegalArgumentException.class);
        thrownException.expectMessage("Cannot return positive value of Integer MIN_VALUE: -2147483648.");
        Maath.abs(Constants.INT_MIN_VAL);
    }

    @Test
    public void maxReturnsZeroWhenArgsAreZero() {
        assertEquals(0, Maath.max(0, -0));
    }

    @Test
    public void maxReturnsBiggerArgOne() {
        for (int i = -11; i < 12; i++) {
            assertEquals(i, Maath.max(i-1, i));
            assertEquals(i, Maath.max(i, i-1));
        }
    }

    @Test
    public void maxReturnsIntMaxVal() {
        assertEquals(Constants.INT_MAX_VAL, Maath.max(Integer.MIN_VALUE, Integer.MAX_VALUE));
    }

    @Test
    public void maxReturnsZeroFromIntMinVal() {
        assertEquals(0, Maath.max(0, Constants.INT_MIN_VAL));
    }

    @Test
    public void maxReturnsThePositiveOfTwo() {
        for (int i = 0; i < 130; i++) {
            assertEquals(i, Maath.max(i, -i));
        }
    }
}
