import { Link } from "react-router-dom";
import { TrashIcon, PencilSquareIcon } from "@heroicons/react/24/solid";
import { formatCurrency, generateRandomColor } from "../helpers";

const ExpensesItem = ({ exp, goals, onDelete }) => {
  const handleDeleteClick = () => {
    onDelete(exp.expenseId);
  };

  const associatedGoal = goals.find((goal) => goal.goalId === exp.goalId);
  const backgroundColor = generateRandomColor();

  return (
    <>
      <td>{exp.name}</td>
      <td>{formatCurrency(exp.amount)}</td>
      <td>
        <div className="goal-description" style={{ backgroundColor }}>
          {associatedGoal ? associatedGoal.description : ""}
        </div>
      </td>
      <td>
        <Link to={`/expenses/${exp.expenseId}`} className="btn btn--dark">
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

export default ExpensesItem;
