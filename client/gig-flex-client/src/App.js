import "./App.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./layouts/Home";
import Navbar from "./components/Navbar";
import GoalsForm from "./components/GoalsForm";
import IncomeForm from "./components/IncomeForm";
import ExpensesForm from "./components/ExpensesForm";
import wave from "./assets/wave.svg";

function App() {
  return (
    <Router>
      <Navbar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/goals/add" element={<GoalsForm />} />
        <Route path="/goals/edit/:id" element={<GoalsForm />} />
        <Route path="/income/add" element={<IncomeForm />} />
        <Route path="/income/edit/:id" element={<IncomeForm />} />
        <Route path="/expenses/add" element={<ExpensesForm />} />
        <Route path="/expenses/edit/:id" element={<ExpensesForm />} />
      </Routes>
      <img src={wave} alt="" />
    </Router>
  );
}

export default App;
