import React from "react";
import GoalsItem from "./GoalsItem";
import { Link } from "react-router-dom";
import { PlusCircleIcon } from "@heroicons/react/24/solid";

const Goals = ({ bankBalance, goals, expenses, onDeleteGoal }) => {
  return (
    <div className="form-wrapper">
      <div className="form-header">
        <h3>
          <span>Goals</span>
        </h3>
        <Link to={`/goals/add`} className="btn btn--dark">
          <h4>Add</h4>
          <PlusCircleIcon width={20} />
        </Link>
      </div>
      <div className="table">
        <table>
          <thead>
            <tr>
              {["Name", "%", "Budget", "Actual", "", ""].map((i, index) => (
                <th key={index}>{i}</th>
              ))}
            </tr>
          </thead>
          <tbody>
            {goals.map((goal) => (
              <tr key={goal.goalId}>
                <GoalsItem
                  goal={goal}
                  bankBalance={bankBalance}
                  expenses={expenses}
                  onDelete={onDeleteGoal}
                />
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Goals;
