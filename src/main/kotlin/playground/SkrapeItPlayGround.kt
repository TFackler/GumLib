package playground

import it.skrape.core.htmlDocument
import it.skrape.matchers.toBe
import it.skrape.selects.html5.link

fun main() {
    TestFileUtil.getWebpageStringForTesting("https://www.google.com")
    val googleHtml = TestFileUtil.loadSavedWebpageHtmlAsString("google.html")

    val someHtml = """
    <html>
        <head>
            <link rel="shortcut icon" href="https://some.url/icon">
            <script src="https://some.url/some-script.js"></script>
            <meta name="foo" content="bar">
        </head>
        <body>
            i'm the body
            <h1>i'm the headline</h1>
            <main>
                <p class="foo bar">i'm a paragraph</p>
                <p>i'm a second paragraph</p>
                <p>i'm a paragraph <wbr> with word break</p>
                <p>i'm the last paragraph</p>
            </main>
        </body>
    </html>
    """

    htmlDocument(someHtml) {
        link {
            withAttribute = "rel" to "shortcut icon"
            findFirst {
                attribute("href") toBe "https://some.url/icon"
            }
        }
    }
}
