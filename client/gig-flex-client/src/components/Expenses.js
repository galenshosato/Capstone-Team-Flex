import React from "react";
import { Link } from "react-router-dom";
import { PlusCircleIcon } from "@heroicons/react/24/solid";
import ExpensesItem from "./ExpensesItem";

const Expenses = ({ expenses, goals, onDeleteExpense }) => {
  return (
    <div className="form-wrapper">
      <div className="form-header">
        <h3>
          <span className="red">Expenses</span>
        </h3>
        <Link to={`/expenses/add`} className="btn btn--dark">
          <h4>Add</h4>
          <PlusCircleIcon width={20} />
        </Link>
      </div>
      <div className="table">
        <table>
          {/* <thead>
            <tr>
              {["Name", "Amount", "Goal", "", ""].map((i, index) => (
                <th key={index}>{i}</th>
              ))}
            </tr>
          </thead> */}
          <tbody>
            {expenses.map((exp) => (
              <tr key={exp.expenseId}>
                <ExpensesItem
                  exp={exp}
                  goals={goals}
                  onDelete={onDeleteExpense}
                />
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Expenses;
