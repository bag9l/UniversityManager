# UniversityManager
test task

Create a simple Spring Boot java project with the console interface for university, which consists of departments and lectors. The lectors could work in more than one department. A lector could have one degree (assistant, associate professor, professor).

All data is stored in the relational database.  
Please send us a link to the GitHub repository with your version of this project.

The app should implement such commands:

1. User Input: Who is head of department {department_name}<br />
        * Answer: Head of {department_name} department is {head_of_department_name}

2. User Input: Show {department_name} statistics.<br />
        * Answer: assistans - {assistams_count}. <br />
                * associate professors - {associate_professors_count}<br />
                * professors -{professors_count}

3. User Input: Show the average salary for the department {department_name}.<br />
        * Answer: The average salary of {department_name} is {average_salary}

4. User Input: Show count of employee for {department_name}.<br />
        * Answer: {employee_count}

5. User Input: Global search by {template}.<br />
        * Example: Global search by van<br />
        * Answer: Ivan Petrenko, Petro Ivanov
