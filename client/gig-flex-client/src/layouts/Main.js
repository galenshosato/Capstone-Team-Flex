import { Outlet } from "react-router-dom";
import Nav from "../components/Nav";
import wave from "../assets/wave.svg";

const Main = () => {
  return (
    <div className="layout">
      <Nav />
      <main>
        <Outlet />
      </main>
      <img src={wave} alt="" />
    </div>
  );
};

export default Main;
