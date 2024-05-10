import React, { useEffect, useRef } from "react";
import * as d3 from "d3";

const MonthlyLineGraph = ({ incomeData, expenseData }) => {
  const svgRef = useRef();

  useEffect(() => {
    const parseDate = d3.timeParse("%Y-%m-%d");
    incomeData.forEach((d) => {
      d.date = parseDate(d.date);
    });
    expenseData.forEach((d) => {
      d.date = parseDate(d.date);
    });

    const incomeByDay = d3.rollup(
      incomeData,
      (s) => d3.sum(s, (d) => d.amount),
      (d) => d.date.getDate()
    );
    const expensesByDay = d3.rollup(
      expenseData,
      (v) => d3.sum(v, (d) => d.amount),
      (d) => d.date.getDate()
    );

    const margin = { top: 20, right: 20, bottom: 30, left: 40 };
    const width = 600 - margin.left - margin.right;
    const height = 400 - margin.top - margin.bottom;

    const svg = d3
      .select(svgRef.current)
      .attr("width", width + margin.left + margin.right)
      .attr("height", height + margin.top + margin.bottom)
      .append("g")
      .attr("transform", `translate(${margin.left}, ${margin.top})`);

    const x = d3.scaleLinear().range([0, width]);
    const y = d3.scaleLinear().range([height, 0]);

    const lineIncome = d3
      .line()
      .x((d) => x(d[0]))
      .y((d) => y(d[1]));

    const lineExpenses = d3
      .line()
      .x((d) => x(d[0]))
      .y((d) => y(d[1]));

    const allDays = new Set([...incomeByDay.keys(), ...expensesByDay.keys()]);
    x.domain([1, 31]);
    y.domain([
      0,
      Math.max(
        d3.max([...incomeByDay.values()]),
        d3.max([...expensesByDay.values()])
      ),
    ]);

    svg
      .append("g")
      .attr("class", "axis")
      .attr("transform", `translate(o, ${height})`)
      .call(d3.axisBottom(x).ticks(31).tickFormat(d3.format("d")));

    svg
      .append("g")
      .attr("class", "axis")
      .call(d3.axisLeft(y).ticks(5).tickSize(-width));

    svg
      .append("path")
      .datum([...incomeByDay])
      .attr("class", "line-income")
      .attr("d", lineIncome)
      .style("fill", "none")
      .style("stroke", "green");

    svg
      .append("path")
      .datum([...expensesByDay])
      .attr("class", "line-expenses")
      .attr("d", lineExpenses)
      .style("fill", "none")
      .style("stroke", "red");
  }, [incomeData, expenseData]);

  return <svg ref={svgRef}></svg>;
};

export default MonthlyLineGraph;
