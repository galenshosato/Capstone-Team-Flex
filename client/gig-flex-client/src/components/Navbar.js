import React from "react";
import logo from "../assets/logo.svg";
import { NavLink } from "react-router-dom";

const Navbar = () => {
  return (
    <nav>
      <NavLink to="/" aria-label="Homepage">
        <img src={logo} alt="" height={30} />
        <span>Gig-Flex</span>
      </NavLink>
    </nav>
  );
};

export default Navbar;
