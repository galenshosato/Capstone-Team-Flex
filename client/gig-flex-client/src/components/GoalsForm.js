import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { PlusCircleIcon, XCircleIcon } from "@heroicons/react/24/solid";

const goalDefault = {
  name: "",
  amount: "",
  description: "",
  date: "",
  userId: 2,
};

const GoalsForm = () => {
  const [goal, setGoal] = useState(goalDefault);
  const url = "http://localhost:8080/api/goal";
  const navigate = useNavigate();
  const { id } = useParams();

  useEffect(() => {
    if (id) {
      fetchGoalData(id);
    }
  }, [id]);

  const fetchGoalData = (id) => {
    fetch(`${url}/${id}`)
      .then((response) => response.json())
      .then((data) => setGoal(data))
      .catch(console.error);
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setGoal((prevGoal) => ({
      ...prevGoal,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (id) {
      updateGoal();
    } else {
      addGoal();
    }
  };

  const addGoal = () => {
    const init = {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(goal),
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

  const updateGoal = () => {
    goal.goalId = id;

    const init = {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(goal),
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
      <h2>{id > 0 ? "Update Goal" : "Add Goal"}</h2>
      <form className="grid-sm" onSubmit={handleSubmit}>
        <div className="grid-xs">
          <label htmlFor="description">Description</label>
          <textarea
            type="text"
            name="description"
            id="description"
            value={goal.description}
            onChange={handleChange}
            placeholder="Lorem Ipsum"
            required
          />
        </div>
        <div className="expense-inputs">
          <div className="grid-xs">
            <label htmlFor="percentage">Percentage</label>
            <div className="expenses-input">
              <input
                type="number"
                name="percentage"
                id="percentage"
                value={goal.percentage}
                onChange={handleChange}
                placeholder="e.g., 5"
                required
              />
            </div>
          </div>
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
            <span>{id > 0 ? "Update Goal" : "Add Goal"}</span>
            <PlusCircleIcon width={20} />
          </button>
        </div>
      </form>
    </div>
  );
};

export default GoalsForm;
