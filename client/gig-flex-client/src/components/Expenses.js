import React from "react";
import { Link } from "react-router-dom";
import {
  TrashIcon,
  PencilSquareIcon,
  PlusCircleIcon,
} from "@heroicons/react/24/solid";

const Expenses = ({ expenses }) => {
  return (
    <div className="form-wrapper">
      <Link to={`/expenses/add`} className="btn btn--dark">
        <h3>Expenses</h3>
        <PlusCircleIcon width={20} />
      </Link>
      <div className="table">
        <table>
          <tbody>
            {expenses.map((expense) => (
              <tr key={expense.id}>
                <td>{expense.name}</td>
                <td>${expense.amount}</td>
                <td>
                  <Link
                    to={`/expenses/edit/${expense.id}`}
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

export default Expenses;
