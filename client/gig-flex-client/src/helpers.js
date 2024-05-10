export const formatPercentage = (percentage) => {
  return `${percentage}%`;
};

export const formatCurrency = (amount) => {
  return amount.toLocaleString(undefined, {
    style: "currency",
    currency: "USD",
  });
};

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
export const monthYear = months[date.getMonth()] + " " + date.getFullYear();

export const setUserLocalStorage = (user) => {
  if (user) {
    localStorage.setItem("user", JSON.stringify(user));
  } else {
    localStorage.removeItem("user");
  }
};

export const generateRandomColor = () => {
  const r = Math.floor(Math.random() * 256);
  const g = Math.floor(Math.random() * 256);
  const b = Math.floor(Math.random() * 256);
  return `rgb(${r}, ${g}, ${b})`;
};
