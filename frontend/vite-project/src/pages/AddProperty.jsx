import { useState } from "react";
import axios from "axios";
import api from "../services/api";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

export default function AddProperty() {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [price, setPrice] = useState("");
  const [location, setLocation] = useState("");

  const { token } = useSelector((state) => state.auth);
  const navigate = useNavigate();

  const handleAdd = async () => {
    try {
      await api.post("/properties", {
        title,
        description,
        price: Number(price),
        location,
    });

      alert("Property added successfully");
      navigate("/");
    } catch (err) {
      console.log(err);
      alert("Failed to add property");
    }
  };

  return (
    <div className="container">
      <h2>Add Property</h2>

      <input
        type="text"
        placeholder="Title"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
      />

      <textarea
        placeholder="Description"
        value={description}
        onChange={(e) => setDescription(e.target.value)}
      />

      <input
        type="number"
        placeholder="Price"
        value={price}
        onChange={(e) => setPrice(e.target.value)}
      />

      <input
        type="text"
        placeholder="Location"
        value={location}
        onChange={(e) => setLocation(e.target.value)}
      />

      <button onClick={handleAdd}>Add Property</button>
    </div>
  );
}