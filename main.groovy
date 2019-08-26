import com.sun.net.httpserver.HttpExchange
import com.sun.net.httpserver.HttpServer

class EnhancedHttpExchange {
    static void reply(HttpExchange self, String text) {
        self.responseHeaders.add("Content-type", "text/plain")
        self.sendResponseHeaders(200, 0)
        self.responseBody.withWriter { out ->
            out << text
        }
    }
}

int PORT = 8080
HttpServer.create(new InetSocketAddress(PORT), /*max backlog*/ 0).with {
    println "Server is listening on ${PORT}, hit Ctrl+C to exit."

    createContext("/") { http -> use(EnhancedHttpExchange) {
        http.reply("Que pasha")
    }}

    createContext("/holi") { http -> use(EnhancedHttpExchange) {
        http.reply("Como que holi")
    }}

    start()
}
