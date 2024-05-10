import React, { useEffect, useRef } from "react";
import * as d3 from "d3";

const MonthlyBarGraph = ({ incomeData, expenseData }) => {
  const svgRef = useRef();

  useEffect(() => {
    const totalIncome = d3.sum(incomeData, (d) => d.amount);
    const totalExpenses = d3.sum(expenseData, (d) => d.amount);

    const margin = { top: 20, right: 20, bottom: 30, left: 40 },
      width = 400 - margin.left - margin.right,
      height = 300 - margin.top - margin.bottom;

    const svg = d3
      .select(svgRef.current)
      .attr("width", width + margin.left + margin.right)
      .attr("height", height + margin.top + margin.bottom)
      .append("g")
      .attr("transform", `translate(${margin.left},${margin.top})`);

    const x = d3
      .scaleBand()
      .range([0, width])
      .padding(0.1)
      .domain(["Income", "Expenses"]);

    const y = d3
      .scaleLinear()
      .range([height, 0])
      .domain([0, Math.max(totalIncome, totalExpenses)]);

    svg
      .selectAll(".bar")
      .data([totalIncome, totalExpenses])
      .enter()
      .append("rect")
      .attr("class", "bar")
      .attr("class", (d, i) => (i === 0 ? "income-bar" : "expense-bar"))
      .attr("x", (d) => x(d === totalIncome ? "Income" : "Expenses"))
      .attr("y", (d) => y(d))
      .attr("width", x.bandwidth())
      .attr("height", (d) => height - y(d));

    svg
      .append("g")
      .attr("transform", `translate(0,${height})`)
      .call(d3.axisBottom(x));

    svg.append("g").call(d3.axisLeft(y));
  }, [incomeData, expenseData]);

  return <svg ref={svgRef}></svg>;
};

export default MonthlyBarGraph;
