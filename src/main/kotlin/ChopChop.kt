import com.gargoylesoftware.htmlunit.BrowserVersion
import com.gargoylesoftware.htmlunit.Page
import com.gargoylesoftware.htmlunit.TextPage
import com.gargoylesoftware.htmlunit.WebClient
import com.gargoylesoftware.htmlunit.html.HtmlButton
import com.gargoylesoftware.htmlunit.html.HtmlForm
import com.gargoylesoftware.htmlunit.html.HtmlInput
import com.gargoylesoftware.htmlunit.html.HtmlPage
import kotlinx.serialization.Serializable
import java.io.File


@Serializable
class ChopChopFormData(
    val geneInput: String,
    val fastaInput: String = "",
    val forSelect: String = "knock-out",
    val isIsoform: Boolean = false,
//    val opts: Array<String> = OPTS_STRING.split(";")
//        .toTypedArray()
)

enum class Genome(val value: String) {
    Musculus_38("mm10"),
    Musculus_39("mm39"),
    HomoSapiens("hg38"),
}

object ChopChop {

    fun processChopChop(gene: String,
        genome: Genome
    ) {
        WebClient(BrowserVersion.BEST_SUPPORTED).use { it: WebClient ->
            it.options.isUseInsecureSSL = true
            it.options.isRedirectEnabled = true
            it.options.isThrowExceptionOnScriptError = false
            it.waitForBackgroundJavaScript(3_000)
            val formPage: HtmlPage = it.getPage("https://chopchop.cbu.uib.no/")
            val form: HtmlForm = formPage.getHtmlElementById("runChopChop")

            val geneInput: HtmlInput = form.getInputByName("geneInput")
            geneInput.type(gene)

            val genomeSelect = form.getSelectByName("genomeSelect")
            val genomeOption = genomeSelect.getOptionByValue(genome.value)
            genomeOption.isSelected = true

            formPage.getHtmlElementById<HtmlButton>("searchRequest")
                .click<Page>()
            it.waitForBackgroundJavaScript(10000)

            val resultPage: HtmlPage = it.currentWindow.enclosedPage as HtmlPage

            var iter = 1
            while (resultPage.getElementById("time") != null) {
                println("Page is still processing, waiting for 10 seconds.")
                if (iter > 10) throw RuntimeException("Waited too long!")
                Thread.sleep(10_000)
                iter++
            }

            val contentAsString = it.getPage<TextPage>(resultPage.baseURL.toString() + "results.tsv").webResponse.contentAsString
            File("chopchopResult_${gene}_${genome.name}_.txt").printWriter()
                .use { writer ->
                    contentAsString.lineSequence()
                        .drop(1)
                        .take(10)
                        .forEach { line ->
                            val sequence = line.split(Regex("\\s"))[1].dropLast(3)
                            writer.println(sequence)
                        }
                }
        }

    }

}