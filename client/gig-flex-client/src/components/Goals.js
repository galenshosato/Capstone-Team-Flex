import React from "react";
import { Link } from "react-router-dom";
import {
  TrashIcon,
  PencilSquareIcon,
  PlusCircleIcon,
} from "@heroicons/react/24/solid";

const Goals = ({ goals }) => {
  return (
    <div className="form-wrapper">
      <div className="display: flex">
        <Link to={`/goals/add`} className="btn btn--dark">
          <h3>Goals</h3>
          <PlusCircleIcon width={20} />
        </Link>
      </div>
      <div className="table">
        <table>
          <thead>
            <tr>
              {["Name", "Budget", "Actual"].map((i, index) => (
                <th key={index}>{i}</th>
              ))}
            </tr>
          </thead>
          <tbody>
            {goals.map((goal) => (
              <tr key={goal.id}>
                <td>{goal.name}</td>
                <td>${goal.budget}</td>
                <td>${goal.actual}</td>
                <td>
                  <Link to={`/goals/edit/${goal.id}`} className="btn btn--dark">
                    <PencilSquareIcon width={20} />
                  </Link>
                </td>
                <td>
                  <button type="submit" className="btn btn--warning">
                    <TrashIcon width={20} />
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Goals;
