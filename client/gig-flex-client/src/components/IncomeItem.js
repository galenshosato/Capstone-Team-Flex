import React from "react";
import { Link } from "react-router-dom";
import { TrashIcon, PencilSquareIcon } from "@heroicons/react/24/solid";
import { formatCurrency } from "../helpers";

const IncomeItem = ({ inc, onDelete }) => {
  const handleDeleteClick = () => {
    onDelete(inc.incomeId);
  };

  return (
    <>
      <td>{inc.name}</td>
      <td>{formatCurrency(inc.amount)}</td>
      <td>
        <Link to={`/income/${inc.incomeId}`} className="btn btn--dark">
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

export default IncomeItem;
