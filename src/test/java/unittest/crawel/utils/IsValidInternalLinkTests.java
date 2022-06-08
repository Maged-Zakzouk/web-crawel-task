package unittest.crawel.utils;


import com.boost.webcrawel.utils.CrawelingUtils;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class IsValidInternalLinkTests {

    @Test
    public void isValidInternalLink_SendExternalLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://facebook.com/monzo", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendCSSAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.css", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendJSAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.js", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendGIFAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.gif", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendJPGAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.jpg", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendJPEGAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.jpeg", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendPNGAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.png", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendMP3AssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.mp3", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendMP4AssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.mp4", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendZIPAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.zip", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendGZAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.gz", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendPDFAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.pdf", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendXLSAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.xls", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendXLSXAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.xlsx", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendDOCAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.doc", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendDOCXAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.docx", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendCSSCapitalLetterAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.CSS", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendJSCapitalLetterAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.JS", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendGIFCapitalLetterAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.GIF", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendJPGCapitalLetterAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.JPG", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendJPEGCapitalLetterAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.JPEG", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendPNGCapitalLetterAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.PNG", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendMP3CapitalLetterAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.MP3", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendMP4CapitalLetterAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.MP4", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendZIPCapitalLetterAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.ZIP", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendGZCapitalLetterAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.GZ", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendPDFCapitalLetterAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.PDF", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendXLSCapitalLetterAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.XLS", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendXLSXCapitalLetterAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.XLSX", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendDOCCapitalLetterAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.DOC", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendDOCXCapitalLetterAssetLink_ReturnFalse() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/assets/file.DOCX", "https://monzo.com");
        assertFalse(result);
    }

    @Test
    public void isValidInternalLink_SendValidInternalLink_ReturnTrue() {
        Boolean result = CrawelingUtils.isValidInternalLink("https://monzo.com/info", "https://monzo.com");
        assertTrue(result);
    }


}
