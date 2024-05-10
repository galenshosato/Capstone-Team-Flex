import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { PlusCircleIcon, XCircleIcon } from "@heroicons/react/24/solid";

const incomeDefault = {
  name: "",
  amount: "",
  description: "",
  date: "",
  userId: 2,
};

const IncomeForm = () => {
  const [income, setIncome] = useState(incomeDefault);
  const url = "http://localhost:8080/api/income";
  const navigate = useNavigate();
  const { id } = useParams();

  useEffect(() => {
    if (id) {
      fetchIncomeData(id);
    }
  }, [id]);

  const fetchIncomeData = (id) => {
    fetch(`${url}/${id}`)
      .then((response) => response.json())
      .then((data) => setIncome(data))
      .catch(console.error);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setIncome((prevIncome) => ({
      ...prevIncome,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (id) {
      updateIncome();
    } else {
      addIncome();
    }
  };

  const addIncome = () => {
    const init = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(income),
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

  const updateIncome = () => {
    income.incomeId = id;

    const init = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(income),
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
      <h2>{id > 0 ? "Update Income" : "Add Income"}</h2>
      <form className="grid-sm" onSubmit={handleSubmit}>
        <div className="expense-inputs">
          <div className="grid-xs">
            <label htmlFor="name">Income Name</label>
            <input
              type="text"
              name="name"
              id="name"
              value={income.name}
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
              value={income.amount}
              onChange={handleChange}
              placeholder="e.g., 200"
              required
            />
          </div>
        </div>
        <div className="expense-inputs">
          <div className="grid-xs">
            <label htmlFor="date">Date</label>
            <input
              type="date"
              name="date"
              id="date"
              value={income.date}
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
            value={income.description}
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
            <span>{id > 0 ? "Update Income" : "Add Income"}</span>
            <PlusCircleIcon width={20} />
          </button>
        </div>
      </form>
    </div>
  );
};

export default IncomeForm;
