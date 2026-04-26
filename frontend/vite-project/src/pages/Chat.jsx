import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import { connectSocket } from "../services/websocket";

export default function Chat() {
    const { username } = useSelector((state) => state.auth);

    const [socket, setSocket] = useState(null);
    const [receiver, setReceiver] = useState("");
    const [message, setMessage] = useState("");
    const [messages, setMessages] = useState([]);

    // Connect WebSocket
    useEffect(() => {
        if (!username) return;

        const ws = connectSocket(username, (msg) => {
            setMessages((prev) => [...prev, msg]);
        });

        setSocket(ws);

        return () => ws.close();
    }, [username]);

    // Send message
    const sendMessage = () => {
        if (!socket || !receiver || !message) return;

        const msg = {
            sender: username,
            receiver: receiver,
            content: message,
        };

        socket.send(JSON.stringify(msg));
        setMessage("");
    };

    return (
        <div className="container">
            <h2>Chat</h2>

            <input
                type="text"
                placeholder="Receiver username"
                value={receiver}
                onChange={(e) => setReceiver(e.target.value)}
            />

            <div style={{ border: "1px solid #ccc", height: "300px", overflowY: "scroll", margin: "10px 0", padding: "10px" }}>
                {messages.map((m, index) => (
                    <div key={index}>
                        <b>{m.sender}:</b> {m.content}
                    </div>
                ))}
            </div>

            <input
                type="text"
                placeholder="Type message"
                value={message}
                onChange={(e) => setMessage(e.target.value)}
            />

            <button onClick={sendMessage}>Send</button>
        </div>
    );
}