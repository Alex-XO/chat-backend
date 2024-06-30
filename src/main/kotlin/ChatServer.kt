package org.example

import org.java_websocket.WebSocket
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.server.WebSocketServer
import java.net.InetSocketAddress

class ChatServer(port: Int) : WebSocketServer(InetSocketAddress(port)) {
    private val messages = mutableListOf<String>()

    override fun onOpen(conn: WebSocket, handshake: ClientHandshake) {
        conn.send(messages.joinToString("\n"))
    }

    override fun onClose(conn: WebSocket, code: Int, reason: String, remote: Boolean) {
        println("User close connection ${conn.remoteSocketAddress}")
    }

    override fun onMessage(conn: WebSocket, message: String) {
        messages.add(message)
        broadcast(message)
    }

    override fun onError(conn: WebSocket?, ex: Exception?) {
        throw Exception(ex)
    }

    override fun onStart() {
        println("Server started on port $port")
    }
}