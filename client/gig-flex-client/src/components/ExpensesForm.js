import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { PlusCircleIcon, XCircleIcon } from "@heroicons/react/24/solid";

const expenseDefault = {
  name: "",
  amount: "",
  description: "",
  date: "",
  userId: 2,
  goalId: "",
};

const ExpensesForm = () => {
  const [expense, setExpense] = useState(expenseDefault);
  const [goals, setGoals] = useState([]);
  const url = "http://localhost:8080/api/expense";
  const navigate = useNavigate();
  const { id } = useParams();

  useEffect(() => {
    if (id) {
      fetchExpenseData(id);
    }
    fetchGoals();
  }, [id]);

  const fetchExpenseData = (id) => {
    fetch(`${url}/${id}`)
      .then((response) => response.json())
      .then((data) => setExpense(data))
      .catch(console.error);
  };

  const fetchGoals = () => {
    fetch(`http://localhost:8080/api/goal/get/2`)
      .then((response) => response.json())
      .then((data) => setGoals(data))
      .catch(console.error);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setExpense((prevExpense) => ({
      ...prevExpense,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (id) {
      updateExpenses();
    } else {
      addExpenses();
    }
  };

  const addExpenses = () => {
    const init = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(expense),
    };
    fetch(url, init)
      .then((response) => {
        if (response.status === 201 || response.status === 400) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected status code: ${response.status}`);
        }
      })
      .then(() => {
        navigate("/");
      })
      .catch(console.log);
  };

  const updateExpenses = () => {
    expense.expenseId = id;

    const init = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(expense),
    };

    fetch(`${url}/${id}`, init)
      .then((response) => {
        if (response.status === 204) {
          return null;
        } else if (response.status === 400) {
          return response.json();
        } else {
          return Promise.reject(`Unexpected status code: ${response.status}`);
        }
      })
      .then(() => {
        navigate("/");
      })
      .catch(console.log);
  };

  const handleCancel = () => {
    navigate(-1);
  };

  return (
    <div className="form-wrapper">
      <h2>{id > 0 ? "Update Expense" : "Add Expense"}</h2>
      <form className="grid-sm" onSubmit={handleSubmit}>
        <div className="expense-inputs">
          <div className="grid-xs">
            <label htmlFor="name">Expense Name</label>
            <input
              type="text"
              name="name"
              id="name"
              value={expense.name}
              onChange={handleChange}
              placeholder="e.g., Bartending"
              required
            />
          </div>
          <div className="grid-xs">
            <label htmlFor="amount">Amount</label>
            <input
              type="number"
              step="0.01"
              inputMode="decimal"
              name="amount"
              id="amount"
              value={expense.amount}
              onChange={handleChange}
              placeholder="e.g., 200"
              required
            />
          </div>
        </div>
        <div className="expense-inputs">
          <div className="grid-xs">
            <label htmlFor="goalId">Goal Category</label>
            <select
              name="goalId"
              id="goalId"
              value={expense.goalId}
              onChange={handleChange}
              required
            >
              <option value="">Select a Goal</option>
              {goals.map((goal) => (
                <option key={goal.goalId} value={goal.goalId}>
                  {goal.description}
                </option>
              ))}
            </select>
          </div>
          <div className="grid-xs">
            <label htmlFor="date">Date</label>
            <input
              type="date"
              name="date"
              id="date"
              value={expense.date}
              onChange={handleChange}
              required
            />
          </div>
        </div>
        <div className="grid-xs">
          <label htmlFor="description">Description</label>
          <textarea
            type="text"
            name="description"
            id="description"
            value={expense.description}
            onChange={handleChange}
            placeholder="Lorem Ipsum"
            required
          />
        </div>
        <div className="btn-group">
          <button
            type="button"
            className="btn btn--warning"
            onClick={handleCancel}
          >
            <span>Cancel</span>
            <XCircleIcon width={20} />
          </button>

          <button type="submit" className="btn btn--dark">
            <span>{id > 0 ? "Update Expense" : "Add Expense"}</span>
            <PlusCircleIcon width={20} />
          </button>
        </div>
      </form>
    </div>
  );
};

export default ExpensesForm;
