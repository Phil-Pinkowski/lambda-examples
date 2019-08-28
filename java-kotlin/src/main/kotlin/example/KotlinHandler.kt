package example

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.beust.klaxon.Klaxon
import khttp.get

data class Request(var body: String? = null)
data class RequestBody(var textUrl: String)
data class ResponseBody(val wordCount: Int, val longestWord: String)
data class Response(val body: String?, val statusCode: Int)

fun Request.parseBody() = Klaxon().parse<RequestBody>(this.body!!)!!
fun ResponseBody.toJson() = Klaxon().toJsonString(this)

class KotlinHandler : RequestHandler<Request, Response> {
    override fun handleRequest(request: Request?, context: Context?): Response {
        if (request == null) {
            return Response(null, 400)
        }

        val requestBody = request.parseBody()

        val text = get(requestBody.textUrl).text

        val wordCount = text.split(" ").size
        val longestWord = text.split(" ")
            .reduce{longest, next -> if (next.length > longest.length) next else longest }

        return Response(ResponseBody(wordCount, longestWord).toJson(), 200)
    }
}