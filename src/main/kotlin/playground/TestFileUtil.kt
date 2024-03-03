package playground

import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape

class TestFileUtil {

    companion object {
        fun getWebpageStringForTesting(url: String) {
            skrape(HttpFetcher) {
                request {
                    this.url = url
                }
                response {
                    val contents = responseBody
                    println(responseBody)
                }
            }
        }

        /**
         * @param fileName The name of the file in the `/resources/saved_pages` directory
         */
        fun loadSavedWebpageHtmlAsString(fileName: String): String {
            val path = "/saved_pages/$fileName"
            return TestFileUtil::class.java.getResource(path)?.readText() ?: error("File not found at path: $path")
        }
    }

}