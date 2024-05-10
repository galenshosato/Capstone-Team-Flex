import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import Login from "../components/Login";
import DataGraph from "../components/DataGraph";
import Goals from "../components/Goals";
import Income from "../components/Income";
import Expenses from "../components/Expenses";
import { formatCurrency, monthYear, setUserLocalStorage } from "../helpers";

const Dashboard = () => {
  const [user, setUser] = useState(() => {
    const storedUser = localStorage.getItem("user");
    return storedUser ? JSON.parse(storedUser) : null;
  });
  const [goals, setGoals] = useState([]);
  const [income, setIncome] = useState([]);
  const [expenses, setExpenses] = useState([]);
  const [justSetUser, setJustSetUser] = useState(false);
  const navigate = useNavigate();
  const url = "http://localhost:8080/api";

  useEffect(() => {
    const fetchData = async () => {
      try {
        if (user) {
          const goalsResponse = await fetch(`${url}/goal/get/${user.userId}`);
          if (goalsResponse.status === 200) {
            const goalsData = await goalsResponse.json();
            setGoals(goalsData);
          } else {
            throw new Error(`Unexpected status code: ${goalsResponse.status}`);
          }

          const incomeResponse = await fetch(
            `${url}/income/${user.userId}/get/5/2024`
          );
          if (incomeResponse.status === 200) {
            const incomeData = await incomeResponse.json();
            setIncome(incomeData);
          } else {
            throw new Error(`Unexpected status code: ${incomeResponse.status}`);
          }

          const expenseResponse = await fetch(
            `${url}/expense/${user.userId}/get/5/2024`
          );
          if (expenseResponse.status === 200) {
            const expenseData = await expenseResponse.json();
            setExpenses(expenseData);
          } else {
            throw new Error(
              `Unexpected status code: ${expenseResponse.status}`
            );
          }
        }
      } catch (error) {
        console.log(error);
      }
    };

    fetchData();
  }, [user]);

  const handleDeleteGoal = (goalId) => {
    if (window.confirm("Are you sure you want to delete this income item?")) {
      const init = {
        method: "DELETE",
      };
      fetch(`${url}/goal/${goalId}`, init)
        .then((response) => {
          if (response.status === 204) {
            setGoals((prevGoal) =>
              prevGoal.filter((goal) => goal.goalId !== goalId)
            );
          } else {
            return Promise.reject(`Unexpected Status Code: ${response.status}`);
          }
        })
        .catch(console.log);
    }
  };

  const handleDeleteIncome = (incomeId) => {
    if (window.confirm("Are you sure you want to delete this income item?")) {
      const init = {
        method: "DELETE",
      };
      fetch(`${url}/income/${incomeId}`, init)
        .then((response) => {
          if (response.status === 204) {
            setIncome((prevIncome) =>
              prevIncome.filter((inc) => inc.incomeId !== incomeId)
            );
          } else {
            return Promise.reject(`Unexpected Status Code: ${response.status}`);
          }
        })
        .catch(console.log);
    }
  };

  const handleDeleteExpense = (expenseId) => {
    if (window.confirm("Are you sure you want to delete this expense item?")) {
      const init = {
        method: "DELETE",
      };
      fetch(`${url}/expense/${expenseId}`, init)
        .then((response) => {
          if (response.status === 204) {
            setExpenses((prevExpense) =>
              prevExpense.filter((exp) => exp.expenseId !== expenseId)
            );
          } else {
            return Promise.reject(`Unexpected Status Code: ${response.status}`);
          }
        })
        .catch(console.log);
    }
  };

  useEffect(() => {
    setUserLocalStorage(user);
    if (justSetUser) {
      navigate(0);
      setJustSetUser(false);
    }
  }, [user, justSetUser, navigate]);

  const totalIncome = income.reduce((acc, curr) => acc + curr.amount, 0);
  const totalExpenses = expenses.reduce((acc, curr) => acc + curr.amount, 0);
  const bankBalance = user ? user.bank + totalIncome - totalExpenses : 0;

  const handleUserLogin = (user) => {
    setUser(user);
    setJustSetUser(true);
  };

  const balanceClass = bankBalance <= 0 ? "red" : "green";

  return (
    <>
      {user ? (
        <div className="dashboard">
          <h2>
            Welcome back, <span className="accent">{user.name}</span>
          </h2>
          <h3>{monthYear}</h3>
          <h3>
            <span className={balanceClass}>{formatCurrency(bankBalance)}</span>
          </h3>
          <div className="grid-sm">
            <div className="grid-lg">
              <div className="flex-lg">
                <DataGraph income={income} expenses={expenses} data={goals} />
                <Goals
                  bankBalance={bankBalance}
                  goals={goals}
                  expenses={expenses}
                  onDeleteGoal={handleDeleteGoal}
                />
                <Income income={income} onDeleteIncome={handleDeleteIncome} />
                <Expenses
                  expenses={expenses}
                  goals={goals}
                  onDeleteExpense={handleDeleteExpense}
                />
              </div>
            </div>
          </div>
        </div>
      ) : (
        <Login onUserLogin={handleUserLogin} />
      )}
    </>
  );
};

export default Dashboard;
