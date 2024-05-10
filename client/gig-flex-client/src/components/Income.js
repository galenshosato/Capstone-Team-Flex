import React from "react";
import IncomeItem from "./IncomeItem";
import { Link } from "react-router-dom";
import { PlusCircleIcon } from "@heroicons/react/24/solid";

const Income = ({ income, onDeleteIncome }) => {
  return (
    <div className="form-wrapper">
      <div className="form-header">
        <h3>
          <span className="green">Income</span>
        </h3>
        <Link to={`/income/add`} className="btn btn--dark">
          <h4>Add</h4>
          <PlusCircleIcon width={20} />
        </Link>
      </div>
      <div className="table">
        <table>
          {/* <thead>
            <tr>
              {["Name", "Amount", "", ""].map((i, index) => (
                <th key={index}>{i}</th>
              ))}
            </tr>
          </thead> */}
          <tbody>
            {income.map((inc) => (
              <tr key={inc.incomeId}>
                <IncomeItem inc={inc} onDelete={onDeleteIncome} />
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default Income;
