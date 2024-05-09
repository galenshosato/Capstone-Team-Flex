import React, { useState } from "react";
import MonthlyBarGraph from "./MonthlyBarGraph";
import PieChart from "./PieChart";

const Graph = ({ income, expenses, data }) => {
    const [selectedComponent, setSelectedComponent] =
        useState("monthlyBarGraph"); // Initial selection

    const handleComponentChange = (event) => {
        setSelectedComponent(event.target.value);
    };

    return (
        <div className="graph">
            {selectedComponent === "monthlyBarGraph" && (
                <MonthlyBarGraph incomeData={income} expenseData={expenses} />
            )}
            {selectedComponent === "pieChart" && <PieChart data={data} />}
            <div>
                <label htmlFor="monthlyBarGraph">
                    <input
                        type="radio"
                        id="monthlyBarGraph"
                        name="graphType"
                        value="monthlyBarGraph"
                        checked={selectedComponent === "monthlyBarGraph"}
                        onChange={handleComponentChange}
                    />
                    Income and Expenses
                </label>
                <label htmlFor="pieChart">
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
    );
};

export default Graph;
