# Mobile-Applications-Project
Sigourney Wamback


# User Stories

### Data Management and Persistance
- As a user, I want my financial data to be stored locally on my device so I can access it without an internet connection.
### UI and Navigation 
- As a user, I want to see a main dashboard displaying my total expenses and income, so I can quickly understand my financial status.
- As a user, I want a floating action button to appear on every screen, allowing me to send my financial summary via email when needed.

### Financial Calculations 
- As a user, I want the app to calculate and display my total expenses and income so I can have a clear picture of my overall spending and earnings.
- As a user, I want to add purchases and view an itemized list with total costs per category to help me manage my spending in different areas.
### Enhanced User Experience (Detailed Analytics)
- As a user, I want to select a specific month to view my spending, so I can analyze my monthly expenses.
- As a user, I want to see a visual graph that summarizes my expenses for the selected month, allowing me to understand my spending trends over time.
# Activity Diagram 
![image](https://github.com/user-attachments/assets/01805376-7f21-4f70-b728-00fad0a6974c)

# Mock Ups
![image](https://github.com/user-attachments/assets/cce39cd1-6021-49a3-bcb2-853614a7235c)


# Database Design
![image](https://github.com/user-attachments/assets/14169472-9719-4860-83ab-76227fad905c)

# UAT


| User Story                                         | Acceptance Criteria                                                                                                              | ID      | Test Summary                                     | Manual/Auto | Status    | QA   | UAT  |
|----------------------------------------------------|-----------------------------------------------------------------------------------------------------------------------------------|---------|--------------------------------------------------|-------------|-----------|------|------|
| As a user, I want my financial data to be stored locally on my device so I can access it without an internet connection. | The app stores all user data locally, and data persists across app restarts without needing internet. | UAT 1  | Verify data persistence in offline mode         | Manual      | completed |   |      |
| As a user, I want to see a main dashboard displaying my total expenses and income, so I can quickly understand my financial status. | Dashboard displays total expenses in red, income in green, and overall balance in black.                  | UAT 2 | Check main dashboard displays financial summary | Manual      | completed     |    |   |
| As a user, I want a floating action button to appear on the analytics screen, allowing me to send my financial summary via email when needed. | Floating action button is present on all screens and triggers email intent with the summary attached when clicked.                 | UAT 3 | Verify email button is functional on all screens | Manual        | n/a   |    |    |
| As a user, I want the app to calculate and display my total expenses and income so I can have a clear picture of my overall spending and earnings. | Total expenses and income are accurately calculated from transaction data and displayed on the dashboard.                         | UAT 4  | Validate calculation of total expenses & income | Manual        | completed |      |      |
| As a user, I want to add purchases and view an itemized list with total costs per category to help me manage my spending in different areas. | Users can add new transactions, and an itemized list displays costs per category, with each category's total displayed.           | UAT 5  | Check itemized list for categories              | Manual      | wip |      |      |
| As a user, I want to select a specific month to view my spending, so I can analyze my monthly expenses. | Date selector allows users to pick a month, and expenses for that month are displayed accurately on the analytics page.           | UAT 6  | Verify monthly data filtering                   | manual       | completed but changed |      |      |
| As a user, I want to see a visual graph that summarizes my expenses for the selected month, allowing me to understand my spending trends over time. | Visual graph is generated on the analytics page after a month is selected, showing expenses by category.                 | UAT 7  | Validate graph generation & display             | manual    | blocked|      |      |



							
# Final Outcome 
<img width="659" height="859" alt="image" src="https://github.com/user-attachments/assets/549add37-30b9-43c6-8a77-8cccada6c56e" />
<img width="557" height="712" alt="image" src="https://github.com/user-attachments/assets/76128056-19db-4037-8a6f-695167c97f44" />
<img width="582" height="723" alt="image" src="https://github.com/user-attachments/assets/36f43c0e-c0a7-495c-befe-0dea93a4f45f" />

							




