import React from "react";
import MonthlyLineGraph from "./MonthlyLineGraph";
import MonthlyBarGraph from "./MonthlyBarGraph";
import PieChart from "./PieChart";

const Graph = ({ income, expenses, data }) => {
    return (
        // <div className="form-wrapper">
        //   <h3>Overview</h3>
        //   <p>Placeholder for graph component</p>
        //   <p>Lorem ipsum</p>
        //   <p>Lorem ipsum</p>
        //   <p>Lorem ipsum</p>
        // </div>
        <>
            {/* <MonthlyBarGraph /> */}
            <PieChart data={data} />
        </>
    );
};

export default Graph;
