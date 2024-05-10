import React, { useState } from "react";
import { UserPlusIcon } from "@heroicons/react/24/solid";
import illustration from "../assets/illustration.jpg";

const Login = ({ onUserLogin }) => {
  const [email, setEmail] = useState("");

  const url = "http://localhost:8080/api/user";

  const handleSubmit = (e) => {
    e.preventDefault();

    fetch(`${url}/${email}`)
      .then((response) => {
        if (response.status === 200) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected status code: ${response.status}`);
        }
      })
      .then((data) => {
        onUserLogin(data);
      })
      .catch(console.error);
  };

  return (
    <div className="intro">
      <div>
        <h1>
          Specifically designed for <span className="accent">Gigs</span>
        </h1>
        <p>
          Providing real-time insights and adaptive budgeting tools to navigate
          the uncertainty of the gig economy.
        </p>
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            name="email"
            required
            placeholder="What is your user email?"
            aria-label="User Email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
          <button type="submit" className="btn btn--dark">
            <span>Log In</span>
            <UserPlusIcon width={20} />
          </button>
        </form>
      </div>
      <img src={illustration} alt="unDraw graphic" width={600} />
    </div>
  );
};

export default Login;
