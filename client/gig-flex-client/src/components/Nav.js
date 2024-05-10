import React, { useState, useEffect } from "react";
import { NavLink, useNavigate } from "react-router-dom";
import logo from "../assets/logo.svg";
import { UserCircleIcon } from "@heroicons/react/24/solid";
import { setUserLocalStorage } from "../helpers";

const Nav = () => {
  const navigate = useNavigate();
  const [user, setUser] = useState(() => {
    const storedUser = localStorage.getItem("user");
    return storedUser ? JSON.parse(storedUser) : null;
  });

  useEffect(() => {
    const handleStorageChange = () => {
      const storedUser = localStorage.getItem("user");
      setUser(storedUser ? JSON.parse(storedUser) : null);
    };

    window.addEventListener("storage", handleStorageChange);

    return () => {
      window.removeEventListener("storage", handleStorageChange);
    };
  }, []);

  const handleClick = () => {
    if (window.confirm("Are you sure you want to log out?")) {
      setUserLocalStorage(null);
      navigate(0);
    }
  };

  return (
    <nav>
      <NavLink to="/" aria-label="Return home">
        <img src={logo} alt="" height={35} />
        <span>Gig-Flex</span>
      </NavLink>
      {user && (
        <button type="button" className="btn btn--dark" onClick={handleClick}>
          <span>Log Out</span>
          <UserCircleIcon width={20} />
        </button>
      )}
    </nav>
  );
};

export default Nav;
