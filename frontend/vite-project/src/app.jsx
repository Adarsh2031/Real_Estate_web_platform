import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { logout } from "./store";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Home from "./pages/Home";
import AddProperty from "./pages/AddProperty";
import Chat from "./pages/Chat";

import ProtectedRoute from "./ProtectedRoute";
import "./App.css";

function App() {
    const { token } = useSelector((state) => state.auth);
    const dispatch = useDispatch();

    return (
        <BrowserRouter>
            <nav className="navbar">
                <h2>Real Estate</h2>
                <div>
                    <Link to="/">Home</Link>

                    {/* Show only when logged in */}
                    {token && <Link to="/add">Add Property</Link>}
                    {token && <Link to="/chat">Chat</Link>}

                    {/* Show only when NOT logged in */}
                    {!token && <Link to="/login">Login</Link>}
                    {!token && <Link to="/register">Register</Link>}

                    {/* Logout */}
                    {token && (
                        <button onClick={() => dispatch(logout())}>
                            Logout
                        </button>
                    )}
                </div>
            </nav>

            <Routes>
                <Route path="/" element={<Home />} />

                <Route
                    path="/add"
                    element={
                        <ProtectedRoute>
                            <AddProperty />
                        </ProtectedRoute>
                    }
                />

                <Route
                    path="/chat"
                    element={
                        <ProtectedRoute>
                            <Chat />
                        </ProtectedRoute>
                    }
                />

                <Route path="/login" element={<Login />} />
                <Route path="/register" element={<Register />} />
            </Routes>
        </BrowserRouter>
    );
}

export default App;