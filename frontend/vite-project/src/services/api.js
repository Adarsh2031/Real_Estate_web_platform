import axios from "axios";
import store from "../store";

// Create axios instance
const api = axios.create({
  baseURL: "http://localhost:8080",
});

// Add token automatically
api.interceptors.request.use((config) => {
  const state = store.getState();
  const token = state.auth.token;

  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

export default api;