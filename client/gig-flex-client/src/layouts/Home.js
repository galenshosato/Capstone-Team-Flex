import React from "react";
import Graph from "../components/Graph";
import Goals from "../components/Goals";
import Income from "../components/Income";
import Expenses from "../components/Expenses";

const months = [
    "January",
    "February",
    "March",
    "April",
    "May",
    "June",
    "July",
    "August",
    "September",
    "October",
    "November",
    "December",
];
const date = new Date();
const monthYear = months[date.getMonth()] + " " + date.getFullYear();
const goals = [
    { id: 1, name: "Save for car", budget: "5000.00", actual: "2000.00" },
    { id: 2, name: "Go on vacation", budget: "5000.00", actual: "2000.00" },
    { id: 3, name: "Other stuff", budget: "5000.00", actual: "2000.00" },
];

const income = [
    { id: 1, name: "Bartending", amount: "500.00", date: "2024-05-04" },
    { id: 2, name: "Security", amount: "1000.00", date: "2024-05-10" },
];

const expenses = [
    { id: 1, name: "Car note", amount: "500.00", date: "2024-05-04" },
    { id: 2, name: "Day care", amount: "500.00", date: "2024-05-10" },
    { id: 3, name: "Pet supplies", amount: "500.00", date: "2024-05-01" },
    { id: 4, name: "Groceries", amount: "60.00", date: "2024-05-01" },
];

const Home = () => {
    return (
        <div className="layout">
            <main>
                <div className="dashboard">
                    <h2>
                        Welcome back, <span className="accent">Flexer</span>
                    </h2>
                    <h3>{monthYear}</h3>
                    <h3>$0</h3>
                    <div className="grid-lg">
                        <div className="flex-lg">
                            <Graph
                                income={income}
                                expenses={expenses}
                                data={data}
                            />
                            <Goals goals={goals} />
                            <Income income={income} />
                            <Expenses expenses={expenses} />
                        </div>
                    </div>
                </div>
            </main>
        </div>
    );
};

export default Home;
