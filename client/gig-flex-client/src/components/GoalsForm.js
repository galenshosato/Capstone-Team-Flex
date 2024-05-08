import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { PlusCircleIcon } from "@heroicons/react/24/solid";

const GoalsForm = () => {
  const [goal, setGoal] = useState({});
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
          setGoal(data);
        })
        .catch(console.log);
    }
  }, [id]);

  const handleChange = (e) => {
    const newGoal = { ...goal };
    newGoal[e.target.name] = e.target.value;
    setGoal(newGoal);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (id) {
      updateGoal();
    } else {
      addGoal();
    }
  };

  const addGoal = () => {};

  const updateGoal = () => {};

  return (
    <div className="form-wrapper">
      <h2>{id > 0 ? "Update Goal" : "Add Goal"}</h2>
      <form onSubmit={handleSubmit}>
        <div className="expense-inputs">
          <div className="grid-xs">
            <label htmlFor="name">Goal Name</label>
            <input
              id="name"
              name="name"
              type="text"
              className=""
              placeholder="e.g., Save for trip to Mexico"
              value={goal.name}
              onChange={handleChange}
              required
            />
          </div>
        </div>
        <div className="expense-inputs">
          <div className="grid-xs">
            <label htmlFor="budget">Budget</label>
            <input
              id="budget"
              name="budget"
              type="number"
              placeholder="e.g., $2000"
              value={goal.budget}
              onChange={handleChange}
              required
            />
          </div>
          <div className="grid-xs">
            <label htmlFor="actual">Actual</label>
            <input
              id="actual"
              name="actual"
              type="number"
              placeholder="e.g., $850"
              value={goal.actual}
              onChange={handleChange}
              required
            />
          </div>
        </div>
        <button type="submit" className="btn btn--dark">
          <span>{id > 0 ? "Update Goal" : "Add Goal"}</span>
          <PlusCircleIcon width={20} />
        </button>
      </form>
    </div>
  );
};

export default GoalsForm;
