import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { PlusCircleIcon } from "@heroicons/react/24/solid";

const ExpensesForm = () => {
  const [expenses, setExpenses] = useState({});
  const url = "http://localhost:8080/api/...";
  const { id } = useParams();

  useEffect(() => {
    if (id) {
      fetch(`${url}/${id}`)
        .then((response) => {
          if (response.status === 200) {
            return response.json();
          } else {
            return Promise.reject(`Unexpected status code: ${response.status}`);
          }
        })
        .then((data) => {
          setExpenses(data);
        })
        .catch(console.log);
    }
  }, [id]);

  const handleChange = (e) => {
    const newExpenses = { ...expenses };
    newExpenses[e.target.name] = e.target.value;
    setExpenses(newExpenses);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (id) {
      updateExpenses();
    } else {
      addExpenses();
    }
  };

  const addExpenses = () => {};

  const updateExpenses = () => {};

  return (
    <div className="form-wrapper">
      <h2>{id > 0 ? "Update Expenses" : "Add Expenses"}</h2>
      <form onSubmit={handleSubmit}>
        <div className="expense-inputs">
          <div className="grid-xs">
            <label htmlFor="name">Name</label>
            <input
              id="name"
              name="name"
              type="text"
              placeholder="e.g., Bartending"
              value={expenses.name}
              onChange={handleChange}
              required
            />
          </div>
          <div className="grid-xs">
            <label htmlFor="amount">Amount</label>
            <input
              id="amount"
              name="amount"
              type="number"
              placeholder="e.g., $850"
              value={expenses.amount}
              onChange={handleChange}
              required
            />
          </div>
        </div>
        <button type="submit" className="btn btn--dark">
          <span>{id > 0 ? "Update Expenses" : "Add Expenses"}</span>
          <PlusCircleIcon width={20} />
        </button>
      </form>
    </div>
  );
};

export default ExpensesForm;
