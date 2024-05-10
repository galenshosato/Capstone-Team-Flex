import "./App.css";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import ExpensesForm from "./components/ExpensesForm";
import Main from "./layouts/Main";
import Dashboard from "./pages/Dashboard";
import GoalsForm from "./components/GoalsForm";
import IncomeForm from "./components/IncomeForm";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Main />,
    errorElement: "error",
    children: [
      {
        index: true,
        element: <Dashboard />,
        errorElement: "error",
      },
      {
        path: "goals/add",
        element: <GoalsForm />,
        errorElement: "error",
      },
      {
        path: "goals/:id",
        element: <GoalsForm />,
        errorElement: "error",
      },
      {
        path: "income/add",
        element: <IncomeForm />,
        errorElement: "error",
      },
      {
        path: "income/:id",
        element: <IncomeForm />,
        errorElement: "error",
      },
      {
        path: "expenses/add",
        element: <ExpensesForm />,
        errorElement: "error",
      },
      {
        path: "expenses/:id",
        element: <ExpensesForm />,
        errorElement: "error",
      },
    ],
  },
]);

function App() {
  return (
    <div className="App">
      <RouterProvider router={router} />
    </div>
  );
}

export default App;
