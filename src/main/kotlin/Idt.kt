import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import java.time.Duration


object Idt {

    fun processIdt(gene: String) {
        WebDriverManager.chromiumdriver()
            .setup()
//        WebDriverManager.chromedriver()
//            .setup()
//        WebDriverManager.chromedriver().driverVersion("107").setup()
//        WebDriverManager.chromedriver().browserVersion("107").setup()
//        val driver = WebDriverManager.chromedriver()
//            .browserInDocker()
//            .create()
        val driver = ChromeDriver()
        try {

            driver.manage()
                .timeouts()
                .implicitlyWait(Duration.ofMillis(500))
            driver.get("https://eu.idtdna.com/site/order/designtool/index/CRISPR_SEQUENCE")
            WebDriverWait(
                driver,
                Duration.ofSeconds(10)
            ).until(ExpectedConditions.presenceOfElementLocated(By.className("close")))

            driver.findElement(By.className("close"))
                .click()
            val element = driver.findElement(By.xpath("//a[text()='Search for predesigned gRNA']"))
            element.click()

//        WebClient(BrowserVersion.BEST_SUPPORTED).use { it: WebClient ->
//            it.options.isUseInsecureSSL = true
//            it.options.isRedirectEnabled = true
//            it.options.isThrowExceptionOnScriptError = false;
//            it.waitForBackgroundJavaScript(10_000);
//            var formPage: HtmlPage = it.getPage("https://eu.idtdna.com/site/order/designtool/index/CRISPR_SEQUENCE")
//
//            val preDesignTab = formPage.getByXPath<HtmlAnchor>("//a[text()='Search for predesigned gRNA']")
//                .first()
//            formPage = preDesignTab.click<HtmlPage>()
//            it.waitForBackgroundJavaScript(10_000);
//
//            val geneInput: HtmlTextArea = formPage.getHtmlElementById("input-paste0")
//            geneInput.click<Page>()
//            geneInput.type(gene)
//            geneInput.blur()
//            it.waitForBackgroundJavaScript(10_000);
//
//            val pageAfterClick = formPage.getHtmlElementById<HtmlButton>("start-search-button")
//                .click<HtmlPage>()
//            it.waitForBackgroundJavaScript(10_000);
//
//            val selector = pageAfterClick.querySelector<HtmlTable>(".table.table-condensed")
//            val resultPage: HtmlPage = it.currentWindow.enclosedPage as HtmlPage;
            println("asd")

//            val contentAsString = it.getPage<TextPage>(resultPage.baseURL.toString() + "results.tsv").webResponse.contentAsString
//            File("chopchopResult_${gene}_${genome.name}_.txt").printWriter()
//                .use { writer ->
//                    contentAsString.lineSequence()
//                        .drop(1)
//                        .take(10)
//                        .forEach { line ->
//                            val sequence = line.split(Regex("\\s"))[1].dropLast(3)
//                            writer.println(sequence)
//                        }
//                }
//        }

//        }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            driver.close()
        }
    }

}