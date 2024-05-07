# Application Scenarios

## Income

### Display Income Items

Display a list of income items

-   Display based on month and year

### Create Income

Create an Income object
Suggested data:

-   Name
-   Description of the income
-   Amount
-   Date
-   **Pre-condition**: Amount must be greater than $0
-   **Post-condition**: Income item is added to the list of income items on the user's home page. The user's bank is increased by the income amount

### Edit Income

Edit an Income object
Editable data:

-   Name
-   Description
-   Amount
-   Date
-   **Pre-condition**: Amount must be greater than $0
-   **Post-condition**: The bank is increased or decreased based on the amount adjustment. The attribute is changed in the database and re-rendered in the DOM

### Delete Income

Delete an Income Object

-   **Pre-condition**: None
-   **Post-condition**: Bank decreases by deleted amount. The income object is deleted from the list

## Expense

### Display Expense Items

Display a list of expense items

-   Display based on month and year

### Create Expense

Create an Expense Object
Suggested data:

-   Name
-   Description of the Income
-   Amount
-   Date
-   Goal
-   **Pre-condition**: Amount must be greater than $0. A goal must be assigned.
-   **Post-condition**: Expense item added to the list of expense items. The bank decreases by the expense amount

### Edit Expense

Edit an Expense object
Editable Data:

-   Name
-   Description
-   Amount
-   Date
-   **Pre-condition**: Amount must be greater than $0. A goal must be assigned.
-   **Post-condition**: The bank is increased or decreased based on the amount adjustment. The attribute is changed in the database and re-rendered in the DOM

### Delete Expense

Delete an expense

-   **Pre-condition**: None
-   **Post-condition**: Bank increases by deleted amount. The goal amount adjusts. The expense is deleted from the list.

## Goal

### Display Goals

Display a list of Goal objects

-   Goals should have an actual amount and a budget amount.
-   The actual amount is the amount that the expenses add up to and the budget amount should be the % of the amount alloted to that goal

### Create Goal

Create a Goal object
Suggested data:

-   Description
-   Goal Percentage
-   **Pre-condition**: Percentage must be larger than 0. The percentage cannot exceed 100 when combined with the other goals.
-   **Post-condition**: Goal is displayed with the other list of goals

### Edit Goal

Edit a Goal object
Editable Data:

-   Description
-   Goal Percentage
-   **Pre-condition**: Percentage must be larger than 0. The percentage cannot exceed 100 when combined with the other goals.
-   **Post-condition**: Attributes re-render in the DOM. The budget percentage adjusts in the DOM

### Delete Goal

Delete a Goal object

-   **Pre-condition**: None
-   **Post-condition**: Goal is deleted from the list. _Need to figure out what to do with expenses that are not assigned to goals_
