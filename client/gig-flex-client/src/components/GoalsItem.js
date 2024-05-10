import React from "react";
import { Link } from "react-router-dom";
import { TrashIcon, PencilSquareIcon } from "@heroicons/react/24/solid";
import { formatCurrency, formatPercentage } from "../helpers";

const GoalsItem = ({ goal, bankBalance, expenses, onDelete }) => {
  const handleDeleteClick = () => {
    onDelete(goal.goalId);
  };

  const budget = (bankBalance * goal.percentage) / 100;
  const goalExpenses = expenses.filter(
    (expense) => expense.goalId === goal.goalId
  );
  const expensesSum = goalExpenses.reduce(
    (acc, expense) => acc + expense.amount,
    0
  );

  const className = budget < expensesSum ? "red" : "green";

  return (
    <>
      <td>{goal.description}</td>
      <td>{formatPercentage(goal.percentage)}</td>
      <td>{budget > 0 ? formatCurrency(budget) : 0}</td>
      <td className={className}>{formatCurrency(expensesSum)}</td>
      <td>
        <Link to={`/goals/${goal.goalId}`} className="btn btn--dark">
          <PencilSquareIcon width={20} />
        </Link>
      </td>
      <td>
        <button
          type="submit"
          className="btn btn--warning"
          onClick={handleDeleteClick}
        >
          <TrashIcon width={20} />
        </button>
      </td>
    </>
  );
};

export default GoalsItem;
