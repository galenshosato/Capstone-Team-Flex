import React, { useEffect, useRef } from "react";
import * as d3 from "d3";

const PieChart = ({ data }) => {
    const svgRef = useRef();

    useEffect(() => {
        const width = 400;
        const height = 400;
        const radius = Math.min(width, height) / 2;

        const svg = d3
            .select(svgRef.current)
            .attr("width", width)
            .attr("height", height)
            .append("g")
            .attr("transform", `translate(${width / 2},${height / 2})`);

        const color = d3.scaleOrdinal(d3.schemeCategory10);

        const pie = d3
            .pie()
            .value((d) => d.percentage)
            .sort(null);

        const arc = d3.arc().innerRadius(0).outerRadius(radius);

        const arcs = svg
            .selectAll("arc")
            .data(pie(data))
            .enter()
            .append("g")
            .attr("class", "arc");

        arcs.append("path")
            .attr("d", arc)
            .attr("fill", (d, i) => color(i));

        arcs.append("text")
            .attr("transform", (d) => `translate(${arc.centroid(d)})`)
            .attr("text-anchor", "middle")
            .attr("font-size", ".6rem")
            .text((d) => d.data.name);
    }, [data]);

    return <svg ref={svgRef}></svg>;
};

export default PieChart;
