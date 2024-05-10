import React, { useState } from "react";
import MonthlyBarGraph from "./MonthlyBarGraph";
import PieChart from "./PieChart";

const DataGraph = ({ income, expenses, data }) => {
  const [selectedComponent, setSelectedComponent] = useState("monthlyBarGraph");

  const handleComponentChange = (event) => {
    setSelectedComponent(event.target.value);
  };

  return (
    <div className="form-wrapper">
      <h3>
        <span className="muted">Overview</span>
      </h3>
      <div className="graph">
        {selectedComponent === "monthlyBarGraph" && (
          <MonthlyBarGraph incomeData={income} expenseData={expenses} />
        )}
        {selectedComponent === "pieChart" && <PieChart data={data} />}
        <div className="radio-inputs">
          <label htmlFor="monthlyBarGraph" className="form-control">
            <input
              type="radio"
              id="monthlyBarGraph"
              name="graphType"
              value="monthlyBarGraph"
              checked={selectedComponent === "monthlyBarGraph"}
              onChange={handleComponentChange}
            />
            Income/Expense Ratio
          </label>
          <label htmlFor="pieChart" className="form-control">
            <input
              type="radio"
              id="pieChart"
              name="graphType"
              value="pieChart"
              checked={selectedComponent === "pieChart"}
              onChange={handleComponentChange}
            />
            Goals
          </label>
        </div>
      </div>
    </div>
  );
};

export default DataGraph;
