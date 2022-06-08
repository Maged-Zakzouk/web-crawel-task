package unittest.crawel.utils;


import com.boost.webcrawel.core.commons.exception.LogicalException;
import com.boost.webcrawel.utils.CrawelingUtils;
import org.junit.Test;

import static com.boost.webcrawel.core.commons.exception.ServerError.INVALID_URL;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ValidateURLTests {

    @Test
    public void validateUrl_InvalidUrl_ThrowsInvalidUrlException() {
        try {
            CrawelingUtils.validateURL("monzo");
            fail("It should throw logical exception");
        } catch (LogicalException logicalException) {
            assertEquals(INVALID_URL.getMessage(), logicalException.getMessage());
        }
    }

    @Test
    public void validateUrl_InvalidUrl2_ThrowsInvalidUrlException() {
        try {
            CrawelingUtils.validateURL("http://monzo");
            fail("It should throw logical exception");
        } catch (LogicalException logicalException) {
            assertEquals(INVALID_URL.getMessage(), logicalException.getMessage());
        }
    }

    @Test
    public void validateUrl_InvalidUrl3_ThrowsInvalidUrlException() {
        try {
            CrawelingUtils.validateURL("monzo.com");
            fail("It should throw logical exception");
        } catch (LogicalException logicalException) {
            assertEquals(INVALID_URL.getMessage(), logicalException.getMessage());
        }
    }

    @Test
    public void validateUrl_ValidUrl_ContinueWithoutThrowAnyExceptions() {
        try {
            CrawelingUtils.validateURL("http://monzo.com");
        } catch (LogicalException logicalException) {
            fail("It should not throw logical exception");
        }
    }

    @Test
    public void validateUrl_ValidUrl2_ContinueWithoutThrowAnyExceptions() {
        try {
            CrawelingUtils.validateURL("https://monzo.com");
        } catch (LogicalException logicalException) {
            fail("It should not throw logical exception");
        }
    }

    @Test
    public void validateUrl_ValidUrl3_ContinueWithoutThrowAnyExceptions() {
        try {
            CrawelingUtils.validateURL("https://www.monzo.com");
        } catch (LogicalException logicalException) {
            fail("It should not throw logical exception");
        }
    }


}
