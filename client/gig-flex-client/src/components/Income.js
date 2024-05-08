import React from "react";
import { Link } from "react-router-dom";
import {
  TrashIcon,
  PencilSquareIcon,
  PlusCircleIcon,
} from "@heroicons/react/24/solid";

const Income = ({ income }) => {
  return (
    <div className="form-wrapper">
      <Link to={`/income/add`} className="btn btn--dark">
        <h3>Income</h3>
        <PlusCircleIcon width={20} />
      </Link>
      <div className="table">
        <table>
          <tbody>
            {income.map((income) => (
              <tr key={income.id}>
                <td>{income.name}</td>
                <td>${income.amount}</td>
                <td>
                  <Link
                    to={`/income/edit/${income.id}`}
                    className="btn btn--dark"
                  >
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

export default Income;
