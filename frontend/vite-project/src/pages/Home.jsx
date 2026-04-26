import { useEffect, useState } from "react";
import axios from "axios";

export default function Home() {
  const [properties, setProperties] = useState([]);

  // Fetch properties
  const fetchProperties = async () => {
    try {
      const res = await axios.get("http://localhost:8080/properties");
      setProperties(res.data);
    } catch (err) {
      console.log(err);
      alert("Failed to load properties");
    }
  };

  useEffect(() => {
    fetchProperties();
  }, []);

  return (
    <div className="container">
      <h2>All Properties</h2>

      {properties.length === 0 ? (
        <p>No properties found</p>
      ) : (
        properties.map((p) => (
          <div key={p.id} style={{ border: "1px solid #ccc", padding: "10px", margin: "10px 0" }}>
            <h3>{p.title}</h3>
            <p>{p.description}</p>
            <p><b>Price:</b> ₹{p.price}</p>
            <p><b>Location:</b> {p.location}</p>
            <p><b>Owner:</b> {p.owner?.username}</p>
          </div>
        ))
      )}
    </div>
  );
}