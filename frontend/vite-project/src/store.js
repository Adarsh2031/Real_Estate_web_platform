import { configureStore, createSlice } from "@reduxjs/toolkit";

// Load from localStorage
const savedAuth = JSON.parse(localStorage.getItem("auth")) || {
  token: null,
  username: null,
};

const authSlice = createSlice({
  name: "auth",
  initialState: savedAuth,
  reducers: {
    login: (state, action) => {
      state.token = action.payload.token;
      state.username = action.payload.username;

      // Save to localStorage
      localStorage.setItem("auth", JSON.stringify(state));
    },
    logout: (state) => {
      state.token = null;
      state.username = null;

      // Remove from localStorage
      localStorage.removeItem("auth");
    },
  },
});

export const { login, logout } = authSlice.actions;

const store = configureStore({
  reducer: {
    auth: authSlice.reducer,
  },
});

export default store;